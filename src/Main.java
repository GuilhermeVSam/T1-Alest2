import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        APP app = new APP();
        Scanner sc = new Scanner(System.in);
        String caso;
        System.out.println("Digite o nome do arquivo à ser carregado (ex: casoC1500): ");
        caso = sc.nextLine();
        app.carregarMapa(caso);
        app.lerElementos();
        System.out.println("Valor encontrado: R$" + app.getSoma());
/*        app.carregarMapa("casoC100");
        app.lerElementos();
        System.out.println("casoC50: " + app.getSoma());
        app.carregarMapa("casoC200");
        app.lerElementos();
        System.out.println("casoC50: " + app.getSoma());
        app.carregarMapa("casoC500");
        app.lerElementos();
        System.out.println("casoC50: " + app.getSoma());
        app.carregarMapa("casoC750");
        app.lerElementos();
        System.out.println("casoC50: " + app.getSoma());
        app.carregarMapa("casoC1000");
        app.lerElementos();
        System.out.println("casoC50: " + app.getSoma());
        app.carregarMapa("casoC1500");
        app.lerElementos();
        System.out.println("casoC50: " + app.getSoma());
        app.carregarMapa("casoC2000");
        app.lerElementos();
        System.out.println("casoC50: " + app.getSoma());*/
    }
}