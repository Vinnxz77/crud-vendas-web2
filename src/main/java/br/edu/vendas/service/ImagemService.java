package br.edu.vendas.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@ApplicationScoped
public class ImagemService {
    public static final int MAX = 2 * 1024 * 1024;

    public Path diretorio() {
        String v = System.getenv("UPLOAD_DIR");
        return Path.of(v == null ? System.getProperty("user.home") + "/vendas-uploads" : v)
                .toAbsolutePath()
                .normalize();
    }

    public static String validar(byte[] b, String nome) {
        if (b == null || b.length == 0 || b.length > MAX)
            throw new RegraException("A imagem deve ter entre 1 byte e 2 MB.");
        String n = nome == null ? "" : nome.toLowerCase(Locale.ROOT);
        String tipo;
        if (b.length >= 3 && (b[0] & 255) == 255 && (b[1] & 255) == 216 && (b[2] & 255) == 255)
            tipo = "jpg";
        else if (b.length >= 8
                && Arrays.equals(
                        Arrays.copyOf(b, 8), new byte[] {(byte) 137, 80, 78, 71, 13, 10, 26, 10}))
            tipo = "png";
        else if (b.length >= 12
                && new String(b, 0, 4, java.nio.charset.StandardCharsets.US_ASCII).equals("RIFF")
                && new String(b, 8, 4, java.nio.charset.StandardCharsets.US_ASCII).equals("WEBP"))
            tipo = "webp";
        else throw new RegraException("Conteúdo inválido. Envie JPG, PNG ou WEBP.");
        if (!(n.endsWith("." + tipo) || (tipo.equals("jpg") && n.endsWith(".jpeg"))))
            throw new RegraException("A extensão não corresponde ao conteúdo da imagem.");
        return tipo;
    }

    public String salvar(byte[] dados, String nome) {
        String tipo = validar(dados, nome);
        String seguro = UUID.randomUUID() + "." + tipo;
        try {
            Files.createDirectories(diretorio());
            Files.write(diretorio().resolve(seguro), dados, StandardOpenOption.CREATE_NEW);
            return seguro;
        } catch (IOException e) {
            throw new RegraException("Não foi possível gravar a imagem.");
        }
    }

    public Path localizar(String nome) {
        if (nome == null || !nome.matches("[a-f0-9-]{36}[.](jpg|png|webp)")) return null;
        Path p = diretorio().resolve(nome).normalize();
        return p.startsWith(diretorio()) ? p : null;
    }

    public void apagar(String nome) {
        Path p = localizar(nome);
        if (p != null)
            try {
                Files.deleteIfExists(p);
            } catch (IOException e) {
                java.util.logging.Logger.getLogger(getClass().getName())
                        .warning("Falha ao remover arquivo de imagem sem vínculo.");
            }
    }
}
