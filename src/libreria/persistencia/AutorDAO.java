
package libreria.persistencia;

import java.sql.SQLException;
import java.util.List;
import libreria.entidades.Autor;


public class AutorDAO  extends DAO<Autor>{
    
    //guardar
    @Override
    public void guardar(Autor autor) throws SQLException {
        super.guardar(autor);
    }
    //eliminar
    @Override
    public void eliminar(Autor autor) throws SQLException{
    super.eliminar(autor);
    }
    //editar
    @Override
    public void editar(Autor autor) throws SQLException{
    super.editar(autor);
    }
    
      // retorna todos los libros de la base
    public List retornarTodos() throws SQLException {
        // conexion
        conectar();

        //busca todos los libros de la base con JPQL, de la clase padre DAO
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a")
                .getResultList();

        //retorna la lista de libros a Libro servicio
        return autores;
    }

     //buscar por id 
    public Autor buscarPorId(String id) throws Exception {
        conectar();

        //busca por el id, retorna el libro 
        Autor autor = em.find(Autor.class, id);

        desconectar();

        // retorna el libro
        return autor;
    }

    
}
