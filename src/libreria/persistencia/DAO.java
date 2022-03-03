package libreria.persistencia;

import java.sql.SQLException;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO<T> {

    protected final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("JPAPO");
    protected EntityManager em = EMF.createEntityManager();
    
    
    //codigo para conectarse a la base de datos, incorporado una excepcion tipo SQL para que no pare el programa al correrlo
    protected void conectar() throws SQLException{
        try {
            if (!em.isOpen()) {
            em = EMF.createEntityManager();
        }
        } catch (Exception e) {
            
            System.out.println("Error al intentar la conexion a la base de datos! »»" + e);
            System.out.println("Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
    }
    
    //codigo para desconectarse a la base de datos, incorporado una excepcion tipo SQL para que no pare el programa al correrlo
    protected void desconectar() throws SQLException{
        try {
            if (em.isOpen()) {
            em.close();
        }
        } catch (Exception e) {
            
            System.out.println("Error al desconectar de la base de datos! »»" + e);
            System.out.println("Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
    }
    
    //codigo para guardar datos a la base de datos, incorporado una excepcion tipo SQL para que no pare el programa al correrlo
    protected void guardar(T objeto) throws SQLException {
        try {
            conectar();
            em.getTransaction().begin();
            em.persist(objeto);
            em.getTransaction().commit();
            desconectar();
        } catch (Exception e) {

            System.out.println("Error al guardar! »»" + e);
            System.out.println("Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
    }
    
    //codigo para guardar un objeto editado a la base de datos, incorporado una excepcion tipo SQL para que no pare el programa al correrlo
    protected void editar(T objeto) throws SQLException {
        try {
            conectar();
            em.getTransaction().begin();
            em.merge(objeto);
            em.getTransaction().commit();
            desconectar();
        } catch (Exception e) {

            System.out.println("Error al guardar el libro editado! »»" + e);
            System.out.println("Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    //codigo para eliminar un libro de la base de datos, incorporado una excepcion tipo SQL para que no pare el programa al correrlo
    protected void eliminar(T objeto) throws SQLException {

        try {
            conectar();
            em.getTransaction().begin();
            em.remove(objeto);
            em.getTransaction().commit();
            desconectar();

        } catch (Exception e) {

            System.out.println("Error al guardar! »»" + e);
            System.out.println("Stacktrace: " + Arrays.toString(e.getStackTrace()));
        }
    }

}
