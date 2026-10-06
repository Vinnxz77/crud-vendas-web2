package br.edu.iftm.ppw2.aula1.bean;

import br.edu.iftm.ppw2.aula1.entity.Usuario;
import br.edu.iftm.ppw2.aula1.logic.UsuarioLogic;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class UsuarioBean extends GenericCrud<Usuario, UsuarioLogic>{

    @Inject
    private UsuarioLogic logic;
    
    @Override
    public Usuario novaEntiade() {
        return new Usuario();
    }

    @Override
    public UsuarioLogic getLogic() {
        return logic;
    }
    
   
}
