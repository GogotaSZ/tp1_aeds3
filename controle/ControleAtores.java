package controle;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.Ator;
import util.*;

public class ControleAtores {
    private Arquivo<Ator> arqAtor;
    private ArvoreBMais<AtorSerie> arvoreAtorSerie;
    private ArvoreBMais<SerieAtor> arvoreSerieAtor;
    private Scanner scanner;

    public ControleAtores() throws Exception {
        arqAtor = new Arquivo<>("ator", Ator.class.getConstructor());
        arvoreAtorSerie = new ArvoreBMais<>(AtorSerie.class.getConstructor(), 4, "ator_serie.db");
        arvoreSerieAtor = new ArvoreBMais<>(SerieAtor.class.getConstructor(), 4, "serie_ator.db");
        scanner = new Scanner(System.in);
    }

    public void menu() throws Exception {
        int opcao;
        do {
            System.out.println("\nMENU ATORES");
            System.out.println("1) Cadastrar novo ator");
            System.out.println("2) Buscar ator por nome");
            System.out.println("3) Listar todos os atores");
            System.out.println("4) Atualizar ator");
            System.out.println("5) Excluir ator");
            System.out.println("6) Listar séries do ator");
            System.out.println("0) Voltar");
            System.out.print("\nOpção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    cadastrarAtor();
                    break;
                case 2:
                    buscarAtorPorNome();
                    break;
                case 3:
                    listarAtores();
                    break;
                case 4:
                    atualizarAtor();
                    break;
                case 5:
                    excluirAtor();
                    break;
                case 6:
                    listarSeriesDoAtor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // Métodos auxiliares
    private int getIdPorNome(String nome) throws Exception {
        for (int id = 1; id <= arqAtor.getUltimoID(); id++) {
            Ator a = arqAtor.read(id);
            if (a != null && a.getNome().equalsIgnoreCase(nome)) {
                return id;
            }
        }
        return -1;
    }

    private Ator getAtorPorNome(String nome) throws Exception {
        for (int id = 1; id <= arqAtor.getUltimoID(); id++) {
            Ator a = arqAtor.read(id);
            if (a != null && a.getNome().equalsIgnoreCase(nome)) {
                return a;
            }
        }
        return null;
    }

    // Métodos principais modificados
    public void cadastrarAtor() throws Exception {
        System.out.println("\nCADASTRAR ATOR");

        System.out.print("Nome do ator: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Erro: Nome não pode ser vazio!");
            return;
        }

        if (getAtorPorNome(nome) != null) {
            System.out.println("Erro: Ator já cadastrado!");
            return;
        }

        int novoId = arqAtor.getUltimoID() + 1;
        Ator novoAtor = new Ator(novoId, nome);
        criar(novoAtor);
        System.out.println("Ator cadastrado com sucesso! ID gerado: " + novoId);
    }

    public void buscarAtorPorNome() throws Exception {
        System.out.println("\nBUSCAR ATOR");
        System.out.print("Digite o nome do ator: ");
        String nome = scanner.nextLine();

        Ator ator = getAtorPorNome(nome);
        if (ator == null) {
            System.out.println("Ator não encontrado!");
            return;
        }

        System.out.println("\nDADOS DO ATOR:");
        System.out.println("ID: " + ator.getID());
        System.out.println("Nome: " + ator.getNome());

        System.out.println("\nSÉRIES PARTICIPADAS:");
        ArrayList<AtorSerie> series = arvoreAtorSerie.read(new AtorSerie(ator.getID(), 0));
        if (series.isEmpty()) {
            System.out.println("Nenhuma série vinculada");
        } else {
            for (AtorSerie as : series) {
                System.out.println("- Série ID: " + as.getIdSerie());
            }
        }
    }

    public void listarAtores() throws Exception {
        System.out.println("\nLISTA DE ATORES:");
        boolean encontrou = false;
        for (int id = 1; id <= arqAtor.getUltimoID(); id++) {
            Ator a = arqAtor.read(id);
            if (a != null) {
                System.out.println("Nome: " + a.getNome() + " | ID: " + a.getID());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum ator cadastrado.");
        }
    }

    public void atualizarAtor() throws Exception {
        System.out.println("\nATUALIZAR ATOR");
        System.out.print("Digite o nome atual do ator: ");
        String nomeAtual = scanner.nextLine();

        Ator ator = getAtorPorNome(nomeAtual);
        if (ator == null) {
            System.out.println("Ator não encontrado!");
            return;
        }

        System.out.print("Novo nome (deixe em branco para manter): ");
        String novoNome = scanner.nextLine().trim();

        if (!novoNome.isEmpty()) {
            if (getAtorPorNome(novoNome) != null) {
                System.out.println("Erro: Já existe um ator com este nome!");
                return;
            }

            Ator atorAtualizado = new Ator(ator.getID(), novoNome);
            if (arqAtor.excluir(ator.getID()) && criar(atorAtualizado) == ator.getID()) {
                System.out.println("Ator atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar ator!");
            }
        } else {
            System.out.println("Nenhuma alteração realizada.");
        }
    }

    public void excluirAtor() throws Exception {
        System.out.println("\nEXCLUIR ATOR");
        System.out.print("Digite o nome do ator: ");
        String nome = scanner.nextLine();

        int id = getIdPorNome(nome);
        if (id == -1) {
            System.out.println("Ator não encontrado!");
            return;
        }

        ArrayList<AtorSerie> vinculos = arvoreAtorSerie.read(new AtorSerie(id, 0));
        if (!vinculos.isEmpty()) {
            System.out.println("Erro: Ator não pode ser excluído pois está vinculado a " +
                    vinculos.size() + " série(s)!");
            return;
        }

        if (deletar(id)) {
            System.out.println("Ator excluído com sucesso!");
        } else {
            System.out.println("Falha ao excluir ator!");
        }
    }

    public void listarSeriesDoAtor() throws Exception {
        System.out.println("\nSÉRIES DO ATOR");
        System.out.print("Digite o nome do ator: ");
        String nome = scanner.nextLine();

        Ator ator = getAtorPorNome(nome);
        if (ator == null) {
            System.out.println("Ator não encontrado!");
            return;
        }

        System.out.println("\nSéries vinculadas ao ator: " + ator.getNome());
        ArrayList<AtorSerie> series = arvoreAtorSerie.read(new AtorSerie(ator.getID(), 0));
        if (series.isEmpty()) {
            System.out.println("Nenhuma série vinculada");
        } else {
            for (AtorSerie as : series) {
                System.out.println("- Série ID: " + as.getIdSerie());
            }
        }
    }

    // Métodos CRUD mantidos (agora usando IDs internamente)
    public int criar(Ator ator) throws Exception {
        return arqAtor.incluir(ator);
    }

    public Ator ler(int id) throws Exception {
        return arqAtor.read(id);
    }

    public boolean deletar(int id) throws Exception {
        return arqAtor.excluir(id);
    }

    // Métodos de vinculação (mantidos com IDs internos)
    public void vincularAtorSerie(int idAtor, int idSerie) throws Exception {
        arvoreAtorSerie.create(new AtorSerie(idAtor, idSerie));
        arvoreSerieAtor.create(new SerieAtor(idSerie, idAtor));
    }

    public void deletarVinculo(int idAtor, int idSerie) throws Exception {
        arvoreAtorSerie.delete(new AtorSerie(idAtor, idSerie));
        arvoreSerieAtor.delete(new SerieAtor(idSerie, idAtor));
    }

}