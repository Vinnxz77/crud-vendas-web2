package br.edu.vendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.*;

@Entity
@Table(name = "tb_categoria")
public class Categoria extends Entidade {
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoriaPai;

    @OneToMany(
            mappedBy = "categoriaPai",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Categoria> subcategorias = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String v) {
        nome = v;
    }

    public Categoria getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(Categoria v) {
        categoriaPai = v;
    }

    public List<Categoria> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(List<Categoria> v) {
        subcategorias = v;
    }

    public String getCaminho() {
        LinkedList<String> nomes = new LinkedList<>();
        Set<Long> ids = new HashSet<>();
        Categoria c = this;
        while (c != null) {
            if (c.getId() != null && !ids.add(c.getId()))
                throw new IllegalStateException("Ciclo na hierarquia.");
            nomes.addFirst(c.getNome());
            c = c.getCategoriaPai();
        }
        return String.join(" > ", nomes);
    }

    public int getNivel() {
        int n = 0;
        Categoria c = categoriaPai;
        Set<Long> ids = new HashSet<>();
        while (c != null) {
            if (c.getId() != null && !ids.add(c.getId()))
                throw new IllegalStateException("Ciclo na hierarquia.");
            n++;
            c = c.getCategoriaPai();
        }
        return n;
    }

    public String getRotulo() {
        return "— ".repeat(getNivel()) + nome;
    }
}
