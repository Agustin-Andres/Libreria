package libreria.servicio;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import libreria.entidades.Autor;
import libreria.entidades.Libro;
import libreria.persistencia.AutorDAO;

public class AutorServicio {

    private final AutorDAO dao;

    // constructor para tener acceso al DAO
    public AutorServicio() {
        dao = new AutorDAO();

    }

    public void crear(String nombre, Boolean alta) {
        Autor autor = new Autor();

        try {
            autor.setNombre(validarNombreAutor(nombre));
            autor.setAlta(alta);

            //guardamos los cambios 
            dao.guardar(autor);
        } catch (SQLException e) {

            System.out.println("Error al guardar! »»" + e);
            System.out.println("Stacktrace: " + Arrays.toString(e.getStackTrace()));

        }

    }

    //guardar cambios
    public void guardarCambios(Autor autor) throws SQLException {

        dao.guardar(autor);
    }

    //editar un libro existenta
    public Autor editarLibro(String id) throws Exception {
        Autor autor = dao.buscarPorId(id);
        System.out.println("El Autor seleccionado es " + autor.getId() + " y su ID es : " + autor.getNombre());
        return autor;
    }

    //validar nombre del autor para mayusculas
    public String validarNombreAutor(String titulo) {

        try {

            titulo = titulo.toLowerCase();
            titulo = titulo.substring(0, 1).toUpperCase() + titulo.substring(1, titulo.length()).toLowerCase();
            return titulo;

        } catch (Exception e) {
            System.out.println("Error al cargar el titulo, por favor revise nuevamente el titulo del libro, error :" + e);
            return null;
        }
    }

    //retornar todos los autores
    public void mostrarTodosLosAutores() throws SQLException {

        List<Autor> autores = dao.retornarTodos();
        System.out.println("\t\t\tTodos los Autores en nuestra base: \n");
        formatearTabla(autores);

    }

    //mostrar autores disponibles
    public void mostrarTodosLosAutoresDisponibles() throws SQLException {

        List<Autor> autor = dao.retornarTodos();

        autor.forEach((next) -> {
            if (next.getAlta()) {
            } else {
                autor.remove(next);
            }
        });

        System.out.println("\t\t\t Todos los libros disponibles:\n");
        formatearTabla(autor);

    }

    //formatear autores en una tabla
    private void formatearTabla(List<Autor> autores) throws SQLException {
        // Print the list objects in tabular format.
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%1s %40s %15s", "ID", "Autor", " Alta");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        autores.stream().map((autor) -> {
            System.out.format("%5s %40s %15s",
                    autor.getId(), autor.getNombre(), autor.getAlta());
            return autor;
        }).forEachOrdered((_item) -> {
            System.out.println();
        });
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }

}
