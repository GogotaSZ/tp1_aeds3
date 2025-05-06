package controle;

import modelo.Serie;
import modelo.Episodio;
import modelo.Ator;
import util.*;
import visao.VisaoSeries;
import visao.VisaoEpisodios;

import java.util.ArrayList;
import java.util.Scanner;

public class ControleSeries {
    private Arquivo<Serie> arqSeries;
    private Arquivo<Episodio> arqEpisodios;
    private ArvoreBMais<ParSerieEpisodio> indiceArvore;
    private ArvoreBMais<SerieAtor> arvoreSerieAtor;
    private ControleAtores controleAtores;
    private VisaoSeries visaoS;
    private VisaoEpisodios visaoE;
    private Scanner sc;

    public ControleSeries() throws Exception {
        arqSeries = new Arquivo<>("Series", Serie.class.getConstructor());
        arqEpisodios = new Arquivo<>("Episodios", Episodio.class.getConstructor());
        indiceArvore = new ArvoreBMais<>(
                ParSerieEpisodio.class.getConstructor(),
                4,
                "dados/Episodios/serie_episodio.ind"
        );
        arvoreSerieAtor = new ArvoreBMais<>(
                SerieAtor.class.getConstructor(),
                4,
                "dados/Series/serie_ator.ind"
        );
        controleAtores = new ControleAtores();
        visaoS = new VisaoSeries();
        visaoE = new VisaoEpisodios();
        sc = new Scanner(System.in);
    }

