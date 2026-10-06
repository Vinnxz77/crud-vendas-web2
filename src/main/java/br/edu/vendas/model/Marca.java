package br.edu.vendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.*;

@Entity
@Table(name = "tb_marca")
public class Marca extends Entidade {
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Size(max = 500)
    @Column(length = 500)
    private String descricao;

    @NotNull
    @Column(nullable = false)
    private Boolean ativo = true;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean v) {
        ativo = v;
    }
}
