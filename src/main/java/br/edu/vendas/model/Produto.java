package br.edu.vendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "tb_produto")
public class Produto extends Entidade {
    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @Column(columnDefinition = "text")
    private String descricao;

    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @NotNull
    @Min(0)
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque = 0;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "caminho_imagem", length = 100)
    private String caminhoImagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String v) {
        nome = v;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String v) {
        descricao = v;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal v) {
        preco = v;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer v) {
        quantidadeEstoque = v;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca v) {
        marca = v;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria v) {
        categoria = v;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String v) {
        caminhoImagem = v;
    }
}
