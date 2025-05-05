package controle;

import modelo.Episodio;
import modelo.Serie;
import visao.VisaoEpisodios;
import util.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ControleEpisodios {

    private Arquivo<Episodio> arqEpisodios;
    private Arquivo<Serie> arqSeries;
    private ArvoreBMais<ParSerieEpisodio> indiceArvore;
    private VisaoEpisodios visaoE;
    private Scanner sc;
    private int idSerie;

    public ControleEpisodios(int idSerie) throws Exception {
        arqEpisodios = new Arquivo<>("Episodios", Episodio.class.getConstructor());
        arqSeries = new Arquivo<>("Series", Serie.class.getConstructor());
        indiceArvore = new ArvoreBMais<>(
                ParSerieEpisodio.class.getConstructor(),
                4,
                "dados/Episodios/serie_episodio.ind"
        );
        visaoE = new VisaoEpisodios();
        sc = new Scanner(System.in);
        this.idSerie = idSerie;

        Serie s = arqSeries.read(idSerie);
        if (s == null) {
            throw new Exception("Série não encontrada. Não é possível gerenciar episódios.");
        }
    }

    public ControleEpisodios() throws Exception {
        this(4);  // Valor padrão razoável para a ordem da árvore
    }

    public void menu() {
        int opc;
        do {
            System.out.println("\nPUCFlix 1.0");
            System.out.println("------------");
            System.out.println("> Início > Séries > Série: "+ idSerie);
            System.out.println("1. Incluir episódio");
            System.out.println("2. Buscar episódio");
            System.out.println("3. Atualizar episódio");
            System.out.println("4. Excluir episódio");
            System.out.println("5. Listar todos os episódios");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opc = Integer.parseInt(sc.nextLine());

            try {
                switch (opc) {
                    case 1 -> incluirEpisodio();
                    case 2 -> buscarEpisodio();
                    case 3 -> atualizarEpisodio();
                    case 4 -> excluirEpisodio();
                    case 5 -> listarEpisodios();
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }

        } while (opc != 0);
    }

    public void incluirEpisodio() throws Exception {
        Episodio ep = visaoE.leEpisodio(idSerie);
        if (ep == null) return;

        int id = arqEpisodios.create(ep);
        indiceArvore.create(new ParSerieEpisodio(idSerie, id));
        System.out.println("Episódio incluído com ID: " + id);
    }

    public void buscarEpisodio() throws Exception {
        System.out.print("ID do episódio: ");
        int id = Integer.parseInt(sc.nextLine());
        Episodio ep = arqEpisodios.read(id);
        if (ep != null && ep.getIdSerie() == idSerie) {
            visaoE.mostraEpisodio(ep);
        } else {
            System.out.println("Episódio não encontrado ou não pertence a esta série.");
        }
    }

    public void atualizarEpisodio() throws Exception {
        System.out.print("ID do episódio: ");
        int id = Integer.parseInt(sc.nextLine());
        Episodio antigo = arqEpisodios.read(id);
        if (antigo == null || antigo.getIdSerie() != idSerie) {
            System.out.println("Episódio não encontrado ou não pertence a esta série.");
            return;
        }

        Episodio novo = visaoE.leEpisodio(idSerie);
        if (novo == null) return;

        novo.setId(id);
        arqEpisodios.update(novo);
        System.out.println("Episódio atualizado.");
    }

    public void excluirEpisodio() throws Exception {
        System.out.print("ID do episódio: ");
        int id = Integer.parseInt(sc.nextLine());
        Episodio ep = arqEpisodios.read(id);
        if (ep == null || ep.getIdSerie() != idSerie) {
            System.out.println("❌ Episódio não encontrado ou não pertence a esta série.");
            return;
        }

        arqEpisodios.delete(id);
        indiceArvore.delete(new ParSerieEpisodio(idSerie, id));
        System.out.println("Episódio excluído.");
    }

    public void listarEpisodios() throws Exception {
        // Corrigido: leitura de todos os pares e filtragem por idSerie
        ArrayList<ParSerieEpisodio> todos = indiceArvore.readAll();
        ArrayList<ParSerieEpisodio> pares = new ArrayList<>();
        for (ParSerieEpisodio par : todos) {
            if (par.getIdSerie() == idSerie) {
                pares.add(par);
            }
        }

        if (pares.isEmpty()) {
            System.out.println("Nenhum episódio encontrado para esta série.");
            return;
        }

        for (ParSerieEpisodio par : pares) {
            Episodio ep = arqEpisodios.read(par.getIdEpisodio());
            if (ep != null) visaoE.mostraEpisodio(ep);
        }
    }

}
