package libreria.persistencia;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import libreria.entidades.Libro;

public class LibroDAO extends DAO<Libro> {

    //guardar un libro a la base
    @Override
    public void guardar(Libro libro) throws SQLException {
        super.guardar(libro);
    }

    //eliminar un libro e la base
    @Override
    public void eliminar(Libro libro) throws SQLException {
        super.eliminar(libro);
    }

    //guardar el libro editado
    @Override
    public void editar(Libro libro) throws SQLException {
        super.editar(libro);
    }

    // ----» Retorna lista u objetos para demostrar :
    // retorna todos los libros de la base
    public List retornarTodos() throws SQLException {
        // conexion
        conectar();

        //busca todos los libros de la base con JPQL, de la clase padre DAO
        List<Libro> libro = em.createQuery("SELECT l FROM Libro l")
                .getResultList();

        //retorna la lista de libros a Libro servicio
        return libro;
    }

    //retorna todos los codigos de barra de la base de datos
    public List<Libro> mostrarTodosIsbn() throws Exception {
        conectar();

        // usando JPQL para seleccionar todos los ISB de la lista.
        List libro = em.createQuery("SELECT l.isbn FROM Libro l").getResultList();

        desconectar();

        //retornamos la lista 
        return libro;
    }

    //---»busqueda especiifica 
    //buscar por id 
    public Libro buscarPorId(String id) throws Exception {
        conectar();

        //busca por el id, retorna el libro 
        Libro libro = em.find(Libro.class, id);

        desconectar();

        // retorna el libro
        return libro;
    }

    //retorna el objeto con el codigo de barras
    public Libro buscarPorIsbn(Long isbn) throws Exception {
        conectar();

        // busca el libro con el codigo de barra
        Libro libro = em.find(Libro.class, isbn);

        desconectar();
        //retornamos el libro
        return libro;
    }

    //buscar por titulo, recibe el titulo para buscar y retoran una lista de libros con el mismo titulo
    public List<Libro> buscaPorTitulo(String titulo) throws Exception {

        try {
            //asignamos los objetos tipo libro a la lista, los libros que tengan el mismo titulo
            List libros = em.createQuery("SELECT l "
                    + " FROM Libro l"
                    + " WHERE l.titulo LIKE CONCAT('%', :titulo, '%')", Libro.class)
                    .setParameter("titulo", titulo)
                    .getResultList();

            //retornamos la lista
            if (libros.isEmpty()) {
                return null;
            } else {
                return libros;
            }
            // en caso de que haya una excepcion retornamos null y aclaramos que hubo un error y cual es mas el stacktrace
        } catch (Exception e) {
            System.out.println("Error al buscar por titulo.");
            System.out.println("StrackTrace: " + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
