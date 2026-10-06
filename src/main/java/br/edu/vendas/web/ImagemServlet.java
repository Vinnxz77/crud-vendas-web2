package br.edu.vendas.web;

import br.edu.vendas.service.ImagemService;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;

@WebServlet("/imagens/*")
public class ImagemServlet extends HttpServlet {
    @Inject ImagemService imagens;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String info = req.getPathInfo();
        Path p = imagens.localizar(info == null ? null : info.substring(1));
        if (p == null || !Files.isRegularFile(p)) {
            res.sendError(404);
            return;
        }
        String nome = p.getFileName().toString();
        res.setContentType(
                nome.endsWith(".jpg")
                        ? "image/jpeg"
                        : nome.endsWith(".png") ? "image/png" : "image/webp");
        res.setHeader("X-Content-Type-Options", "nosniff");
        res.setHeader("Cache-Control", "public, max-age=86400");
        res.setContentLengthLong(Files.size(p));
        Files.copy(p, res.getOutputStream());
    }
}
