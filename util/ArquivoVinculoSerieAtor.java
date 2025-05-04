package util;

import java.util.ArrayList;

public class ArquivoVinculoSerieAtor {

    private ArvoreBMais<SerieAtor> arqSerieAtor;
    private ArvoreBMais<AtorSerie> arqAtorSerie;

    public ArquivoVinculoSerieAtor() throws Exception {
        arqSerieAtor = new ArvoreBMais<>(SerieAtor.class.getConstructor(), "dados/serie_ator.idx");
        arqAtorSerie = new ArvoreBMais<>(AtorSerie.class.getConstructor(), "dados/ator_serie.idx");
    }

    /**
     * Cria o vínculo entre uma série e um ator.
     */
    public void criarVinculo(int idSerie, int idAtor) throws Exception {
        arqSerieAtor.create(new SerieAtor(idSerie, idAtor));
        arqAtorSerie.create(new AtorSerie(idAtor, idSerie));
    }

    /**
     * Retorna todos os atores vinculados a uma série.
     */
    public ArrayList<SerieAtor> lerAtoresPorSerie(int idSerie) throws Exception {
        return arqSerieAtor.read(new SerieAtor(idSerie, -1));
    }

    /**
     * Retorna todas as séries vinculadas a um ator.
     */
    public ArrayList<AtorSerie> lerSeriesPorAtor(int idAtor) throws Exception {
        return arqAtorSerie.read(new AtorSerie(idAtor, -1));
    }

    /**
     * Exclui o vínculo entre uma série e um ator.
     */
    public boolean excluirVinculo(int idSerie, int idAtor) throws Exception {
        boolean ok1 = arqSerieAtor.delete(new SerieAtor(idSerie, idAtor));
        boolean ok2 = arqAtorSerie.delete(new AtorSerie(idAtor, idSerie));
        return ok1 && ok2;
    }
}
