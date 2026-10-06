package br.edu.vendas.service;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.validation.*;

@ApplicationScoped
public class ValidationProducer {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Produces
    public Validator validator() {
        return factory.getValidator();
    }

    @PreDestroy
    public void fechar() {
        factory.close();
    }
}
