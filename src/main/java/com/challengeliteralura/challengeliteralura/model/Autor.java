package com.challengeliteralura.challengeliteralura.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
   @Table(name = "Autores") //  especificar el nombre de la tabla en la base de datos
    public class Autor {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_autor;
        private String nombre;
        private String fechaDeNacimiento;
        private String fechaDeFallecimiento;
        @OneToMany(mappedBy = "autor" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
       private Set<Libros> libros = new HashSet<>();;



        // Constructor sin argumentos (necesario para Hibernate)
        public Autor() {}


       public Autor(DatosAutor datosAutor) {
           this.nombre = datosAutor.nombre();
           this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
           this.fechaDeFallecimiento = datosAutor.fechaDeFallecimiento();
       }


       public Long getId_autor() { return id_autor; }

       public void setId_autor(Long id) {this.id_autor = id_autor;}

       public String getNombre() {return nombre;}

       public void setNombre(String nombre) {this.nombre = nombre;}

       public String getFechaDeNacimiento() {return fechaDeNacimiento;}

       public void setFechaDeNacimiento(String fechaDeNacimiento) {this.fechaDeNacimiento = fechaDeNacimiento;}

    public String getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public Set<Libros> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libros> libros) {
        this.libros = libros;
    }


    @Override
    public String toString() {
        return  "Nombre =" + nombre + '\''+
                "fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                "fechaDeFallecimiento='" + fechaDeFallecimiento + '\'';
    }

  }
