package libreria.servicio;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;
import libreria.persistencia.LibroDAO;

public class LibroServicio {

    private final LibroDAO dao;

    // constructor para tener acceso al DAO
    public LibroServicio() {
        dao = new LibroDAO();

    }

    //Creacion de un libro, con los atributos basicos, sin asignar un editorial o autor
    public Libro crear(Long isbn, String titulo, Integer anio, Integer ejemplares) throws SQLException {

        try {
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setIsbn(isbn);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(ejemplares);
            libro.setAlta(true);

            guardarCambios(libro);
            return libro;
        } catch (SQLException e) {

            System.out.println("Error al guardar! »»" + e);
            System.out.println("Stacktrace: " + Arrays.toString(e.getStackTrace()));

            return null;
        }

    }

    //guardar cambios a la base de datos 
    public void guardarCambios(Libro libro) throws SQLException {
        dao.guardar(libro);
    }

    //fuardar un libro editado 
    public void guardarCambioEditado(Libro libro) throws SQLException {
        dao.editar(libro);
    }

    //Validaciones 
    // esto guarda el titulo bonito
    public String validarTitulo(String titulo) throws Exception {
        try {

            titulo = titulo.toLowerCase();
            titulo = titulo.substring(0, 1).toUpperCase() + titulo.substring(1, titulo.length()).toLowerCase();
            return titulo;

        } catch (Exception e) {
            System.out.println("Error al cargar el titulo, por favor revise nuevamente el titulo del libro, error :" + e);
            return null;
        }

    }

    //Valida que el codigo de barras, el ISBN es unico.
    public Long validarIsbn(Long isbn) throws Exception {

        try {
            Libro libro = dao.buscarPorIsbn(isbn);

            if (libro == null) {
                System.out.println("El ISBN ya existe, por favor valide los datos nuevamente");
                return null;
            } else {
                return isbn;

            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error :" + e);
            System.out.println("Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    //Valida que los eejemplares prestados nos uperen los libros disponibles al publico
    public Libro validarEjemplaresPrestados(Libro libro, Integer ejemplares, Integer ejemplaresPrestados) {
        try {
            if (ejemplares < ejemplaresPrestados) {
                System.out.println("Los libros prestados no pueden exeder la cantidad de libros disponibles.");
                return libro;
            } else {
                libro.setEjemplaresPrestados(ejemplaresPrestados);
                libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
                return libro;

            }
        } catch (Exception e) {
            System.out.println("Hubo un error :" + e);
        }
        return null;

    }

    //se fija si un libro esta disponible
    public Libro disponibilidadDeUnLibro(String titulo) throws Exception {
        try {

            // busca los libros por titulos
            List<Libro> libros = dao.buscaPorTitulo(titulo);

            if (libros == null) {
                System.out.println("No encontramos el libro que busca");
            } else {
                System.out.println("Estos son los libros que tenemos disponible:");
                libros.forEach((_itemX) -> {
                    System.out.println(libros.toString());
                });
            }
            Libro libro = null;
            for (int i = 0; i < libros.size(); i++) {
                if (libros.get(i).getTitulo().equals(titulo)) {
                    libro = libros.get(i);
                    break;
                }
            }

            return libro;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException");
            return null;
        }
    }

    public void mostrarTodosLosTitulosEId() throws SQLException {
        List<Libro> libros = dao.retornarTodos();

        //______________________________________________________________________________________________________________-
        // Print the list objects in tabular format.
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%1s %40s", "ID", "Titulo");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");

        libros.stream().map((libro) -> {
            System.out.format("%1s %50s",
                    libro.getId(), libro.getTitulo());
            return libro;
        }).forEachOrdered((_item) -> {
            System.out.println();
        });
        System.out.println("-----------------------------------------------------------------------------");
    }

    //__________________________________________________________________________________________________________
    public Libro editarLibro(String id) throws Exception {
        Libro libro = dao.buscarPorId(id);
        System.out.println("El libro seleccionado es " + libro.getTitulo() + " y su ID es : " + libro.getId());
        return libro;
    }

    public void mostrarTodosLosLibrosDisponibles() throws SQLException {

        List<Libro> libros = dao.retornarTodos();

        for (Iterator<Libro> iterator = libros.iterator(); iterator.hasNext();) {
            Libro next = iterator.next();

            if (next.getAlta()) {

            } else {
                libros.remove(next);
            }

        }

        System.out.println("\t\t\t Todos los libros disponibles:\n");
        formatearTabla(libros);

    }

    public void mostrarTodosLosLibros() throws SQLException {

        List<Libro> libros = dao.retornarTodos();
        System.out.println("\t\t\tTodos los Libros en nuestra base: \n");
        formatearTabla(libros);

    }

    public void formatearTabla(List<Libro> libros) throws SQLException {
        // Print the list objects in tabular format.
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %40s %17s %19s %16s %15s", "ID", "Titulo", " Año", "Ejemplares", "Prestados", "Disponibles");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        libros.stream().map((libro) -> {
            System.out.format("%5s %40s %15s %15s %15s %17s",
                    libro.getId(), libro.getTitulo(), libro.getAnio(), libro.getEjemplares(), libro.getEjemplaresPrestados(), libro.getEjemplaresRestantes());
            return libro;
        }).forEachOrdered((_item) -> {
            System.out.println();
        });
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }

}
