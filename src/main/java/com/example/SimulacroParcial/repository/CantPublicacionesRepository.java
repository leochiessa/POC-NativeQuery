package com.example.SimulacroParcial.repository;

import com.example.SimulacroParcial.models.CantidadPublicaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CantPublicacionesRepository extends JpaRepository<CantidadPublicaciones, String> {

    @Query(value = "select u.nombre as usuario, count(p.id) as cantidad from usuario u join publicacion p on p.id_usuario = u.id", nativeQuery = true)
    List<CantidadPublicaciones> getCantidadPublicaciones();
}