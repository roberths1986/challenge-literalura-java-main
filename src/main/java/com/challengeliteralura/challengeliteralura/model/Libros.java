package com.challengeliteralura.challengeliteralura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Libros {
    @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_libro;
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Autor autor;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Idiomas> idiomas;
    private Double numeroDeDescargas;

    // Constructor sin argumentos (necesario para Hibernate)
    public Libros() {}

    public Libros (DatosLibros datosLibros, Autor autor, List<Idiomas> idiomas) {
        this.titulo = datosLibros.titulo();
        this.autor = autor;
        this.idiomas= idiomas;
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

        public Long getId_libro() {
        return id_libro;
    }

    public void setId_libro(Long id) {
        this.id_libro = id_libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public List<Idiomas> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idiomas> idiomas) {
        this.idiomas = idiomas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return
                "titulo=" + titulo + '\''+
              ", Autor=" + autor + '\''+
                        ", Idiomas=" + idiomas + '\''+
                        ",numeroDeDescargas=" +numeroDeDescargas + '\'';
    }

}