    public void menu() {
        int opc;
        do {
            System.out.println("\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.println("> Início > Séries");
            System.out.println("1. Inserir série");
            System.out.println("2. Buscar série");
            System.out.println("3. Atualizar série");
            System.out.println("4. Excluir série");
            System.out.println("5. Visualizar episódios por temporada");
            System.out.println("6. Gerenciar elenco");
            System.out.println("0. Voltar");

            System.out.print("Opção: ");
            opc = Integer.parseInt(sc.nextLine());

            try {
                switch (opc) {
                    case 1 -> inserirSerie();
                    case 2 -> buscarSerie();
                    case 3 -> atualizarSerie();
                    case 4 -> excluirSerie();
                    case 5 -> visualizarEpisodiosPorTemporada();
                    case 6 -> gerenciarElenco();
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opc != 0);
    }

    private void gerenciarElenco() throws Exception {
        System.out.print("\nID da série: ");
        int idSerie = Integer.parseInt(sc.nextLine());

        if (arqSeries.read(idSerie) == null) {
            System.out.println("Série não encontrada!");
            return;
        }

        int opcao;
        do {
            System.out.println("\nGERENCIAR ELENCO - SÉRIE " + idSerie);
            System.out.println("1) Listar atores");
            System.out.println("2) Adicionar ator");
            System.out.println("3) Remover ator");
            System.out.println("0) Voltar");
            System.out.print("\nOpção: ");

            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1 -> listarAtoresDaSerie(idSerie);
                case 2 -> adicionarAtorNaSerie(idSerie);
                case 3 -> removerAtorDaSerie(idSerie);
            }
        } while (opcao != 0);
    }

    public void listarSeries() throws Exception {
        System.out.println("\nLISTA DE SÉRIES:");
        boolean encontrou = false;

        // Percorre todos os IDs possíveis
        for (int id = 1; id <= arqSeries.getUltimoID(); id++) {
            Serie s = arqSeries.read(id);
            if (s != null) {
                System.out.println("ID: " + s.getId() + " | Nome: " + s.getNome());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma série cadastrada.");
        }
    }

    public void inserirSerie() throws Exception {
        Serie nova = visaoS.leSerie(sc);
        int id = arqSeries.create(nova);
        System.out.println("Série salva com ID: " + id);
    }

    public void buscarSerie() throws Exception {
        System.out.print("ID da série: ");
        int id = Integer.parseInt(sc.nextLine());
        Serie s = arqSeries.read(id);
        if (s != null) {
            visaoS.mostraSerie(s);
            listarAtoresDaSerie(id);
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    public void atualizarSerie() throws Exception {
        System.out.print("ID da série para atualizar: ");
        int id = Integer.parseInt(sc.nextLine());
        Serie existente = arqSeries.read(id);
        if (existente != null) {
            Serie nova = visaoS.leSerie(sc);
            nova.setId(id);
            if (arqSeries.update(nova)) {
                System.out.println("Série atualizada com sucesso.");
            } else {
                System.out.println("Erro ao atualizar série.");
            }
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    public void excluirSerie() throws Exception {
        System.out.print("ID da série para excluir: ");
        int id = Integer.parseInt(sc.nextLine());

        // Verifica se há episódios vinculados
        ArrayList<ParSerieEpisodio> relacionados = indiceArvore.read(new ParSerieEpisodio(id, -1));
        if (!relacionados.isEmpty()) {
            System.out.println("Não é possível excluir. Existem episódios vinculados à série.");
            return;
        }

        // Remove todos os vínculos com atores
        ArrayList<SerieAtor> vinculos = arvoreSerieAtor.read(new SerieAtor(id, 0));
        for (SerieAtor vinculo : vinculos) {
            controleAtores.deletarVinculo(vinculo.getIdAtor(), id);
        }

        if (arqSeries.delete(id)) {
            System.out.println("Série excluída com sucesso.");
        } else {
            System.out.println("Série não encontrada ou erro ao excluir.");
        }
    }

    public void listarAtoresDaSerie(int idSerie) throws Exception {
        System.out.println("\nATORES DA SÉRIE:");

        ArrayList<SerieAtor> vinculos = arvoreSerieAtor.read(new SerieAtor(idSerie, 0));

        if (vinculos.isEmpty()) {
            System.out.println("Nenhum ator vinculado a esta série.");
            return;
        }

        for (SerieAtor vinculo : vinculos) {
            Ator a = controleAtores.ler(vinculo.getIdAtor());
            if (a != null) {
                System.out.println("ID: " + a.getID() + " | Nome: " + a.getNome());
            }
        }
    }

    public void adicionarAtorNaSerie(int idSerie) throws Exception {
        System.out.print("\nID do ator a ser vinculado: ");
        int idAtor = Integer.parseInt(sc.nextLine());

        // Verifica se o ator existe
        if (controleAtores.ler(idAtor) == null) {
            System.out.println("Ator não encontrado!");
            return;
        }

        // Verifica se o vínculo já existe
        SerieAtor novoVinculo = new SerieAtor(idSerie, idAtor);
        if (!arvoreSerieAtor.read(novoVinculo).isEmpty()) {
            System.out.println("Este ator já está vinculado à série!");
            return;
        }

        // Cria o vínculo nas duas árvores
        controleAtores.vincularAtorSerie(idAtor, idSerie);
        System.out.println("Ator vinculado com sucesso!");
    }

    public void removerAtorDaSerie(int idSerie) throws Exception {
        System.out.print("\nID do ator a ser desvinculado: ");
        int idAtor = Integer.parseInt(sc.nextLine());

        // Verifica se o vínculo existe
        SerieAtor vinculo = new SerieAtor(idSerie, idAtor);
        if (arvoreSerieAtor.read(vinculo).isEmpty()) {
            System.out.println("Este ator não está vinculado à série!");
            return;
        }

        // Remove o vínculo
        controleAtores.deletarVinculo(idAtor, idSerie);
        System.out.println("Ator desvinculado com sucesso!");
    }

    public void visualizarEpisodiosPorTemporada() throws Exception {
        System.out.print("ID da série: ");
        int idSerie = Integer.parseInt(sc.nextLine());
        Serie s = arqSeries.read(idSerie);

        if (s == null) {
            System.out.println("Série não encontrada.");
            return;
        }

        ArrayList<ParSerieEpisodio> todos = indiceArvore.readAll();
        ArrayList<ParSerieEpisodio> pares = new ArrayList<>();
        for (ParSerieEpisodio par : todos) {
            if (par.getIdSerie() == idSerie) {
                pares.add(par);
            }
        }

        ArrayList<Episodio> episodios = new ArrayList<>();
        for (ParSerieEpisodio par : pares) {
            Episodio ep = arqEpisodios.read(par.getIdEpisodio());
            if (ep != null) episodios.add(ep);
        }

        System.out.print("Temporada para filtrer: ");
        int temporada = Integer.parseInt(sc.nextLine());

        boolean encontrados = false;
        for (Episodio ep : episodios) {
            if (ep.getTemporada() == temporada) {
                visaoE.mostraEpisodio(ep);
                encontrados = true;
            }
        }

        if (!encontrados) {
            System.out.println("Nenhum episódio encontrado para essa temporada.");
        }
    }
}