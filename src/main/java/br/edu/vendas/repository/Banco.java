package br.edu.vendas.repository;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.util.*;
import java.util.function.Function;

@ApplicationScoped
public class Banco {
    private EntityManagerFactory factory;

    private synchronized EntityManagerFactory factory() {
        if (factory == null) {
            Map<String, Object> p = new HashMap<>();
            p.put(
                    "jakarta.persistence.jdbc.url",
                    env("DB_URL", "jdbc:postgresql://localhost:5432/vendas"));
            p.put("jakarta.persistence.jdbc.user", env("DB_USER", "vendas"));
            p.put("jakarta.persistence.jdbc.password", env("DB_PASSWORD", "vendas"));
            p.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
            p.put("hibernate.connection.pool_size", Integer.parseInt(env("DB_POOL_SIZE", "5")));
            factory = Persistence.createEntityManagerFactory("my_persistence_unit", p);
        }
        return factory;
    }

    private String env(String key, String fallback) {
        String v = System.getenv(key);
        return v == null || v.isBlank() ? fallback : v;
    }

    public <T> T executar(Function<EntityManager, T> acao) {
        EntityManager em = factory().createEntityManager();
        try {
            em.getTransaction().begin();
            T result = acao.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @PreDestroy
    public void fechar() {
        if (factory != null) factory.close();
    }
}
