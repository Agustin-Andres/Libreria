
package libreria.persistencia;

import java.sql.SQLException;
import libreria.entidades.Editorial;

public class EditorialDAO extends DAO<Editorial>{
    
    //guardar
    @Override
    public void guardar(Editorial editorial) throws SQLException {
        super.guardar(editorial);
    }
    //eliminar
    @Override
    public void eliminar(Editorial editorial) throws SQLException{
    super.eliminar(editorial);
    }
    //editar
    @Override
    public void editar(Editorial editorial) throws SQLException{
    super.editar(editorial);
    }
    
}
