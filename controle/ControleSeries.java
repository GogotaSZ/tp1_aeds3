// Series Controller
/* 
 * - Insert,Search,Update and Delete Menu
 * - Calls methods from VisaoSeries
 * - Verifies if series to be deleted have episodes
 * - Uses B+ tree indexes to manage series and episodes. 
 */

package controle;

import modelo.Serie;
import modelo.Episodio;
import visao.VisaoSeries;
import visao.VisaoEpisodios;
import util.Arquivo;
import util.ArvoreBMais;
import java.util.ArrayList;
import java.util.Scanner;
import util.ParSerieEpisodio;

public class ControleSeries {

    private Arquivo<Serie> arqSeries;
    private Arquivo<Episodio> arqEpisodios;
    private ArvoreBMais<ParSerieEpisodio> indiceArvore;
    private VisaoSeries visaoS;
    private VisaoEpisodios visaoE;
    private Scanner sc;

    public ControleSeries() throws Exception {
        arqSeries = new Arquivo<>("Series", Serie.class.getConstructor());
        arqEpisodios = new Arquivo<>("Episodios", Episodio.class.getConstructor());
        indiceArvore = new ArvoreBMais<>(ParSerieEpisodio.class.getConstructor(), 4, "serie_episodio.ind");
        visaoS = new VisaoSeries();
        visaoE = new VisaoEpisodios();
        sc = new Scanner(System.in);
    }

    public void menu() {
        int opc;
        do {
            System.out.println("\nPUCFlix 1.0")
            System.out.println("-----------");
            System.out.println("> Início > Séries")
            System.out.println("1. Inserir série");
            System.out.println("2. Buscar série");
            System.out.println("3. Atualizar série");
            System.out.println("4. Excluir série");
            System.out.println("5. Visualizar episódios por temporada");
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
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opc != 0);
    }

    private void inserirSerie() throws Exception {
        Serie nova = visaoS.leSerie(sc);
        int id = arqSeries.create(nova);
        System.out.println("Série salva com ID: " + id);
    }

    private void buscarSerie() throws Exception {
        System.out.print("ID da série: ");
        int id = Integer.parseInt(sc.nextLine());
        Serie s = arqSeries.read(id);
        if (s != null)
            visaoS.mostraSerie(s);
        else
            System.out.println("Série não encontrada.");
    }

    private void atualizarSerie() throws Exception {
        System.out.print("ID da série para atualizar: ");
        int id = Integer.parseInt(sc.nextLine());
        Serie existente = arqSeries.read(id);
        if (existente != null) {
            Serie nova = visaoS.leSerie(sc);
            nova.setId(id);
            if (arqSeries.update(nova))
                System.out.println("Série atualizada com sucesso.");
            else
                System.out.println("Erro ao atualizar série.");
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    private void excluirSerie() throws Exception {
        System.out.print("ID da série para excluir: ");
        int id = Integer.parseInt(sc.nextLine());

        ArrayList<ParSerieEpisodio> relacionados = indiceArvore.read(new ParSerieEpisodio(id, -1));
        if (!relacionados.isEmpty()) {
            System.out.println("Não é possível excluir. Existem episódios vinculados à série.");
            return;
        }

        if (arqSeries.delete(id))
            System.out.println("Série excluída com sucesso.");
        else
            System.out.println("Série não encontrada ou erro ao excluir.");
    }

    private void visualizarEpisodiosPorTemporada() throws Exception {
        System.out.print("ID da série: ");
        int idSerie = Integer.parseInt(sc.nextLine());
        Serie s = arqSeries.read(idSerie);

        if (s == null) {
            System.out.println("Série não encontrada.");
            return;
        }

        ArrayList<ParSerieEpisodio> pares = indiceArvore.read(new ParSerieEpisodio(idSerie, -1));
        ArrayList<Episodio> episodios = new ArrayList<>();
        for (ParSerieEpisodio par : pares) {
            Episodio ep = arqEpisodios.read(par.getIdEpisodio());
            if (ep != null) episodios.add(ep);
        }

        System.out.print("Temporada para filtrar: ");
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
