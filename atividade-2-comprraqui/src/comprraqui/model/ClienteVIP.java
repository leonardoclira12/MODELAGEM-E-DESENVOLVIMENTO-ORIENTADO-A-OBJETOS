package comprraqui.model;

/**
 * Pilar OO: HERANCA — ClienteVIP estende Cliente e acrescenta o atributo
 * especifico descontoProgressivo, alem de redefinir calcularFrete().
 */
public class ClienteVIP extends Cliente {

    /** Percentual de desconto progressivo, ex.: 0.10 = 10%. */
    private final double descontoProgressivo;

    public ClienteVIP(String nome, String documento, String endereco, double descontoProgressivo) {
        super(nome, documento, endereco);
        this.descontoProgressivo = descontoProgressivo;
    }

    /**
     * Cliente VIP tem frete gratis.
     */
    @Override
    public double calcularFrete() {
        return 0.0;
    }

    public double getDescontoProgressivo() {
        return descontoProgressivo;
    }
}
