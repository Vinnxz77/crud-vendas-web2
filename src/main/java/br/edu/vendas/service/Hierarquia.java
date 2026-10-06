package br.edu.vendas.service;

import br.edu.vendas.model.Categoria;

import java.util.*;

public final class Hierarquia {
    private Hierarquia() {}

    public static void validarPai(Long editada, Categoria pai) {
        Set<Long> visitados = new HashSet<>();
        while (pai != null) {
            Long id = pai.getId();
            if (id != null && (id.equals(editada) || !visitados.add(id)))
                throw new RegraException(
                        "Uma categoria não pode ser filha de si mesma ou de uma descendente.");
            pai = pai.getCategoriaPai();
        }
    }
}
