package comprraqui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Relacionamento: Categoria ◇ Produto — AGREGACAO.
 * A Categoria apenas agrupa Produtos que existem por conta propria no
 * catalogo. Se a Categoria for removida, os Produtos continuam existindo.
 */
public class Categoria {

    private final String nome;
    private final List<Produto> produtos = new ArrayList<>();

    public Categoria(String nome) {
        this.nome = nome;
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public String getNome() {
        return nome;
    }

    /** Lista somente-leitura: quem chama nao pode alterar a colecao interna. */
    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }
}
