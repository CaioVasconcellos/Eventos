package trabalho.eventos.repository;

import lombok.extern.log4j.Log4j2;
import trabalho.eventos.dominio.Pessoa;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Log4j2
public class PessoaRepository {

    private static final String CAMINHO_ARQUIVO = "C:\\Users\\Caio Eduardo\\IdeaProjects\\Eventos\\src\\main\\java\\trabalho\\eventos\\repository\\pessoa.file";

    public static void save(String name, String sobreNome, String dataDeNascimento, String cpf, String cidade, String login, String senha) {
        Pessoa pessoa = Pessoa.builder()
                .name(name)
                .sobreNome(sobreNome)
                .dataDeNascimento(dataDeNascimento)
                .cpf(cpf)
                .cidade(cidade)
                .login(login)
                .senha(senha)
                .build();
        System.out.printf("Bem vindo %s!", pessoa.name());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
            writer.write("\n" + pessoa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean login(String login) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String loginValido = reader.lines().filter(line -> line.contains("login=" + login))
                    .findAny()
                    .orElse(null);
            if (loginValido != null) {
                log.info("Login bem-sucedido para: " + login);
            } else {
                log.info("Login falhou. Usuário não encontrado.");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static Pessoa getPessoaByLogin(String login) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("login=" + login)) {
                    String[] tokens = line.split(",");
                    String nome = tokens[0].substring(tokens[1].indexOf("=") + 1).trim();
                    String sobrenome = tokens[1].substring(tokens[1].indexOf("=") + 1).trim();
                    String data = tokens[2].substring(tokens[1].indexOf("=") + 1).trim();
                    return Pessoa.builder()
                            .name(nome)
                            .dataDeNascimento(data)
                            .build();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo de pessoas.", e);
        }
        return null;
    }


    public static boolean senha(String login, String senha) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String senhaAssociada = reader.lines()
                    .filter(line -> line.contains("login=" + login + ", senha="))
                    .map(line -> line.substring(line.indexOf("senha=") + 6, line.indexOf("]")).trim())
                    .findAny()
                    .orElse(null);
            if (senhaAssociada != null && senhaAssociada.equals(senha)) {
                log.info("Senha correta para o login.");
            } else {
                log.info("Senha incorreta para o login.");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShowRepository.notificarProximoEvento();
        return true;
    }

    public static boolean validacaoLogin(String login) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            return reader.lines().anyMatch(line -> line.contains("login=" + login));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validarFormatoDataHora(String dia, String hora) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate.parse(dia, dateFormatter);
            LocalTime.parse(hora, timeFormatter);

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}



