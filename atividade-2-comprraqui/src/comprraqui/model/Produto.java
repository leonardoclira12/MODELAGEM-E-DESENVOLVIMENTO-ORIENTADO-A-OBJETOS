package comprraqui.model;

/**
 * Pilar OO: ENCAPSULAMENTO — o estoque e privado e so muda por
 * baixarEstoque(), que garante o invariante "estoque nunca fica negativo".
 *
 * Relacionamento: Categoria (agregacao ◇) — um Produto existe sozinho no
 * catalogo, com ou sem Categoria associada.
 */
public class Produto {

    private final String nome;
    private final double preco;
    private int estoque;

    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public boolean temEstoqueDisponivel(int quantidade) {
        return estoque >= quantidade;
    }

    /**
     * Baixa o estoque em quantidade unidades.
     * Garante que o estoque nunca fique negativo.
     */
    public void baixarEstoque(int quantidade) {
        if (!temEstoqueDisponivel(quantidade)) {
            throw new IllegalStateException(
                    "Estoque insuficiente para o produto '" + nome + "'. Disponivel: " + estoque);
        }
        estoque -= quantidade;
    }

    /**
     * Usado ao cancelar um pedido ja pago, para devolver o estoque baixado.
     */
    public void devolverEstoque(int quantidade) {
        estoque += quantidade;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }
}
