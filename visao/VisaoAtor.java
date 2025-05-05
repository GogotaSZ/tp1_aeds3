package visao;

import java.util.Scanner;
import controle.ControleSeries;
import controle.ControleEpisodios;
import controle.ControleAtores;

public class VisaoAtor {
    private Scanner scanner;
    private ControleSeries controleSeries;
    private ControleEpisodios controleEpisodios;
    private ControleAtores controleAtores;

    public VisaoAtor() throws Exception {
        scanner = new Scanner(System.in);
        controleSeries = new ControleSeries();
        controleEpisodios = new ControleEpisodios();
        controleAtores = new ControleAtores();
    }

    public void menu() throws Exception {
        int opcao;
        do {
            System.out.println("\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.println("> Início");
            System.out.println("\n1) Séries");
            System.out.println("2) Episódios");
            System.out.println("3) Atores");
            System.out.println("0) Sair");
            System.out.print("\nOpção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    menuSeries();
                    break;
                case 2:
                    menuEpisodios();
                    break;
                case 3:
                    menuAtores();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuSeries() throws Exception {
        int opcao;
        do {
            System.out.println("\nMENU SÉRIES");
            System.out.println("1) Listar séries");
            System.out.println("2) Cadastrar nova série");
            System.out.println("3) Buscar série por ID");
            System.out.println("4) Atualizar série");
            System.out.println("5) Excluir série");
            System.out.println("6) Gerenciar elenco da série");
            System.out.println("0) Voltar");
            System.out.print("\nOpção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    controleSeries.visualizarEpisodiosPorTemporada();
                    break;
                case 2:
                    controleSeries.inserirSerie();
                    break;
                case 3:
                    controleSeries.buscarSerie();
                    break;
                case 4:
                    controleSeries.atualizarSerie();
                    break;
                case 5:
                    controleSeries.excluirSerie();
                    break;
                case 6:
                    menuElencoSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuElencoSerie() throws Exception {
        System.out.print("\nDigite o ID da série: ");
        int idSerie = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        int opcao;
        do {
            System.out.println("\nELENCO DA SÉRIE " + idSerie);
            System.out.println("1) Listar atores");
            System.out.println("2) Adicionar ator");
            System.out.println("3) Remover ator");
            System.out.println("0) Voltar");
            System.out.print("\nOpção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    controleSeries.listarAtoresDaSerie(idSerie);
                    break;
                case 2:
                    controleSeries.adicionarAtorNaSerie(idSerie);
                    break;
                case 3:
                    controleSeries.removerAtorDaSerie(idSerie);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuEpisodios() throws Exception {
        int opcao;
        do {
            System.out.println("\nMENU EPISÓDIOS");
            System.out.println("1) Listar episódios");
            System.out.println("2) Cadastrar novo episódio");
            System.out.println("3) Buscar episódio por ID");
            System.out.println("4) Atualizar episódio");
            System.out.println("5) Excluir episódio");
            System.out.println("0) Voltar");
            System.out.print("\nOpção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    controleEpisodios.listarEpisodios();
                    break;
                case 2:
                    controleEpisodios.incluirEpisodio();
                    break;
                case 3:
                    controleEpisodios.buscarEpisodio();
                    break;
                case 4:
                    controleEpisodios.atualizarEpisodio();
                    break;
                case 5:
                    controleEpisodios.excluirEpisodio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuAtores() throws Exception {
        int opcao;
        do {
            System.out.println("\nMENU ATORES");
            System.out.println("1) Listar atores");
            System.out.println("2) Cadastrar novo ator");
            System.out.println("3) Buscar ator por ID");
            System.out.println("4) Atualizar ator");
            System.out.println("5) Excluir ator");
            System.out.println("0) Voltar");
            System.out.print("\nOpção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    controleAtores.listarAtores();
                    break;
                case 2:
                    controleAtores.cadastrarAtor();
                    break;
                case 3:
                    controleAtores.buscarAtor();
                    break;
                case 4:
                    controleAtores.atualizarAtor();
                    break;
                case 5:
                    controleAtores.excluirAtor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    public static void main(String[] args) {
        try {
            VisaoAtor menu = new VisaoAtor();
            menu.menu();
        } catch (Exception e) {
            System.err.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}