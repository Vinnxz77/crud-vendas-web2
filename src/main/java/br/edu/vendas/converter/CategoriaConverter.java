package br.edu.vendas.converter;

import br.edu.vendas.model.Categoria;
import br.edu.vendas.service.CatalogoService;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.*;
import jakarta.inject.Inject;

@jakarta.enterprise.context.Dependent
@FacesConverter(value = "categoriaConverter", managed = true)
public class CategoriaConverter implements Converter<Categoria> {
    @Inject CatalogoService service;

    public Categoria getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            Categoria obj = service.buscar(Categoria.class, Long.valueOf(value));
            if (obj == null) throw new IllegalArgumentException();
            return obj;
        } catch (Exception e) {
            throw new ConverterException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Seleção inválida. Atualize a página.",
                            null));
        }
    }

    public String getAsString(FacesContext ctx, UIComponent component, Categoria value) {
        return value == null || value.getId() == null ? "" : value.getId().toString();
    }
}
