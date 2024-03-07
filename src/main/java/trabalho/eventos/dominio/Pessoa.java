package trabalho.eventos.dominio;


import lombok.Builder;


@Builder
public record Pessoa(String name, String sobreNome, String dataDeNascimento, String cpf, String cidade, String login, String senha) {

}
