package br.edu.iftm.ppw2.aula1.logic;

import br.edu.iftm.ppw2.aula1.dao.UsuarioDAO;
import br.edu.iftm.ppw2.aula1.entity.Usuario;
import br.edu.iftm.ppw2.aula1.util.exception.ErroSistemaException;
import java.util.List;

public class UsuarioLogic implements GenericLogic<Usuario> {

    private UsuarioDAO dao = new UsuarioDAO();
    
    @Override
    public void salvar(Usuario entidade) throws ErroSistemaException{
        dao.salvar(entidade);
    }

    @Override
    public void deletar(Usuario entidade) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> listar() {
        return dao.listar();
    }

}
