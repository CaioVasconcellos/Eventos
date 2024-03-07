package trabalho.eventos.dominio;

public enum TipoShow {
    SHOW(1,"SHOW"),
    FESTA(2,"FESTA"),

    INFANTIL(3,"INFANTIL"),

    EVENTO_ESPORTIVO(4,"EVENTO-ESPORTIVO");

    public int valor;
    public String nomeShow;
    TipoShow(int valor, String nomeShow) {
        this.valor = valor;
        this.nomeShow = nomeShow;

    }

    public static TipoShow menu(int op) {
        return switch (op) {
            case 1 -> SHOW;
            case 2 -> FESTA;
            case 3 -> INFANTIL;
            case 4 -> EVENTO_ESPORTIVO;
            default -> throw new IllegalArgumentException("Opção inválida: " + op);
        };
    }
}
