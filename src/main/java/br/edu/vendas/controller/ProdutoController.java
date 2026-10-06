package br.edu.vendas.controller;

import br.edu.vendas.model.*;
import br.edu.vendas.service.*;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.*;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class ProdutoController implements Serializable {
    @Inject CatalogoService service;
    @Inject ImagemService imagens;
    private Produto editando = new Produto();
    private List<Produto> lista;
    private byte[] pendente;
    private String nomePendente;

    public Produto getEditando() {
        return editando;
    }

    public String getNomePendente() {
        return nomePendente;
    }

    public List<Produto> getLista() {
        if (lista == null) atualizar();
        return lista;
    }

    public List<Marca> getMarcas() {
        return service.marcas().stream()
                .filter(
                        m ->
                                m.getAtivo()
                                        || (editando.getMarca() != null
                                                && m.getId().equals(editando.getMarca().getId())))
                .toList();
    }

    public List<Categoria> getCategorias() {
        return service.categorias();
    }

    public void atualizar() {
        try {
            lista = service.produtos();
        } catch (Exception e) {
            lista = new ArrayList<>();
            Mensagens.erro(e);
        }
    }

    public void novo() {
        editando = new Produto();
        pendente = null;
        nomePendente = null;
    }

    public void editar(Produto p) {
        try {
            editando =
                    service.produtos().stream()
                            .filter(x -> x.getId().equals(p.getId()))
                            .findFirst()
                            .orElseThrow(() -> new RegraException("Registro excluído."));
            pendente = null;
            nomePendente = null;
        } catch (Exception e) {
            Mensagens.erro(e);
        }
    }

    public void upload(FileUploadEvent evento) {
        try {
            UploadedFile f = evento.getFile();
            if (f.getSize() > ImagemService.MAX) throw new RegraException("Limite de 2 MB.");
            byte[] b;
            try (var in = f.getInputStream()) {
                b = in.readNBytes(ImagemService.MAX + 1);
            }
            ImagemService.validar(b, f.getFileName());
            pendente = b;
            nomePendente = f.getFileName();
            Mensagens.sucesso("Imagem selecionada. Clique em Salvar para gravá-la.");
        } catch (Exception e) {
            pendente = null;
            nomePendente = null;
            Mensagens.erro(e);
        }
    }

    public String getPreview() {
        return pendente == null
                ? null
                : "data:image/"
                        + (nomePendente.toLowerCase(Locale.ROOT).endsWith(".webp")
                                ? "webp"
                                : nomePendente.toLowerCase(Locale.ROOT).endsWith(".png")
                                        ? "png"
                                        : "jpeg")
                        + ";base64,"
                        + Base64.getEncoder().encodeToString(pendente);
    }

    public void salvar() {
        String antiga = editando.getCaminhoImagem(), nova = null;
        try {
            if (pendente != null) {
                nova = imagens.salvar(pendente, nomePendente);
                editando.setCaminhoImagem(nova);
            }
            service.salvar(editando);
            if (nova != null) imagens.apagar(antiga);
            novo();
            atualizar();
            Mensagens.sucesso("Produto salvo com sucesso.");
        } catch (Exception e) {
            if (nova != null) imagens.apagar(nova);
            editando.setCaminhoImagem(antiga);
            Mensagens.erro(e);
        }
    }

    public void excluir(Produto p) {
        try {
            String arquivo = service.excluirProduto(p.getId());
            imagens.apagar(arquivo);
            atualizar();
            Mensagens.sucesso("Produto excluído.");
        } catch (Exception e) {
            Mensagens.erro(e);
        }
    }
}
