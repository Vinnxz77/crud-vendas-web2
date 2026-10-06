package br.edu.vendas.service;

import br.edu.vendas.model.*;
import br.edu.vendas.repository.CatalogoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.validation.Validator;

import java.util.*;

@ApplicationScoped
public class CatalogoService {
    @Inject CatalogoRepository repo;
    @Inject Validator validator;

    public List<Marca> marcas() {
        return repo.marcas();
    }

    public List<Categoria> categorias() {
        return repo.categorias();
    }

    public List<Produto> produtos() {
        return repo.produtos();
    }

    public <T extends Entidade> T buscar(Class<T> tipo, Long id) {
        return repo.buscar(tipo, id);
    }

    private void validar(Object obj) {
        var erros = validator.validate(obj);
        if (!erros.isEmpty()) throw new RegraException(erros.iterator().next().getMessage());
    }

    private <T> T bloquear(EntityManager em, Class<T> tipo, Long id) {
        T item = em.find(tipo, id, LockModeType.PESSIMISTIC_WRITE);
        if (item == null) throw new RegraException("O registro foi excluído. Atualize a tela.");
        return item;
    }

    public void salvar(Marca m) {
        m.setNome(m.getNome() == null ? null : m.getNome().trim());
        validar(m);
        repo.transacao(
                em -> {
                    Long n =
                            em.createQuery(
                                            "select count(m) from Marca m where"
                                                + " lower(trim(m.nome))=lower(:nome) and (:id is"
                                                + " null or m.id<>:id)",
                                            Long.class)
                                    .setParameter("nome", m.getNome())
                                    .setParameter("id", m.getId())
                                    .getSingleResult();
                    if (n > 0) throw new RegraException("Já existe uma marca com esse nome.");
                    if (m.getId() == null) em.persist(m);
                    else {
                        Marca atual = bloquear(em, Marca.class, m.getId());
                        atual.setNome(m.getNome());
                        atual.setDescricao(m.getDescricao());
                        atual.setAtivo(m.getAtivo());
                    }
                    return null;
                });
    }

    public void salvar(Categoria c) {
        c.setNome(c.getNome() == null ? null : c.getNome().trim());
        validar(c);
        repo.transacao(
                em -> {
                    // Serializa alterações da árvore, impedindo ciclos entre edições concorrentes.
                    em.createNativeQuery("select pg_advisory_xact_lock(20261006)")
                            .getSingleResult();
                    Categoria pai =
                            c.getCategoriaPai() == null
                                    ? null
                                    : bloquear(em, Categoria.class, c.getCategoriaPai().getId());
                    Hierarquia.validarPai(c.getId(), pai);
                    if (c.getId() == null) {
                        c.setCategoriaPai(pai);
                        em.persist(c);
                    } else {
                        Categoria atual = bloquear(em, Categoria.class, c.getId());
                        atual.setNome(c.getNome());
                        atual.setCategoriaPai(pai);
                    }
                    return null;
                });
    }

    public void salvar(Produto p) {
        p.setNome(p.getNome() == null ? null : p.getNome().trim());
        validar(p);
        repo.transacao(
                em -> {
                    Marca m = bloquear(em, Marca.class, p.getMarca().getId());
                    Categoria c = bloquear(em, Categoria.class, p.getCategoria().getId());
                    Produto atual =
                            p.getId() == null ? null : bloquear(em, Produto.class, p.getId());
                    if (!m.getAtivo()
                            && (atual == null || !atual.getMarca().getId().equals(m.getId())))
                        throw new RegraException("Escolha uma marca ativa para o novo vínculo.");
                    if (atual == null) {
                        p.setMarca(m);
                        p.setCategoria(c);
                        em.persist(p);
                    } else {
                        atual.setNome(p.getNome());
                        atual.setDescricao(p.getDescricao());
                        atual.setPreco(p.getPreco());
                        atual.setQuantidadeEstoque(p.getQuantidadeEstoque());
                        atual.setMarca(m);
                        atual.setCategoria(c);
                        atual.setCaminhoImagem(p.getCaminhoImagem());
                    }
                    return null;
                });
    }

    public void excluirMarca(Long id) {
        repo.transacao(
                em -> {
                    Marca m = bloquear(em, Marca.class, id);
                    if (em.createQuery(
                                            "select count(p) from Produto p where p.marca.id=:id",
                                            Long.class)
                                    .setParameter("id", id)
                                    .getSingleResult()
                            > 0)
                        throw new RegraException(
                                "Marca vinculada a produtos. Desative-a ou altere os produtos"
                                    + " primeiro.");
                    em.remove(m);
                    return null;
                });
    }

    public void excluirCategoria(Long id) {
        repo.transacao(
                em -> {
                    em.createNativeQuery("select pg_advisory_xact_lock(20261006)")
                            .getSingleResult();
                    Categoria c = bloquear(em, Categoria.class, id);
                    if (em.createQuery(
                                            "select count(c) from Categoria c where"
                                                + " c.categoriaPai.id=:id",
                                            Long.class)
                                    .setParameter("id", id)
                                    .getSingleResult()
                            > 0)
                        throw new RegraException(
                                "A categoria possui subcategorias. Mova ou exclua as filhas"
                                    + " primeiro.");
                    if (em.createQuery(
                                            "select count(p) from Produto p where"
                                                + " p.categoria.id=:id",
                                            Long.class)
                                    .setParameter("id", id)
                                    .getSingleResult()
                            > 0) throw new RegraException("Categoria vinculada a produtos.");
                    em.remove(c);
                    return null;
                });
    }

    public String excluirProduto(Long id) {
        return repo.transacao(
                em -> {
                    Produto p = bloquear(em, Produto.class, id);
                    String caminho = p.getCaminhoImagem();
                    em.remove(p);
                    return caminho;
                });
    }
}
