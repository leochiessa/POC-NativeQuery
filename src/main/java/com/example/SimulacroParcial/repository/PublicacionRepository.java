package com.example.SimulacroParcial.repository;

import com.example.SimulacroParcial.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {

    @Query(value = "select u.nombre as usuario, p.titulo as publicacion, count(c.id) as cantCom from usuario u join publicacion p on p.id_usuario = u.id join comentario c on c.id_publicacion = p.id", nativeQuery = true)
    List<CantComentarios> getCantComentarios();
}