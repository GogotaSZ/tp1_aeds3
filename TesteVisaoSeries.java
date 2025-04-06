import visao.VisaoSeries;
import modelo.Serie;

public class TesteVisaoSeries {
    public static void main(String[] args) {
        VisaoSeries visao = new VisaoSeries();

        System.out.println("📺 Cadastro de Série");
        Serie novaSerie = visao.leSerie();

        System.out.println("\n🎬 Série cadastrada:");
        visao.mostraSerie(novaSerie);
    }
}
