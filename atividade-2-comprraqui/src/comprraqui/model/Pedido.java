package comprraqui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Relacionamento: Cliente 1 —— * Pedido — ASSOCIACAO
 * (um cliente faz muitos pedidos).
 *
 * Relacionamento: Pedido ◆ ItemPedido — COMPOSICAO
 * (os itens vivem e morrem junto com o pedido).
 *
 * Pilar OO: ENCAPSULAMENTO — o campo estado e privado e so anda por
 * operacoes (adicionarItem, finalizar, pagar, enviar, entregar, cancelar);
 * nunca e alterado "na mao" de fora da classe.
 *
 * Estados possiveis: NOVO -> PAGO -> ENVIADO -> ENTREGUE, ou CANCELADO
 * (a partir de NOVO ou PAGO).
 *
 * Observacao de projeto: o enunciado lista finalizar() e pagar() como
 * operacoes distintas. Aqui, finalizar() "fecha o carrinho" (trava a
 * lista de itens, nao aceita mais adicionarItem) e pagar() e onde a
 * regra critica e aplicada: nao pode finalizar/pagar um pedido com
 * produto sem estoque, e e so em pagar() que o estoque de fato baixa.
 */
public class Pedido {

    private static final String NOVO = "novo";
    private static final String PAGO = "pago";
    private static final String ENVIADO = "enviado";
    private static final String ENTREGUE = "entregue";
    private static final String CANCELADO = "cancelado";

    private final Cliente cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private String estado;
    private boolean carrinhoFechado;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.estado = NOVO;
        this.carrinhoFechado = false;
    }

    /**
     * Pilar OO/Relacionamento: COMPOSICAO — e o proprio Pedido quem cria
     * o ItemPedido; quem chama este metodo nunca constroi um ItemPedido
     * diretamente, reforcando que o item nao existe fora do pedido.
     */
    public void adicionarItem(Produto produto, int quantidade) {
        if (carrinhoFechado) {
            throw new IllegalStateException("Nao e possivel adicionar itens: pedido ja foi finalizado.");
        }
        itens.add(new ItemPedido(produto, quantidade));
    }

    /**
     * Fecha a lista de itens do pedido. A partir daqui nenhum item novo
     * pode ser adicionado, mas o pedido ainda nao foi pago.
     */
    public void finalizar() {
        exigirEstado(NOVO, "finalizar");
        if (itens.isEmpty()) {
            throw new IllegalStateException("Nao e possivel finalizar um pedido sem itens.");
        }
        carrinhoFechado = true;
    }

    /**
     * Regra critica: NAO pode confirmar pagamento de um pedido com
     * produto sem estoque. Verifica o estoque de todos os itens antes de
     * baixar qualquer um deles (tudo ou nada), e so entao muda o estado.
     */
    public void pagar() {
        exigirEstado(NOVO, "pagar");
        if (!carrinhoFechado) {
            throw new IllegalStateException("Chame finalizar() antes de pagar().");
        }
        for (ItemPedido item : itens) {
            if (!item.getProduto().temEstoqueDisponivel(item.getQuantidade())) {
                throw new IllegalStateException(
                        "Nao e possivel pagar: produto sem estoque suficiente -> " + item.getProduto().getNome());
            }
        }
        for (ItemPedido item : itens) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }
        estado = PAGO;
    }

    public void enviar() {
        exigirEstado(PAGO, "enviar");
        estado = ENVIADO;
    }

    public void entregar() {
        exigirEstado(ENVIADO, "entregar");
        estado = ENTREGUE;
    }

    /**
     * Cancela o pedido enquanto ainda nao foi enviado. Se o pedido ja
     * estava pago (estoque ja baixado), devolve o estoque de cada item.
     */
    public void cancelar() {
        if (!estado.equals(NOVO) && !estado.equals(PAGO)) {
            throw new IllegalStateException("So e possivel cancelar um pedido novo ou pago (antes do envio).");
        }
        if (estado.equals(PAGO)) {
            for (ItemPedido item : itens) {
                item.getProduto().devolverEstoque(item.getQuantidade());
            }
        }
        estado = CANCELADO;
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    private void exigirEstado(String estadoEsperado, String operacao) {
        if (!estado.equals(estadoEsperado)) {
            throw new IllegalStateException(
                    "Nao e possivel " + operacao + "(): estado atual e '" + estado + "', esperado '" + estadoEsperado + "'.");
        }
    }

    public String getEstado() {
        return estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }
}
