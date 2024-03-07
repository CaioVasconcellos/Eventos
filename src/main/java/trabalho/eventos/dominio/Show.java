package trabalho.eventos.dominio;

import lombok.Builder;

@Builder
public record Show(TipoShow tipoShow,String nome, String endereco, String cidade, int idade, String dia, String hora) {
}


