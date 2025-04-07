import modelo.Serie;
import util.Arquivo;
import visao.VisaoSeries;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VisaoSeries visao = new VisaoSeries();

        try {
            // Instanciando o CRUD genérico com a classe Serie
            Arquivo<Serie> arq = new Arquivo<>("series.db", Serie.class.getConstructor());

            // Criar uma nova série
            System.out.println("----- Cadastro de nova série -----");
            Serie nova = visao.leSerie(sc);
            int id = arq.create(nova);
            System.out.println("\n✅ Série salva com ID: " + id);

            // Ler do arquivo e mostrar
            Serie lida = arq.read(id);
            System.out.println("\n📂 Série lida do arquivo:");
            visao.mostraSerie(lida);

        } catch (Exception e) {
            System.out.println("❌ Erro durante o processo:");
            e.printStackTrace();
        }

        sc.close();
    }
}
