package br.edu.iftm.ppw2.aula1.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author danilo
 */
@Named
@SessionScoped
public class HelloBean implements Serializable {
    
    private String hello = "Hello JSF - Minha Aplicação está rodando...";
    private String nome;
    
    public String getHello() {
        return hello;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
    
    
    
}
