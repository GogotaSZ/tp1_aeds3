import controle.ControleSeries;
import controle.ControleEpisodios;
import controle.ControleAtores;
import modelo.Serie;
import util.Arquivo;

public class Main {

    public static void main(String[] args) {
        try {
            Arquivo<Serie> arqSeries = new Arquivo<>("Series", Serie.class.getConstructor());
            if (arqSeries.read(4) == null) {
                Serie s = new Serie("Temporária", 2025, "Série para inicialização", "PUCFlix");
                s.setId(4);
                arqSeries.create(s);
            }

            ControleSeries controleSeries = new ControleSeries();
            ControleEpisodios controleEpisodios = new ControleEpisodios();
            ControleAtores controleAtores = new ControleAtores();

            int op;
            java.util.Scanner sc = new java.util.Scanner(System.in);

            do {
                System.out.println("\nPUCFlix 1.0");
                System.out.println("-----------");
                System.out.println("1) Séries");
                System.out.println("2) Episódios");
                System.out.println("3) Atores");
                System.out.println("0) Sair");
                System.out.print("Opção: ");
                op = Integer.parseInt(sc.nextLine());

                switch (op) {
                    case 1 -> controleSeries.menu();
                    case 2 -> controleEpisodios.menu();
                    case 3 -> controleAtores.menu();
                    case 0 -> System.out.println("Saindo do sistema...");
                    default -> System.out.println("❌ Opção inválida!");
                }
            } while (op != 0);

            sc.close();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
