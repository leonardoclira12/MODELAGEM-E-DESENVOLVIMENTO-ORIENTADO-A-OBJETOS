package comprraqui;

import comprraqui.model.Categoria;
import comprraqui.model.Cliente;
import comprraqui.model.ClienteComum;
import comprraqui.model.ClienteVIP;
import comprraqui.model.Pedido;
import comprraqui.model.Produto;

/**
 * Demonstra o modelo de classes da Loja ComprAqui em uso: os quatro
 * pilares da OO e os tres relacionamentos (associacao, agregacao,
 * composicao) descritos no diagrama.
 */
public class Main {

    public static void main(String[] args) {
        // --- Catalogo: Categoria (agregacao ◇) agrupa Produtos ---
        Categoria eletronicos = new Categoria("Eletronicos");
        Produto mouse = new Produto("Mouse sem fio", 89.90, 5);
        Produto teclado = new Produto("Teclado mecanico", 249.90, 2);
        eletronicos.adicionarProduto(mouse);
        eletronicos.adicionarProduto(teclado);

        // --- Polimorfismo: mesma chamada, comportamento diferente ---
        Cliente joao = new ClienteComum("Joao Silva", "111.111.111-11", "Rua A, 100");
        Cliente maria = new ClienteVIP("Maria Souza", "222.222.222-22", "Rua B, 200", 0.10);

        System.out.println("Frete Joao (comum): " + joao.calcularFrete());
        System.out.println("Frete Maria (VIP): " + maria.calcularFrete());

        // --- Associacao: Cliente 1 -> * Pedido; Pedido 1 -> * ItemPedido (composicao) ---
        Pedido pedidoJoao = new Pedido(joao);
        pedidoJoao.adicionarItem(mouse, 2);
        pedidoJoao.adicionarItem(teclado, 1);

        pedidoJoao.finalizar();
        pedidoJoao.pagar();
        pedidoJoao.enviar();
        pedidoJoao.entregar();

        System.out.println("Pedido Joao - estado: " + pedidoJoao.getEstado());
        System.out.println("Pedido Joao - total: " + pedidoJoao.calcularTotal());
        System.out.println("Estoque de mouse apos venda: " + mouse.getEstoque());

        // --- Cancelamento devolve estoque ---
        Pedido pedidoMaria = new Pedido(maria);
        pedidoMaria.adicionarItem(teclado, 1);
        pedidoMaria.finalizar();
        pedidoMaria.pagar();
        System.out.println("Estoque de teclado apos pagar: " + teclado.getEstoque());
        pedidoMaria.cancelar();
        System.out.println("Estoque de teclado apos cancelar: " + teclado.getEstoque());

        // --- Invariante: nao pode pagar pedido com produto sem estoque ---
        Pedido pedidoSemEstoque = new Pedido(joao);
        pedidoSemEstoque.adicionarItem(teclado, 10); // so ha 2 em estoque
        pedidoSemEstoque.finalizar();
        try {
            pedidoSemEstoque.pagar();
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado ao pagar sem estoque: " + e.getMessage());
        }
    }
}
