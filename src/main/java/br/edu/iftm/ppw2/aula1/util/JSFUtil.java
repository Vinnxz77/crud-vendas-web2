package br.edu.iftm.ppw2.aula1.util;

import br.edu.iftm.ppw2.aula1.util.exception.ErroSistemaException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

public class JSFUtil implements Serializable {
    
    public void addMensagem(String titulo, String detalhe, FacesMessage.Severity tipo) {
        FacesMessage fm = new FacesMessage(tipo, titulo, detalhe);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }
    
    public void addInfo(String titulo, String detalhe) {
        addMensagem(titulo, detalhe, FacesMessage.SEVERITY_INFO);
    }
    public void addInfo(String detalhe) {
        addInfo("Info", detalhe);
    }
    public void addAviso(String titulo, String detalhe) {
        addMensagem(titulo, detalhe, FacesMessage.SEVERITY_WARN);
    }
    public void addAviso(String detalhe) {
        addAviso("Aviso", detalhe);
    }
    public void addErro(String titulo, String detalhe) {
        addMensagem(titulo, detalhe, FacesMessage.SEVERITY_ERROR);
    }
    public void addErro(String detalhe) {
        addErro("Erro", detalhe);
    }
     public void addErro(Exception ex) {
        addErro(ex.getMessage());
    }
}
