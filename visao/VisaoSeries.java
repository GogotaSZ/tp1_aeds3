package visao;

import java.util.*;
import modelo.Serie;

public class VisaoSeries {

    // Leitura de dados da série (utilizado no cadastro e atualização)
    public Serie leSerie(Scanner sc) {
        System.out.println("----- Cadastro/Atualização de Série -----");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Ano de lançamento: ");
        int ano = Integer.parseInt(sc.nextLine());

        System.out.print("Sinopse: ");
        String sinopse = sc.nextLine();

        System.out.print("Plataforma de streaming: ");
        String streaming = sc.nextLine();

        Serie s = new Serie();
        s.setNome(nome);
        s.setAnoLancamento(ano);
        s.setSinopse(sinopse);
        s.setStreaming(streaming);

        return s;
    }

    // Exibe os dados principais da série
    public void mostraSerie(Serie s) {
        System.out.println("\n-----------------------------");
        System.out.println("ID: " + s.getId());
        System.out.println("Nome: " + s.getNome());
        System.out.println("Ano de Lançamento: " + s.getAnoLancamento());
        System.out.println("Sinopse: " + s.getSinopse());
        System.out.println("Streaming: " + s.getStreaming());
    }

    // Leitura dos IDs dos atores (ex: "1, 3, 7")
    public String[] leIdsAtores(Scanner sc) {
        System.out.print("Informe os IDs dos atores (separados por vírgula): ");
        String entrada = sc.nextLine();
        if (entrada.trim().isEmpty()) return new String[0];
        return entrada.split(",");
    }

    // Exibição dos nomes dos atores vinculados à série
    public void mostraNomesAtores(List<String> nomes) {
        if (nomes == null || nomes.isEmpty()) {
            System.out.println("Atores: (nenhum ator vinculado)");
        } else {
            System.out.println("Atores:");
            for (String nome : nomes) {
                System.out.println("- " + nome);
            }
        }
    }
}
