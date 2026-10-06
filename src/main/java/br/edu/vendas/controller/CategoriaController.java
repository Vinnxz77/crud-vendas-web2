package br.edu.vendas.controller;

import br.edu.vendas.model.*;
import br.edu.vendas.service.*;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.*;

import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class CategoriaController implements Serializable {
    @Inject CatalogoService service;
    private Categoria editando = new Categoria();
    private List<Categoria> lista;

    public Categoria getEditando() {
        return editando;
    }

    public List<Categoria> getLista() {
        if (lista == null) atualizar();
        return lista;
    }

    public void atualizar() {
        try {
            lista = service.categorias();
        } catch (Exception e) {
            lista = new ArrayList<>();
            Mensagens.erro(e);
        }
    }

    public void novo() {
        editando = new Categoria();
    }

    public void editar(Categoria item) {
        try {
            editando = service.buscar(Categoria.class, item.getId());
            if (editando == null) throw new RegraException("Registro excluído.");
        } catch (Exception e) {
            Mensagens.erro(e);
        }
    }

    public void salvar() {
        try {
            service.salvar(editando);
            novo();
            atualizar();
            Mensagens.sucesso("Categoria salva com sucesso.");
        } catch (Exception e) {
            Mensagens.erro(e);
        }
    }

    public void excluir(Categoria item) {
        try {
            service.excluirCategoria(item.getId());
            atualizar();
            Mensagens.sucesso("Categoria excluída.");
        } catch (Exception e) {
            Mensagens.erro(e);
        }
    }

    public List<Categoria> getPais() {
        return getLista().stream()
                .filter(
                        c -> {
                            try {
                                Hierarquia.validarPai(editando.getId(), c);
                                return true;
                            } catch (RegraException e) {
                                return false;
                            }
                        })
                .toList();
    }
}
