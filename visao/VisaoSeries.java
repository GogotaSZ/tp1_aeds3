package visao;

import java.util.Scanner;
import modelo.Serie;

public class VisaoSeries {

    private Scanner scanner;

    public VisaoSeries() {
        scanner = new Scanner(System.in);
    }

    public Serie leSerie() {
        System.out.print("Nome da série: ");
        String nome = scanner.nextLine();

        System.out.print("Ano de lançamento: ");
        int ano = Integer.parseInt(scanner.nextLine());

        System.out.print("Sinopse: ");
        String sinopse = scanner.nextLine();

        System.out.print("Streaming: ");
        String streaming = scanner.nextLine();

        return new Serie(nome, ano, sinopse, streaming);
    }

    public void mostraSerie(Serie s) {
        System.out.println("\n===== Detalhes da Série =====");
        System.out.println(s);
        System.out.println("==============================");
    }
}
