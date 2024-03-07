package trabalho.eventos.repository;

import lombok.extern.log4j.Log4j2;
import trabalho.eventos.dominio.Pessoa;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
public class PresencaRepository {

    private static final String CAMINHO_SHOW = "C:\\Users\\Caio Eduardo\\IdeaProjects\\Eventos\\src\\main\\java\\trabalho\\eventos\\repository\\show.file";
    private static final String PRESENCA_SHOW = "C:\\Users\\Caio Eduardo\\IdeaProjects\\Eventos\\src\\main\\java\\trabalho\\eventos\\repository\\presenca.file";

    public static void confirmarPresenca(String login, String nomeShow) {
        if (login != null) {
            if (verificarPresencaConfirmada(login, nomeShow)) {
                System.out.printf("Você já confirmou presença no evento %s.%n", nomeShow);
            } else {
                try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_SHOW))) {
                    String line;
                    boolean showEncontrado = false;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("nome=" + nomeShow)) {
                            showEncontrado = true;
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRESENCA_SHOW, true))) {
                                writer.write("\n" + login + " - " + nomeShow);
                            } catch (IOException e) {
                                throw new RuntimeException("Erro ao escrever no arquivo de presenças.", e);
                            }
                            System.out.printf("Presença confirmada para no show %s.%n", nomeShow);
                            break;
                        }
                    }
                    if (!showEncontrado) {
                        log.info("Show não encontrado. Presença não confirmada.");
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Erro ao ler o arquivo de shows.", e);
                }
            }
        } else {
            log.info("Pessoa não encontrada. Presença não confirmada.");
        }
    }

    private static boolean verificarPresencaConfirmada(String login, String nomeShow) {
        String pessoaShow = login + " - " + nomeShow;
        try (BufferedReader reader = new BufferedReader(new FileReader(PRESENCA_SHOW))) {
            return reader.lines().anyMatch(line -> line.contains(pessoaShow));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar presença confirmada.", e);
        }
    }


    public static void exibirEventosConfirmados(String login) {
        Pessoa pessoa = PessoaRepository.getPessoaByLogin(login);
        if (login != null) {
            String pessoaShow = login + " - ";
            try (BufferedReader reader = new BufferedReader(new FileReader(PRESENCA_SHOW))) {
                List<String> eventosConfirmados = reader.lines()
                        .filter(line -> line.contains(pessoaShow))
                        .map(line -> line.substring(line.indexOf(" - ") + 3))
                        .toList();
                System.out.println("Eventos encontrados para " + (pessoa != null ? pessoa.name().substring(2) : null) + ":");
                eventosConfirmados.forEach(System.out::println);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao verificar presença confirmada.", e);
            }
        } else {
            System.out.println("Login não encontrado. Não há eventos confirmados.");
        }
    }

    public static void removerPresenca(String login, String nomeShow) {
        if (login != null) {
            if (verificarPresencaConfirmada(login,nomeShow)) {
                try {
                    Path path = Paths.get(PRESENCA_SHOW);
                    List<String> linhas = Files.readAllLines(path);
                    linhas.removeIf(line -> line.contains(login) && line.contains(nomeShow));
                    Files.write(path, linhas);
                    System.out.printf("Presença removida para o show %s.%n", nomeShow);
                } catch (IOException e) {
                    throw new RuntimeException("Erro ao remover presença do arquivo de presenças.", e);
                }
            } else {
                System.out.printf("Show %s não encontrado. Não é possível remover presença.%n", nomeShow);
            }
        } else {
            log.info("Login não encontrado. Não é possível remover presença.");
        }
    }

}


