package com.example.SimulacroParcial.controller;

import com.example.SimulacroParcial.models.CantidadPublicaciones;
import com.example.SimulacroParcial.models.Publicacion;
import com.example.SimulacroParcial.models.Usuario;
import com.example.SimulacroParcial.repository.CantPublicacionesRepository;
import com.example.SimulacroParcial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;

    @Autowired
    private CantPublicacionesRepository cpr;

    @PostMapping("")
    public void agregar(@RequestBody final @NotNull Usuario u, @RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            if (key.equals("user-agent")) {
                u.setBrowser(value);
            }
        });
        ur.save(u);
    }

    @PutMapping("/{id}/actualizar")
    public void actualizarUsuario(@PathVariable final @NotNull Integer id, @RequestBody final @NotNull Usuario u, @RequestHeader Map<String, String> headers) {
        Usuario us = ur.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Usuario " + id + " no encontrado."));
        us.setNombre(u.getNombre());
        us.setApellido(u.getApellido());
        headers.forEach((key, value) -> {
            if (key.equals("user-agent")) {
                us.setBrowser(value);
            }
        });
        ur.save(us);
    }

    @PostMapping("/{id}/borrar")
    public void borrarUsuario(@PathVariable final @NotNull Integer id) {
        Usuario u = ur.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Usuario " + id + " no encontrado."));
        ur.delete(u);
    }

    @GetMapping("")
    public List<Usuario> listarTodos() {
        return ur.findAll();
    }

    @GetMapping("/{id}")
    public Usuario listarUsuario(@PathVariable final @NotNull Integer id) {
        return ur.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Usuario " + id + " no encontrado."));
    }

    @PostMapping("/{id}/publicar")
    public void publicar(@PathVariable final @NotNull Integer id, @RequestBody @NotNull Publicacion p) {
        Usuario u = ur.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Usuario " + id + " no encontrado."));
        u.getPublicaciones().add(p);
        p.setDefaults();
        p.setUsuario(u);
        ur.save(u);
    }

    @GetMapping("/{id}/cantidadPublicaciones")
    public List<CantidadPublicaciones> contarPublicaciones() {
        return cpr.getCantidadPublicaciones();
    }
}