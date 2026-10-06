package br.edu.iftm.ppw2.aula1.bean;

import br.edu.iftm.ppw2.aula1.logic.GenericLogic;
import br.edu.iftm.ppw2.aula1.util.JSFUtil;
import br.edu.iftm.ppw2.aula1.util.exception.ErroSistemaException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericCrud<E, L extends GenericLogic<E>> extends JSFUtil{

    private E entidade;
    private List<E> entidades = new ArrayList<>();

    private EstadoCrud estadoCrud = EstadoCrud.LISTAR;
    
    public enum  EstadoCrud {
        CRIAR ,
        ATUALIZAR,
        LISTAR
    }
    
    public void novo() {
        entidade = novaEntiade();
        estadoCrud = EstadoCrud.CRIAR;
    }
    
    public void editar(E entidade) {
        this.entidade = entidade;
        estadoCrud = EstadoCrud.ATUALIZAR;
    }
    
    public void salvar() {
        try {
            getLogic().salvar(entidade);
            addInfo("Salvo com sucesso");
            estadoCrud =  EstadoCrud.LISTAR;
        } catch (ErroSistemaException ex) {
            addErro(ex);
            System.getLogger(GenericCrud.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch(Exception ex) {
            addErro("Erro geral  no sistema.");
            System.getLogger(GenericCrud.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);

        }
    }

    public void listar() {
        if(!estadoCrud.equals(EstadoCrud.LISTAR)) {
            estadoCrud = EstadoCrud.LISTAR;
            return;
        }
        entidades = getLogic().listar();
        if(entidades == null || entidades.isEmpty()) {
            addAviso("Nenhum item encontrado.");
        }
    }

    public E getEntidade() {
        return entidade;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public EstadoCrud getEstadoCrud() {
        return estadoCrud;
    }
    
    public abstract E novaEntiade() ;
    public abstract L getLogic();
}
