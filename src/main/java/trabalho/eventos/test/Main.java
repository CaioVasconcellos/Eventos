package trabalho.eventos.test;

import trabalho.eventos.service.PessoalService;
import trabalho.eventos.service.PessoaService;
import trabalho.eventos.service.ShowService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int op;
        final Scanner SCANNER = new Scanner(System.in);

        while(true){
            menu();
            op = Integer.parseInt(SCANNER.nextLine());
            if(op == 0) break;
            switch (op){
                case 1 -> {
                    pessoaMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    PessoaService.menu(op);
                }
                case 2 -> {
                    eventoMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    ShowService.menu(op);
                }

            }
        }
    }

    private static void menu(){
        System.out.println("Digite o número da sua operação");
        System.out.println("1. Pessoa");
        System.out.println("2. Eventos");
        System.out.println("0. Sair");
    }

    private static void pessoaMenu(){
        System.out.println("Digite o número da sua operação");
        System.out.println("1. Login");
        System.out.println("2. Cadastro");
        System.out.println("9. Voltar");
    }

    private static void eventoMenu(){
        System.out.println("Digite o número da sua operação");
        System.out.println("1. Ver eventos");
        System.out.println("2. Detalhe eventos");
        System.out.println("9. Voltar");
    }


}