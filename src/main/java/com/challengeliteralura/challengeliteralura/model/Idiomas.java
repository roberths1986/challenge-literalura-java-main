    package com.challengeliteralura.challengeliteralura.model;

    import jakarta.persistence.*;



    @Entity
    @Table(name = "Idiomas") //  especificar el nombre de la tabla en la base de datos
    public class Idiomas {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id_idioma  ;
            private String idioma;

        @ManyToOne
        @JoinColumn(name = "id_libro")
        private Libros libro;

              // Constructor sin argumentos (necesario para Hibernate)
            public Idiomas() {}


        public Idiomas(String idioma) {
            this.idioma = idioma;
        }

        public Long getId_idioma() {
            return id_idioma;
        }

        public void setId_idioma(Long id_idioma) {
            this.id_idioma = id_idioma;
        }

        public String getIdioma() {
            return idioma;
        }

        public void setIdioma(String idioma) {
            this.idioma = idioma;
        }

        public Libros getLibro() {
            return libro;
        }

        public void setLibro(Libros libro) {
            this.libro = libro;
        }
        @Override
        public String toString() {
            return
                    "idioma=" + idioma +'\'';
        }

        public String getCodigo() {
            return idioma.substring(0, 2).toLowerCase();
            }
    }
