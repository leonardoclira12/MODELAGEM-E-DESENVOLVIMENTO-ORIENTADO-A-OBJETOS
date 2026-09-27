package comprraqui.model;

/**
 * Pilar OO: HERANCA — ClienteComum estende Cliente e herda nome,
 * documento e endereco da base.
 */
public class ClienteComum extends Cliente {

    private static final double TAXA_FRETE_PADRAO = 25.0;

    public ClienteComum(String nome, String documento, String endereco) {
        super(nome, documento, endereco);
    }

    /**
     * Cliente comum paga o frete normal (nao tem beneficio de frete gratis).
     */
    @Override
    public double calcularFrete() {
        return TAXA_FRETE_PADRAO;
    }
}
