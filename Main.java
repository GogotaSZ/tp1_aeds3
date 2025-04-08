import controle.ControleSeries;
import controle.ControleEpisodios;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opc;

        do {
            System.out.println("\n=== PUCFlix ===");
            System.out.println("1. Gerenciar Séries");
            System.out.println("2. Gerenciar Episódios de uma Série");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opc = Integer.parseInt(sc.nextLine());

            try {
                switch (opc) {
                    case 1 -> {
                        ControleSeries cs = new ControleSeries();
                        cs.menu();
                    }
                    case 2 -> {
                        System.out.print("Informe o ID da série: ");
                        int idSerie = Integer.parseInt(sc.nextLine());
                        ControleEpisodios ce = new ControleEpisodios(idSerie);
                        ce.menu();
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opc != 0);

        System.out.println("Encerrando...");
        sc.close();
    }
}
