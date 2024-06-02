package com.challengeliteralura.challengeliteralura.repository;

import com.challengeliteralura.challengeliteralura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatosAutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento, STRING_AGG(b.titulo, ', ') " +
            "FROM Autor a " +
            "JOIN Libros b ON a.id_autor = b.autor.id_autor " +
            "GROUP BY a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento")
    List<Object[]> findAllAutoresWithLibros();

    @Query("SELECT a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento, STRING_AGG(b.titulo, ', ') " +
            "FROM Autor a " +
            "JOIN Libros b ON a.id_autor = b.autor.id_autor " +
            "WHERE a.fechaDeNacimiento <= :anioUsuario " +  // Verifica que el autor haya nacido en o antes del año dado
            "AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento > :anioUsuario) " +  // Verifica que el autor no haya fallecido antes del año dado
            "GROUP BY a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento")
    List<Object[]> findAllAutoresWithLibrosAlive(String anioUsuario);

    @Query("SELECT a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento, STRING_AGG(b.titulo, ', ') " +
            "FROM Autor a " +
            "JOIN Libros b ON a.id_autor = b.autor.id_autor " +
            "WHERE a.fechaDeNacimiento >= :anioInicial " +  // Comparación directa del año de nacimiento
            "GROUP BY a.nombre, a.fechaDeNacimiento, a.fechaDeFallecimiento")
        List<Object[]> findAllAutoresWithLibrosStartingAnioNacimiento(String anioInicial);  // Parámetro para el año de nacimiento
}
