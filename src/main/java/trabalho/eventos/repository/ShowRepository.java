package trabalho.eventos.repository;

import lombok.extern.log4j.Log4j2;
import trabalho.eventos.dominio.Show;
import trabalho.eventos.dominio.TipoShow;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ShowRepository {


    private static final String CAMINHO_SHOW = "C:\\Users\\Caio Eduardo\\IdeaProjects\\Eventos\\src\\main\\java\\trabalho\\eventos\\repository\\show.file";


    public static void criar(TipoShow tipoShow, String nome, String endereco, String cidade, int idade, String dia, String hora) {
        Show show = Show.builder().tipoShow(tipoShow).nome(nome).endereco(endereco).cidade(cidade).idade(idade).dia(dia).hora(hora).build();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_SHOW, true))) {
            writer.write("\n" + show);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Show> filtrarShow(TipoShow tipoShow) {
        List<Show> showsFiltrados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_SHOW))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toUpperCase().contains(("tipoShow=" + tipoShow).toUpperCase())) {
                    ArquivoShow(showsFiltrados, line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo de shows.", e);
        }

        return showsFiltrados;
    }

    private static List<Show> carregarShowsDoArquivo() {
        List<Show> shows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_SHOW))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ArquivoShow(shows, line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo de shows.", e);
        }

        return shows;
    }

    private static void ArquivoShow(List<Show> shows, String line) {
        String[] tokens = line.split(",");
        try {
            String nome = tokens.length >= 2 ? tokens[1].substring(tokens[1].indexOf("=") + 1).trim() : "";
            String endereco = tokens.length >= 3 ? tokens[2].substring(tokens[2].indexOf("=") + 1).trim() : "";
            String cidade = tokens.length >= 4 ? tokens[3].substring(tokens[3].indexOf("=") + 1).trim() : "";
            int idade = tokens.length >= 5 ? Integer.parseInt(tokens[4].substring(tokens[4].indexOf("=") + 1).trim()) : 0;
            String dia = tokens.length >= 6 ? tokens[5].substring(tokens[5].indexOf("=") + 1).trim() : "";
            String hora = tokens.length >= 7 ? tokens[6].substring(tokens[6].indexOf("=") + 1, tokens[6].indexOf("]")).trim() : "";
            Show show = Show.builder().nome(nome).endereco(endereco).cidade(cidade).idade(idade).dia(dia).hora(hora).build();

            shows.add(show);
        } catch (IllegalArgumentException e) {
            log.info("Erro ao converter tipo show: " + tokens[0].substring(tokens[0].indexOf("=") + 1).trim());

        }
    }


    public static void verShow(TipoShow tipoShow) {
        List<Show> shows = filtrarShow(tipoShow);
        System.out.println("Mostrando shows: ");
        for (Show show : shows) {
            detalhesEventoSimples(show);
        }
    }

    public static void detalhesEventoPorNome(String nomeEvento) {
        List<Show> shows = carregarShowsDoArquivo();
        List<Show> showsFiltrados = shows.stream().filter(show -> show.nome().equalsIgnoreCase(nomeEvento)).toList();
        if (showsFiltrados.isEmpty()) {
            System.out.println("Nenhum show encontrado com o nome: " + nomeEvento);
        } else {
            showsFiltrados.forEach(ShowRepository::detalhesEventoCompleto);
        }
    }

    private static void detalhesEventoCompleto(Show show) {
        System.out.printf("Detalhes do Evento: %s%n Cidade: %s%n Endereço: %s%n Dia: %s%n Hora: %s%n Idade:%d%n", show.nome(), show.cidade(), show.endereco(), show.dia(), show.hora(), show.idade());
    }
    private static void detalhesEventoSimples(Show show) {
        System.out.printf("Evento: %s%nCidade: %s Dia: %s%n", show.nome(), show.cidade(),show.dia());
    }

    public static void notificarProximoEvento() {
        List<Show> shows = carregarShowsDoArquivo();
        Show showMaisProximo = encontrarProximoEvento(shows);

        if (showMaisProximo != null) {
            System.out.println("Próximo evento: ");
            detalhesEventoCompleto(showMaisProximo);
        } else {
            System.out.println("Não há shows próximos no momento.");
        }
    }

    private static Show encontrarProximoEvento(List<Show> shows) {
        LocalDateTime agora = LocalDateTime.now();
        Show showMaisProximo = null;
        long minutosAteProximoShow = Long.MAX_VALUE;
        for (Show show : shows) {
            String dataHoraString = show.dia() + " " + show.hora();
            if (!dataHoraString.trim().isEmpty()) {
                try {
                    LocalDateTime dataHoraShow = LocalDateTime.parse(dataHoraString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    long minutosAteShow = agora.until(dataHoraShow, ChronoUnit.MINUTES);
                    if (minutosAteShow >= 0 && minutosAteShow < minutosAteProximoShow) {
                        minutosAteProximoShow = minutosAteShow;
                        showMaisProximo = show;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Erro ao fazer parse da data/hora: " + dataHoraString);
                }
            }
        }

        return showMaisProximo;
    }

}











