package br.edu.vendas.repository;

import br.edu.vendas.model.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.*;
import java.util.function.Function;

@ApplicationScoped
public class CatalogoRepository {
    @Inject Banco banco;

    public <T> T transacao(Function<EntityManager, T> acao) {
        return banco.executar(acao);
    }

    public List<Marca> marcas() {
        return transacao(
                em ->
                        em.createQuery("select m from Marca m order by m.nome", Marca.class)
                                .getResultList());
    }

    public List<Categoria> categorias() {
        return transacao(
                em -> {
                    List<Categoria> cs =
                            em.createQuery("select c from Categoria c", Categoria.class)
                                    .getResultList();
                    cs.forEach(Categoria::getCaminho);
                    cs.sort(
                            Comparator.comparing(
                                    Categoria::getCaminho, String.CASE_INSENSITIVE_ORDER));
                    return cs;
                });
    }

    public List<Produto> produtos() {
        return transacao(
                em -> {
                    List<Produto> ps =
                            em.createQuery(
                                            "select p from Produto p join fetch p.marca join fetch"
                                                + " p.categoria order by p.nome",
                                            Produto.class)
                                    .getResultList();
                    ps.forEach(p -> p.getCategoria().getCaminho());
                    return ps;
                });
    }

    public <T extends Entidade> T buscar(Class<T> tipo, Long id) {
        return transacao(
                em -> {
                    T e = em.find(tipo, id);
                    if (e instanceof Categoria c) c.getCaminho();
                    return e;
                });
    }
}
