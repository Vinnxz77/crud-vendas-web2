package br.edu.iftm.ppw2.aula1.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author danilo
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
    
    @EqualsAndHashCode.Include
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String  salt;
    
}
