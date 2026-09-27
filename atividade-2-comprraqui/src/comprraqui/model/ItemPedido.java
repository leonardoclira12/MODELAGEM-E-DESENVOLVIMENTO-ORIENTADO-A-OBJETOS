package comprraqui.model;

/**
 * Relacionamento: Pedido ◆ ItemPedido — COMPOSICAO.
 * Um ItemPedido so existe dentro de um Pedido; e criado e destruido junto
 * com ele (nao e passado como parametro solto pela aplicacao, so e
 * construido a partir de um Pedido — ver Pedido.adicionarItem).
 *
 * Pilar OO: ENCAPSULAMENTO — precoUnitario e "congelado" no momento da
 * criacao do item (final) e nunca muda depois, mesmo que o preco do
 * Produto mude no catalogo no futuro.
 */
public class ItemPedido {

    private final Produto produto;
    private final int quantidade;
    private final double precoUnitario;

    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public double getSubtotal() {
        return precoUnitario * quantidade;
    }
}
