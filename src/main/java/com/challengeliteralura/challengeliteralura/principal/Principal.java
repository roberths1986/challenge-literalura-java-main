package com.challengeliteralura.challengeliteralura.principal;

import com.challengeliteralura.challengeliteralura.model.*;
import com.challengeliteralura.challengeliteralura.repository.DatosAutorRepository;
import com.challengeliteralura.challengeliteralura.repository.DatosIdiomasRepository;
import com.challengeliteralura.challengeliteralura.repository.DatosLibrosRepository;
import com.challengeliteralura.challengeliteralura.service.ConsumoAPI;
import com.challengeliteralura.challengeliteralura.service.ConvierteDatos;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private final DatosLibrosRepository librosRepository;
    private final DatosAutorRepository autorRepository;
    private final DatosIdiomasRepository idiomasRepository;

    public Principal(DatosLibrosRepository librosRepository,
                     DatosAutorRepository autorRepository,
                     DatosIdiomasRepository idiomasRepository) {
        this.librosRepository = librosRepository;
        this.autorRepository = autorRepository;
        this.idiomasRepository = idiomasRepository;
    }

    private Scanner teclado = new Scanner(System.in);

    public void muestraElMenu() {

        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        int opcion = -1;
        while (opcion != 0) {
            System.out.println();
            System.out.println("POR FAVOR INGRESE UNA OPCIÓN VÁLIDA");
            var menu = """
                    1 - Búsqueda de libro por título    
                    2 - Lista de todos los libros registrados
                    3 - Lista de autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Exhibir cantidad de libros en un determinado idioma  
                    6 - Ver estadísticas de descargas 
                    7 - Ver Top 10 de libros más descargados
                    8 - Buscar datos de un autor por nombre   
                    9 - Buscar autores nacidos a partir del año indicado                       
                    0 - Salir
                    """;

            System.out.println(menu);
            while (!teclado.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                teclado.next(); // descartar la entrada inválida
            }
            opcion = teclado.nextInt();
            teclado.nextLine(); // consumir el salto de línea


            System.out.println("Opción seleccionada: " + opcion);
            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    verEstadisticasDeDescargas();
                    break;
                case 7:
                    topp10LibrosMasDescargados();
                    break;
                case 8:
                    buscarAutorPorNombre();
                    break;
                case 9:
                    listarAutoresNacidosApartirDeUnAnio();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

        // Cerrar el Scanner
        teclado.close();
        System.out.println("Muchas gracias por usar nuestra aplicación.");
    }


    private DatosLibros obtenerDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro Encontrado ");
            System.out.println(libroBuscado.get());
            return libroBuscado.get();
        } else {
            System.out.println("Libro no encontrado");
            return null;
        }
    }


    @Transactional
    private void buscarLibroWeb() {
        DatosLibros datos = obtenerDatosLibro();
        if (datos != null) {

            // Verificar si el libro ya existe en la base de datos
            if (libroExiste(datos.titulo())) {
                System.out.println();
                System.out.println("El libro ya existe en la base de datos. No se puede guardar.");
                return; // No se guarda el libro si ya existe
            }
            // Crear el autor a partir de los datos obtenidos
            DatosAutor datosAutor = datos.autor().get(0); // Tomar el primer autor de la lista
            Autor autor = new Autor(datosAutor);

            // Crear la lista de idiomas a partir de los datos obtenidos
                  List<Idiomas> idiomas = datos.idiomas().stream()
                    .map(idioma -> new Idiomas(idioma))
                    .collect(Collectors.toList());

            // Crear el libro con el autor y los idiomas
            Libros libros = new Libros(datos, autor, idiomas);

            // Relacionar los idiomas con el libro
            idiomas.forEach(idioma -> idioma.setLibro(libros));

            // Guardar el libro (esto también guardará el autor y los idiomas debido a CascadeType.ALL)
            librosRepository.save(libros);
            //           System.out.println(datos);
            System.out.println();
            System.out.println("****** LIBRO GUARDADO EN LA BASE DE DATOS ******");
            System.out.println();
            System.out.println("Titulo: " + libros.getTitulo());
            System.out.println("Autor: " + libros.getAutor().getNombre());
            System.out.println("Fecha de Nacimiento: " + libros.getAutor().getFechaDeNacimiento());
            System.out.println("Fecha de Fallecimiento: " + libros.getAutor().getFechaDeFallecimiento());
            String idiomas1 = libros.getIdiomas().stream()
                    .map(Idiomas::getCodigo)
                    .collect(Collectors.joining(", "));
            System.out.println("Idioma: " + idiomas1);
            System.out.println("Número de Descargas : " + libros.getNumeroDeDescargas());
        } else {
            System.out.println("No se pudo guardar el libro porque no se encontró.");
        }
    }


    private boolean libroExiste(String titulo) {
        Optional<Libros> libroExistente = librosRepository.findByTituloContainsIgnoreCase(titulo);
        return libroExistente.isPresent();
    }

    private void listarLibrosRegistrados() {
        List<Libros> librosRegistrados = librosRepository.findAllLibrosWithAutorAndIdiomas();
        librosRegistrados.forEach(l -> {
            System.out.println();
            System.out.println("****** LIBRO ******");
            System.out.println();
            System.out.println("Titulo: " + l.getTitulo());
            System.out.println("Autor: " + l.getAutor().getNombre());
            System.out.println("Fecha de Nacimiento: " + l.getAutor().getFechaDeNacimiento());
            System.out.println("Fecha de Fallecimiento: " + l.getAutor().getFechaDeFallecimiento());

            String idiomas = l.getIdiomas().stream()
                    .map(Idiomas::getCodigo)
                    .collect(Collectors.joining(", "));
            System.out.println("Idioma: " + idiomas);
            System.out.println("Número de Descargas: " + l.getNumeroDeDescargas());
            System.out.println();

        });
    }

    private void listarAutoresRegistrados() {
        List<Object[]> resultados = autorRepository.findAllAutoresWithLibros();
        System.out.println();
        System.out.println("****** AUTORES ******");
        System.out.println();
        // Iterar sobre los resultados utilizando forEach
        resultados.forEach(resultado -> {
            String nombreAutor = (String) resultado[0];
            String fechaNacimiento = (String) resultado[1];
            String fechaFallecimiento = (String) resultado[2];
            String titulosLibros = (String) resultado[3];

            System.out.println("Nombre del Autor: " + nombreAutor);
            System.out.println("Fecha de Nacimiento: " + fechaNacimiento);
            System.out.println("Fecha de Fallecimiento: " + fechaFallecimiento);
            System.out.println("Títulos de los Libros: " + titulosLibros);
            System.out.println();
        });
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año en el cual desea buscar los autores vivos");
        var anioUsuario = teclado.nextLine();
        List<Object[]> resultados = autorRepository.findAllAutoresWithLibrosAlive(anioUsuario);
        if (resultados.isEmpty()) {
            System.out.println();
            System.out.println("No hay información registrada para ese año");
        } else {
            System.out.println();
            System.out.println("****** AUTORES VIVOS EN EL AÑO INDICADO ******");
            System.out.println();
            // Iterar sobre los resultados utilizando forEach
            resultados.forEach(resultado -> {
                String nombreAutor = (String) resultado[0];
                String fechaNacimiento = (String) resultado[1];
                String fechaFallecimiento = (String) resultado[2];
                String titulosLibros = (String) resultado[3];

                System.out.println("Nombre del Autor: " + nombreAutor);
                System.out.println("Fecha de Nacimiento: " + fechaNacimiento);
                System.out.println("Fecha de Fallecimiento: " + fechaFallecimiento);
                System.out.println("Títulos de los Libros: " + titulosLibros);
                System.out.println();
            });

        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma por el que desea buscar los libros");
        var idiomaUsuario = teclado.nextLine();
        String codigoIdioma = convertirIdiomaACodigo(idiomaUsuario);

        System.out.println("Idioma ingresado: " + idiomaUsuario);
        System.out.println("Código de idioma convertido: " + codigoIdioma);


        if (codigoIdioma != null) {
            List<Libros> librosRegistrados = librosRepository.findAllLibrosWithAutorAndIdiomasByLanguage(codigoIdioma);
            System.out.println("Cantidad de libros encontrados: " + librosRegistrados.size());

            librosRegistrados.forEach(l -> {
                System.out.println();
                System.out.println("****** LIBRO ******");
                System.out.println();
                System.out.println("Titulo: " + l.getTitulo());
                System.out.println("Autor: " + l.getAutor().getNombre());
                System.out.println("Fecha de Nacimiento: " + l.getAutor().getFechaDeNacimiento());
                System.out.println("Fecha de Fallecimiento: " + l.getAutor().getFechaDeFallecimiento());

                String idiomas = l.getIdiomas().stream()
                        .map(Idiomas::getCodigo)
                        .collect(Collectors.joining(", "));
                System.out.println("Número de Descargas: " + l.getNumeroDeDescargas());
                System.out.println();
            });
        } else {
            System.out.println("Idioma no soportado.");
        }
    }

    private String convertirIdiomaACodigo(String idioma) {
        switch (idioma.toLowerCase()) {
            case "español":
                return "es";
            case "inglés":
                return "en";
            case "francés":
                return "fr";
            case "portugués":
                return "pt";
            case "italiano":
                return "it";
            default:
                return null;
        }
    }

    private void verEstadisticasDeDescargas() {
        List<Double> descargas = librosRepository.findNumeroDeDescargas();
        if (!descargas.isEmpty()) {
            DoubleSummaryStatistics est = descargas.stream()
                    .mapToDouble(Double::doubleValue) // Convertir Double a double
                    .summaryStatistics(); // Obtener estadísticas
            System.out.println();
            System.out.println("****** ESTADÍSTICAS ******");
            System.out.println();
            System.out.println("Cantidad media de descargas: " + est.getAverage());
            System.out.println("Cantidad máxima de descargas: " + est.getMax());
            System.out.println("Cantidad mínima de descargas: " + est.getMin());
            System.out.println("Cantidad de registros evaluados para calcular las estadísticas: " + est.getCount());
        } else {
            System.out.println("No hay datos de descargas disponibles para calcular estadísticas.");
        }
    }

    private void topp10LibrosMasDescargados() {
        // Crear objeto Pageable para indicar que solo se necesitan los primeros 10 resultados
        Pageable pageable = PageRequest.of(0, 10);

        // Llamar al método del repositorio para obtener los 10 libros más descargados con su autor
        List<Object[]> top10LibrosConAutor = librosRepository.findTopLibrosConAutor(pageable);

        System.out.println();
        System.out.println("****** TOP 10 LIBROS DESCARGADOS ******");
        System.out.println();
        // Iterar sobre los resultados utilizando forEach
        for (Object[] libroConAutor : top10LibrosConAutor) {
            String tituloLibro = (String) libroConAutor[0];
            String nombreAutor = (String) libroConAutor[1];
            Double numeroDescargas = (double) libroConAutor[2];

            System.out.println("Título del libro: " + tituloLibro);
            System.out.println("Autor: " + nombreAutor);
            System.out.println("Número de descargas: " + numeroDescargas);
            System.out.println();
        }
    }

    private void buscarAutorPorNombre() {
        System.out.println("Ingrese el nombre del autor que desea buscar");
        var autorBuscado = teclado.nextLine().toUpperCase();

        List<Object[]> autores = librosRepository.buscarRegistrosPorAutor(autorBuscado);

        if (autores.isEmpty()) {
            System.out.println("Autor no encontrado");
        } else {
            System.out.println();
            System.out.println("****** REGISTROS DEL AUTOR INDICADO ******");
            System.out.println();
            autores.forEach(resultado -> {
                String nombreAutor = (String) resultado[0];
                String fechaNacimiento = (String) resultado[1];
                String fechaFallecimiento = (String) resultado[2];
                String titulosLibros = (String) resultado[3];

                System.out.println("Nombre del Autor: " + nombreAutor);
                System.out.println("Fecha de Nacimiento: " + fechaNacimiento);
                System.out.println("Fecha de Fallecimiento: " + fechaFallecimiento);
                System.out.println("Títulos de los Libros: " + titulosLibros);
                System.out.println();
            });
        }
    }
    private void listarAutoresNacidosApartirDeUnAnio() {
        System.out.println("Ingrese el año a partir del cual desea consultar los registros de libros");
        var anioInicial = teclado.nextLine();
            List<Object[]> resultadosanioinicio = autorRepository.findAllAutoresWithLibrosStartingAnioNacimiento(anioInicial);
            if (resultadosanioinicio.isEmpty()) {
                System.out.println();
                System.out.println("No hay información registrada a partir de ese año");
            } else {
            System.out.println();
            System.out.println("****** AUTORES NACIDOS A PARTIR DEL AÑO INDICADO ******");
            System.out.println();
            // Iterar sobre los resultados utilizando forEach
            resultadosanioinicio.forEach(resultado -> {
                String nombreAutor = (String) resultado[0];
                String fechaNacimiento = (String) resultado[1];
                String fechaFallecimiento = (String) resultado[2];
                String titulosLibros = (String) resultado[3];

                System.out.println("Nombre del Autor: " + nombreAutor);
                System.out.println("Fecha de Nacimiento: " + fechaNacimiento);
                System.out.println("Fecha de Fallecimiento: " + fechaFallecimiento);
                System.out.println("Títulos de los Libros: " + titulosLibros);
                System.out.println();
            });

        }
    }


}











