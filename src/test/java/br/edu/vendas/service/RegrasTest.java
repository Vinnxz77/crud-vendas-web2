package br.edu.vendas.service;

import static org.junit.jupiter.api.Assertions.*;

import br.edu.vendas.model.Categoria;

import org.junit.jupiter.api.Test;

class RegrasTest {
    private Categoria c(long id, String nome, Categoria pai) {
        Categoria c = new Categoria();
        c.setId(id);
        c.setNome(nome);
        c.setCategoriaPai(pai);
        return c;
    }

    @Test
    void caminhoMultinivel() {
        Categoria raiz = c(1, "Perfumaria", null),
                filha = c(2, "Masculinos", raiz),
                neta = c(3, "Amadeirados", filha);
        assertEquals("Perfumaria > Masculinos > Amadeirados", neta.getCaminho());
        assertEquals(2, neta.getNivel());
    }

    @Test
    void impedePropriaCategoriaEDescendente() {
        Categoria a = c(1, "A", null), b = c(2, "B", a);
        assertThrows(RegraException.class, () -> Hierarquia.validarPai(1L, a));
        assertThrows(RegraException.class, () -> Hierarquia.validarPai(1L, b));
        assertDoesNotThrow(() -> Hierarquia.validarPai(2L, a));
    }

    @Test
    void detectaArvoreCorrompida() {
        Categoria a = c(1, "A", null), b = c(2, "B", a);
        a.setCategoriaPai(b);
        assertThrows(RegraException.class, () -> Hierarquia.validarPai(3L, a));
    }

    @Test
    void rejeitaImagemDisfarcada() {
        assertThrows(
                RegraException.class,
                () -> ImagemService.validar("arquivo falso".getBytes(), "foto.jpg"));
        assertThrows(
                RegraException.class,
                () ->
                        ImagemService.validar(
                                new byte[] {(byte) 255, (byte) 216, (byte) 255}, "foto.exe"));
    }

    @Test
    void aceitaAssinaturasERejeitaTamanho() {
        assertEquals(
                "png",
                ImagemService.validar(
                        new byte[] {(byte) 137, 80, 78, 71, 13, 10, 26, 10}, "foto.png"));
        assertThrows(
                RegraException.class,
                () -> ImagemService.validar(new byte[ImagemService.MAX + 1], "grande.png"));
        assertThrows(RegraException.class, () -> ImagemService.validar(new byte[0], "vazia.jpg"));
    }

    @Test
    void bloqueiaTravessiaDeDiretorio() {
        ImagemService s = new ImagemService();
        assertNull(s.localizar("../../arquivo.png"));
        assertNull(s.localizar("teste.html"));
    }
}
