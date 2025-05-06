import visao.MenuP;


public class Main {
    public static void main(String[] args) {
        try {
            MenuP menu = new MenuP();
            menu.menu();
        } catch (Exception e) {
            System.err.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
