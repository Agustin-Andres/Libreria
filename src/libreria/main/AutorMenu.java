package libreria.main;

import java.io.IOException;
import java.util.Arrays;
import libreria.entidades.Autor;
import static libreria.main.MainMenu.leer;
import static libreria.main.MainMenu.limpiarPantalla;
import static libreria.main.MainMenu.serv;

public class AutorMenu extends MainMenu {
    
    public static void menuLibreria() throws Exception {
        
        try {
            int opcion;
            boolean salida = true;
            
            limpiarPantalla();
            //menu 
            System.out.println((char) 27 + "\n\033[36m\t\t\t\tLibreria\n");
            
            do {
                
                System.out.println((char) 27 + "\n\033[36m••Que desea hacer?\n");
                System.out.println((char) 27 + "[36m1: \033[0mIngresar un nuevo Autor");
                System.out.println((char) 27 + "[36m2: \033[0mModificar un Autor existente");
                System.out.println((char) 27 + "[36m3: \033[0mVer los libros de un autor para disponibilidad y retiro");
                System.out.println((char) 27 + "[36m4: \033[0mVer todos los libros de un autor dispoible");
                System.out.println((char) 27 + "[36m5: \033[0mVer todos los autores con y sin disponibilidad");
                System.out.println((char) 27 + "[36m00: \033[0mVolver");
                System.out.print("» ");
                opcion = leer.nextInt();
                switch (opcion) {
                    case 00:
                        limpiarPantalla();
                        System.out.println("\t\tMuchas gracias! Volviendo al menu principal.");
                        salida = !salida;
                        break;
                    
                    case 1:
                        ingresarAutor();
                        break;
                    
                    case 2:
                        modificarAutorExistente();
                        break;
                    
                    case 3:
                        
                        break;
                    
                    case 4:
                        break;
                    
                    case 5:
                        break;
                    
                    default:
                        System.out.println("Ingreso una opcion equivocada.");
                        break;
                }
                
            } while (salida);
        } catch (IOException e) {
            System.out.println("Hubo un errror en el sistema :  " + e);
            System.out.println("Stacktrace" + Arrays.toString(e.getStackTrace()));
            
        }
    }

    //Crear autor 
    public static void ingresarAutor() {
        
        System.out.println("Ingrese el nombre del autor:\n»");
        String autor = leer.next().trim();
        servAutor.crear(autor, Boolean.TRUE);
        
    }
    
    private static void modificarAutorExistente() throws Exception {
        // muestra solamente el nombre e ID del autor
        servAutor.mostrarTodosLosAutores();
        
        System.out.print("Cual Autor deasea editar? Ingrese el ID por favor \n»");
        String id = leer.next().trim();
        // se fija si ingreso algun caracter que no corresponde
        if (id.matches("[a-zA-Z]+")) {
            System.out.println("Necesita ingresar un ID valido.");
            return;
        }
        Autor autor = servAutor.editarLibro(id);
        
        System.out.print("Nombre del autor: \n» ");
        String nombre = leer.next();
        
        System.out.print("Esta disponible? (S/n)\n» ");
        String alta = leer.next().toUpperCase();
        
        if (alta.contains("S")) {
            autor.setAlta(Boolean.TRUE);
        } else {
            autor.setAlta(Boolean.FALSE);
        }
        
        servAutor.guardarCambios(autor);
    }
    
}
