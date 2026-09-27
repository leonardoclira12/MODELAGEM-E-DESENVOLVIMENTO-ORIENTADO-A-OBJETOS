package comprraqui.model;

/**
 * Classe base abstrata que representa um cliente da Loja ComprAqui.
 *
 * Pilar OO: ABSTRACAO — Cliente nunca e instanciada diretamente, apenas
 * seus subtipos concretos (ClienteComum, ClienteVIP).
 *
 * Pilar OO: ENCAPSULAMENTO — os atributos sao privados e so podem ser
 * lidos por meio dos getters; nada fora da classe pode alterar nome,
 * documento ou endereco diretamente.
 */
public abstract class Cliente {

    private final String nome;
    private final String documento;
    private final String endereco;

    protected Cliente(String nome, String documento, String endereco) {
        this.nome = nome;
        this.documento = documento;
        this.endereco = endereco;
    }

    /**
     * Pilar OO: POLIMORFISMO — cada subtipo de Cliente redefine esta
     * operacao com uma regra de frete diferente.
     *
     * @return valor do frete a ser cobrado
     */
    public abstract double calcularFrete();

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{nome='" + nome + "'}";
    }
}
