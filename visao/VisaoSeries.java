package visao;

import java.util.Scanner;
import modelo.Serie;

public class VisaoSeries {

    public Serie leSerie(Scanner sc) {
        System.out.println("----- Cadastro de nova série -----");
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
    

    public void mostraSerie(Serie s) {
        System.out.println(s.toString());
    }
}
