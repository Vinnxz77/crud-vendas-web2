package br.edu.iftm.ppw2.aula1.logic;

import br.edu.iftm.ppw2.aula1.util.exception.ErroSistemaException;
import java.io.Serializable;
import java.util.List;

public interface GenericLogic<E> extends Serializable{
    
    public void salvar(E entidade) throws ErroSistemaException;
    public void deletar(E entidade);
    public List<E> listar();
}
