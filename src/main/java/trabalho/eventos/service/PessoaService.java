package trabalho.eventos.service;

import trabalho.eventos.repository.PessoaRepository;

import java.util.Scanner;

public class PessoaService {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static String usuarioLogado;

    public static void menu(int op) {

        switch (op) {
            case 1 -> {
                if (login()) {
                    System.out.println("Bem vindo");
                    while(true){
                        if (op == 0)break;
                    menuPessoal();
                    op = Integer.parseInt(SCANNER.nextLine());
                    PessoalService.menuPessoal(op);
                    }
                }
            }
            case 2 -> cadastro();
        }
    }


    private static void cadastro() {
        System.out.print("Login: ");
        String login = SCANNER.nextLine();
        while (PessoaRepository.validacaoLogin(login)) {
            System.out.printf("Login %s já existe. Digite 0 para sair ou informe outro login: ", login);
            String input = SCANNER.nextLine();
            if ("0".equals(input)) {
                System.out.println("Saindo...");
                return;
            } else {
                login = input;
            }
        }
        System.out.print("Senha: ");
        String senha = SCANNER.nextLine();
        System.out.println("Faça seu cadastro: ");
        System.out.print("Primeiro nome: ");
        String nome = SCANNER.nextLine();
        System.out.print("Sobrenome: ");
        String sobreNome = SCANNER.nextLine();
        System.out.print("Data de Nascimento (DD-MM-YYYY): ");
        String dataNascimento = SCANNER.nextLine();
        System.out.print("CPF: ");
        String cpf = SCANNER.nextLine();
        System.out.print("Cidade: ");
        String cidade = SCANNER.nextLine();
        PessoaRepository.save(nome, sobreNome, dataNascimento, cpf, cidade, login, senha);
    }

    private static boolean login() {
        System.out.println("Digite seu usuário de login");
        String login = SCANNER.nextLine();
        if (!PessoaRepository.login(login)) {
            return false;

        }
        System.out.printf("Usuário: %s \n", login);
        System.out.println("Digite sua senha");
        String senha = SCANNER.nextLine();
        if (!PessoaRepository.senha(login, senha)) {
            System.out.println("Senha incorreta. Login falhou.");
            return false;
        }

        usuarioLogado = login;
        return true;

    }

    private static void menuPessoal() {
        System.out.println("Digite o número da sua operação");
        System.out.println("1. Criar evento");
        System.out.println("2. Confirmar presença em evento");
        System.out.println("3. Remover presença em evento");
        System.out.println("4. Ver eventos confirmados");
        System.out.println("0. Sair");
    }

}
