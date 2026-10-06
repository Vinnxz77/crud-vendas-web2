package br.edu.vendas.model;

import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public abstract class Entidade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Entidade e
                && getClass() == e.getClass()
                && id != null
                && id.equals(e.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
