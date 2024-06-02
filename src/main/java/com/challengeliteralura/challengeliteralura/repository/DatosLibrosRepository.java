package com.challengeliteralura.challengeliteralura.repository;

import com.challengeliteralura.challengeliteralura.model.Libros;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DatosLibrosRepository extends JpaRepository<Libros, Long> {
    Optional<Libros> findByTituloContainsIgnoreCase(String titulo);

    @Query("SELECT l FROM Libros l ORDER BY l.id_libro DESC")
    Optional<Libros> findLastRegisteredBook();

    @Query("SELECT l FROM Libros l JOIN FETCH l.autor JOIN FETCH l.idiomas")
    List<Libros> findAllLibrosWithAutorAndIdiomas();

    @Query("SELECT l FROM Libros l JOIN FETCH l.autor JOIN FETCH l.idiomas i WHERE i.idioma = :idiomaUsuario")
    List<Libros> findAllLibrosWithAutorAndIdiomasByLanguage(String idiomaUsuario);

    @Query("SELECT l.numeroDeDescargas FROM Libros l")
    List<Double> findNumeroDeDescargas();

    @Query("SELECT l.titulo, a.nombre, l.numeroDeDescargas " +
            "FROM Libros l JOIN l.autor a " +
            "ORDER BY l.numeroDeDescargas DESC")
    List<Object[]> findTopLibrosConAutor(Pageable pageable);

    @Query("SELECT a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento, STRING_AGG(b.titulo, ', ') " +
            "FROM Autor a INNER JOIN a.libros b " +
            "WHERE UPPER(a.nombre) LIKE UPPER(CONCAT('%', :nombreAutor, '%')) " +
            "GROUP BY a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento")
    List<Object[]> buscarRegistrosPorAutor(@Param("nombreAutor") String nombreAutor);
}






