package br.edu.vendas.controller;

import br.edu.vendas.service.RegraException;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public final class Mensagens {
    private Mensagens() {}

    public static void sucesso(String texto) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    public static void erro(Exception e) {
        String msg =
                e instanceof RegraException
                        ? e.getMessage()
                        : "Não foi possível concluir. Verifique os dados e a conexão com o banco.";
        if (!(e instanceof RegraException))
            java.util.logging.Logger.getLogger(Mensagens.class.getName())
                    .log(java.util.logging.Level.SEVERE, "Erro no catálogo", e);
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        FacesContext.getCurrentInstance().validationFailed();
    }
}
