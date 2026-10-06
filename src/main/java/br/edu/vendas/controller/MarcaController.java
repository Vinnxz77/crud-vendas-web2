package br.edu.vendas.controller;

import br.edu.vendas.model.*;
import br.edu.vendas.service.*;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.*;

import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class MarcaController implements Serializable {
    @Inject CatalogoService service;
    private Marca editando = new Marca();
    private List<Marca> lista;

    public Marca getEditando() {
        return editando;
    }

    public List<Marca> getLista() {
        if (lista == null) atualizar();
        return lista;
    }

    public void atualizar() {
        try {
            lista = service.marcas();
        } catch (Exception e) {
            lista = new ArrayList<>();
            Mensagens.erro(e);
        }
    }

    public void novo() {
        editando = new Marca();
    }

    public void editar(Marca item) {
        try {
            editando = service.buscar(Marca.class, item.getId());
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
            Mensagens.sucesso("Marca salva com sucesso.");
        } catch (Exception e) {
            Mensagens.erro(e);
        }
    }

    public void excluir(Marca item) {
        try {
            service.excluirMarca(item.getId());
            atualizar();
            Mensagens.sucesso("Marca excluída.");
        } catch (Exception e) {
            Mensagens.erro(e);
        }
    }
}
