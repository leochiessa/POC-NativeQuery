package com.example.SimulacroParcial.controller;

import com.example.SimulacroParcial.models.Comentario;
import com.example.SimulacroParcial.repository.ComentarioRepository;
import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioRepository cr;

    @Value("${tiempo}")
    private Integer tiempo;

    @PostMapping("")
    public void agregar(@RequestBody final @NotNull Comentario c) {
        cr.save(c);
    }

    @PostMapping("/{id}/borrar")
    public void borrarComentario(@PathVariable final @NotNull Integer id) {
        Comentario c = cr.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Comentario " + id + " no encontrado"));
        cr.delete(c);
    }

    @Scheduled(cron = "${tiempo}")
    public void borrarComentariosViejos() {
        for (Comentario c : cr.findAll()) {
            if (LocalDate.now().compareTo(c.getFecha()) > tiempo) {
                cr.delete(c);
            }
        }
    }
}