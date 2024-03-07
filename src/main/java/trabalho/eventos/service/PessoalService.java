package trabalho.eventos.service;

import trabalho.eventos.dominio.TipoShow;
import trabalho.eventos.repository.PessoaRepository;
import trabalho.eventos.repository.PresencaRepository;
import trabalho.eventos.repository.ShowRepository;

import java.util.Scanner;

import static trabalho.eventos.service.PessoaService.usuarioLogado;

public class PessoalService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void menuPessoal(int op) {
        switch (op) {
            case 1 -> criarShow();
            case 2 -> confirmarPresenca();
            case 3 -> removerPresenca();
            case 4 -> verEventos();

        }
    }

    private static void criarShow() {
        System.out.println("Digite o número para criar seu show");
        System.out.println("1. Show");
        System.out.println("2. Festa");
        System.out.println("3. Infantil");
        System.out.println("4. Evento Esportivo");
        System.out.println("9. Voltar");
        TipoShow menu = TipoShow.menu(SCANNER.nextShort());
        SCANNER.nextLine();
        System.out.print("Nome do Show: ");
        String nome = SCANNER.nextLine();
        System.out.print("Endereço: ");
        String endereco = SCANNER.nextLine();
        System.out.print("Cidade: ");
        String cidade = SCANNER.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(SCANNER.nextLine());

        String dia;
        String hora;
        boolean formatoValido;
        do {
            System.out.print("Dia (dd/MM/yyyy): ");
            dia = SCANNER.nextLine();
            System.out.print("Hora (HH:mm): ");
            hora = SCANNER.nextLine();
            formatoValido = PessoaRepository.validarFormatoDataHora(dia,hora);

            if (!formatoValido) {
                System.out.println("Formato de data ou hora inválido. Por favor, tente novamente.");
            }
        } while (!formatoValido);
        ShowRepository.criar(menu, nome, endereco, cidade, idade, dia, hora);
    }

    private static void confirmarPresenca() {
        System.out.println("Agora digite o show que quer confirmar presença");
        String show = SCANNER.nextLine();
        PresencaRepository.confirmarPresenca(usuarioLogado, show);

    }
    private static void removerPresenca() {
        System.out.println("Agora digite o show que quer remover a presença");
        String show = SCANNER.nextLine();
        PresencaRepository.removerPresenca(usuarioLogado, show);

    }



    private static void verEventos() {
        PresencaRepository.exibirEventosConfirmados(usuarioLogado);
    }


}