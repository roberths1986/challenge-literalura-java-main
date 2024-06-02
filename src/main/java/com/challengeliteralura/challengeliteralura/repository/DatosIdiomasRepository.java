package com.challengeliteralura.challengeliteralura.repository;

import com.challengeliteralura.challengeliteralura.model.Idiomas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatosIdiomasRepository extends JpaRepository<Idiomas, Long> {
}
