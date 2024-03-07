package trabalho.eventos.service;

import trabalho.eventos.repository.ShowRepository;
import trabalho.eventos.dominio.TipoShow;

import java.util.Scanner;

public class ShowService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void menu(int op) {
        switch (op) {
            case 1 -> verEvento();
            case 2 -> detalheEvento();
        }
    }

    private static void verEvento(){
        System.out.println("Digite o número da sua operação");
        System.out.println("1. Show");
        System.out.println("2. Festa");
        System.out.println("3. Infantil");
        System.out.println("4. Evento Esportivo");
        System.out.println("9. Voltar");
        TipoShow tipoShow = TipoShow.menu(SCANNER.nextShort());
        ShowRepository.verShow(tipoShow);
    }

    private static void detalheEvento(){
        System.out.println("Digite o nome do evento desejado");
        String nome = SCANNER.nextLine();
        ShowRepository.detalhesEventoPorNome(nome);


    }
}
