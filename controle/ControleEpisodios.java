package controle;

import modelo.Episodio;
import modelo.Serie;
import visao.VisaoEpisodios;
import util.Arquivo;
import util.HashExtensivel;
import util.ArvoreBMais;
import util.ParSerieEpisodio;
import java.util.Scanner;

public class ControleEpisodios {

    private Arquivo<Serie> arqSeries;
    private HashExtensivel<Episodio> hashEpisodios;
    private ArvoreBMais<ParSerieEpisodio> indiceArvore;
    private VisaoEpisodios visaoE;
    private Scanner sc;
    private int idSerie;

    public ControleEpisodios(int idSerie) throws Exception {
        arqSeries = new Arquivo<>("Series", Serie.class.getConstructor());
        hashEpisodios = new HashExtensivel<>(Episodio.class.getConstructor(), 4, "episodios.hash.dir", "episodios.hash.dad");
        indiceArvore = new ArvoreBMais<>(ParSerieEpisodio.class.getConstructor(), 4, "serie_episodio.ind");
        visaoE = new VisaoEpisodios();
        sc = new Scanner(System.in);
        this.idSerie = idSerie;

        Serie s = arqSeries.read(idSerie);
        if (s == null) {
            throw new Exception("Série não encontrada. Não é possível gerenciar episódios.");
        }
    }

    public void menu() {
        int opc;
        do {
            System.out.println("\nPUCFlix 1.0");
            System.out.println("------------------")
            System.out.println("> Início > Episódios > Série: "+ idSerie);
            System.out.println("1. Incluir episódio");
            System.out.println("2. Buscar episódio");
            System.out.println("3. Atualizar episódio");
            System.out.println("4. Excluir episódio");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            opc = Integer.parseInt(sc.nextLine());

            try {
                switch (opc) {
                    case 1 -> incluirEpisodio();
                    case 2 -> buscarEpisodio();
                    case 3 -> atualizarEpisodio();
                    case 4 -> excluirEpisodio();
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opc != 0);
    }

    private void incluirEpisodio() throws Exception {
        Episodio ep = visaoE.leEpisodio(idSerie);
        int id = ep.hashCode();
        hashEpisodios.create(ep);
        indiceArvore.create(new ParSerieEpisodio(idSerie, id));
        System.out.println("Episódio incluído com ID/hash: " + id);
    }

    private void buscarEpisodio() throws Exception {
        System.out.print("ID/hash do episódio: ");
        int id = Integer.parseInt(sc.nextLine());
        Episodio ep = hashEpisodios.read(id);
        if (ep != null)
            visaoE.mostraEpisodio(ep);
        else
            System.out.println("Episódio não encontrado.");
    }

    private void atualizarEpisodio() throws Exception {
        System.out.print("ID/hash do episódio: ");
        int id = Integer.parseInt(sc.nextLine());
        Episodio ep = hashEpisodios.read(id);
        if (ep != null) {
            Episodio novo = visaoE.leEpisodio(idSerie);
            hashEpisodios.update(novo);
            System.out.println("Episódio atualizado.");
        } else {
            System.out.println("Episódio não encontrado.");
        }
    }

    private void excluirEpisodio() throws Exception {
        System.out.print("ID/hash do episódio: ");
        int id = Integer.parseInt(sc.nextLine());
        if (hashEpisodios.delete(id)) {
            indiceArvore.delete(new ParSerieEpisodio(idSerie, id));
            System.out.println("Episódio excluído.");
        } else {
            System.out.println("Episódio não encontrado.");
        }
    }
}
