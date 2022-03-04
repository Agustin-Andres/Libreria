/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.main;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import libreria.entidades.Libro;
import static libreria.main.MainMenu.leer;
import static libreria.main.MainMenu.serv;

/**
 *
 * @author agustin
 */
public class LibreriaMenu extends MainMenu {

    public static void menuLibreria() {

        try {
            int opcion;
            boolean salida = true;

            limpiarPantalla();
            //menu 
            System.out.println((char) 27 + "\n\033[36m\t\t\t\tLibreria\n");

            do {

                System.out.println((char) 27 + "\n\033[36m••Que desea hacer?\n");
                System.out.println((char) 27 + "[36m1: \033[0mIngresar un nuevo libro");
                System.out.println((char) 27 + "[36m2: \033[0mModificar un libro existente");
                System.out.println((char) 27 + "[36m3: \033[0mVer un libro en espepcifico");
                System.out.println((char) 27 + "[36m4: \033[0mRetirar un libro");
                System.out.println((char) 27 + "[36m5: \033[0mVer todos los libros dispoibles");
                System.out.println((char) 27 + "[36m6: \033[0mVer todos los libros con y sin disponibilidad");
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
                        ingresarLibro();
                        break;

                    case 2:
                        modificarLibroExistente();
                        break;

                    case 3:
                        buscarLibroPorTiutlo();
                        break;
                    case 4:
                        retirarLibro();

                        break;

                    case 5:
                        serv.mostrarTodosLosLibrosDisponibles();
                        break;

                    case 6:
                        serv.mostrarTodosLosLibros();
                        break;

                    default:
                        System.out.println("Ingreso una opcion equivocada.");
                        break;
                }

            } while (salida);
        } catch (Exception e) {
            System.out.println("Hubo un errror en el sistema :  " + e);
            System.out.println("Stacktrace" + Arrays.toString(e.getStackTrace()));

        }
    }

    //metodos
    private static void ingresarLibro() throws SQLException {
        System.out.print("Titulo de Libro : \n» ");
        String titulo = leer.next();

        System.out.print("El codigo de barra del libro\n» ");
        Long isbn = leer.nextLong();

        System.out.print("Año del libro\n» ");
        Integer anio = leer.nextInt();

        System.out.print("Cuantos libros ingresa?\n» ");
        Integer ejemplares = leer.nextInt();

        //   LibroServicio serv = new LibroServicio();
        Libro libro = serv.crear(isbn, titulo, anio, ejemplares);

        System.out.println("El Libro \" " + libro.getTitulo() + " \" ha sido ingresado a nuestra base!");

    }

    private static void buscarLibroPorTiutlo() throws Exception {
        //Le pedimos al usuario el titulo para buscarlo en la base de datos 
        System.out.print("Ingrese el titulo del libro que busca\n» ");
        String titulo = leer.next();

        //busca todos los libros con el mismo titulo,
        //retorna la lista de todos los Libros con el mismo nombre
        Libro libro = serv.disponibilidadDeUnLibro(titulo);

        try {
            if (libro == null) {
                

            } else {
                //retiro de un libro
                System.out.print("Desea retirar uno?\n» ");
                String retiro = leer.next().toLowerCase();

                // Codigo para retirar uno, y cambiar los atributos del libro
                if (retiro.contains("s")) {
                    

                    // for each para evaluar cada ID de cada libro en la lista (porque todos tienen el mismo nombre) 
                    for (Libro libro1 : libro) {

                        // if statement para seleccionar el libro que quiere retirar el  usuario
                        if (libro1.getId() == null ? idLibroRetirar == null : libro1.getId().equals(idLibroRetirar)) {

                            //si no quedan mas libros disponibles le decimos al usuario y salimos del metodo
                            if (libro1.getEjemplaresRestantes() == 0) {
                                System.out.println("Lo lamentamos, no esta disponible el libro \"" + libro1.getTitulo() + "\", han sido retirados todos ");
                            }

                            //le decimos cual libro ha seleccionado
                            System.out.println("Ha elegido el libro \"" + libro1.getTitulo() + "\" ID: " + libro1.getId());
                            // por defecro, por si deasea ingresar u numero negativo, el retiro va a ser 1
                            int cantidadARetirar = 1;

                            // try and catch para ver cuantos libros quiere retirar 
                            try {

                                //cuantos a retirar
                                System.out.print("Cuantos desea retirar? puede retirar la candidad de : " + libro1.getEjemplaresRestantes() + "\n»");
                                cantidadARetirar = leer.nextInt();

                                // basoluto para que no ingrese negativos
                                cantidadARetirar = Math.abs(cantidadARetirar);

                                //SI ingresa un numero mayor larga la excepcion y le asigna 1
                                if (libro1.getEjemplaresRestantes() < cantidadARetirar) {
                                    cantidadARetirar = 1;
                                    throw new Exception();
                                }

                            } catch (Exception e) {
                                System.out.println("Lo lamentamos, no puede retirar esa cantidad de libros. Por defecto le asignamos 1");

                            }

                            //aca le asignamos los nuevbos atributos de los ejemplares prestados y restestantes
                            libro1.setEjemplaresPrestados(libro1.getEjemplaresPrestados() + cantidadARetirar);
                            libro1.setEjemplaresRestantes(libro1.getEjemplaresRestantes() - cantidadARetirar);

                            //si no hay libros restantes le cambiamos la disponibilidad
                            if (libro1.getEjemplaresRestantes() == 0) {
                                libro1.setAlta(Boolean.FALSE);
                            }

                            // guardamos los cambios del objeto
                            serv.guardarCambios(libro1);

                            // salimos del bucle y nuevamente al menu
                            break;
                        }
                    }

                }// fin for each

            }// fin if statement
        } catch (NullPointerException e) {
            System.out.println("Ingreso un ID invalido.");
        } catch (SQLException ex) {
            System.out.println("Error SQL : " + ex);

        } catch (Exception exception) {
            System.out.println("Error : " + exception);
            System.out.println("Stacktrace : " + Arrays.toString(exception.getStackTrace()));

        }

    }

    private static void modificarLibroExistente() throws Exception {
        // muestra solamente el titulo e ID del libro
        serv.mostrarTodosLosTitulosEId();

        System.out.print("Cual libro deasea editar? Ingrese el ID por favor \n»");
        String id = leer.next().trim();
        // se fija si ingreso algun caracter que no corresponde
        if (id.matches("[a-zA-Z]+")) {
            System.out.println("Necesita ingresar un ID valido.");
            return;
        }
        Libro libro = serv.editarLibro(id);

        System.out.print("Titulo de Libro : \n» ");
        String titulo = leer.next().trim();

        System.out.print("El codigo de barra del libro\n» ");
        Long isbn = leer.nextLong();

        System.out.print("Año del libro\n» ");
        Integer anio = leer.nextInt();

        System.out.print("Cuantos libros ingresa?\n» ");
        Integer ejemplares = leer.nextInt();

        System.out.print("Cuantos libros han sido prestados?\n» ");
        Integer prestados = leer.nextInt();

        if (ejemplares < prestados) {
            System.out.println("No puede haber prestado mas que lo ingresado, volviendo al menu principal");
            return;
        }

        libro.setTitulo(titulo);
        libro.setIsbn(isbn);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresRestantes(ejemplares - prestados);
        libro.setEjemplaresPrestados(prestados);

        serv.guardarCambioEditado(libro);

    }

    private static void retirarLibro() throws SQLException, Exception {

        serv.mostrarTodosLosLibrosDisponibles();
        System.out.println("Cual desea retirar? ingrese el ID del libro por favor:");
        String idLibroRetirar = leer.next();
        List<Libro> libro = serv.disponibilidadDeUnLibro(idLibroRetirar);

// for each para evaluar cada ID de cada libro en la lista (porque todos tienen el mismo nombre) 
        for (Libro libro1 : libro) {

            // if statement para seleccionar el libro que quiere retirar el  usuario
            if (libro1.getId() == null ? idLibroRetirar == null : libro1.getId().equals(idLibroRetirar)) {

                //si no quedan mas libros disponibles le decimos al usuario y salimos del metodo
                if (libro1.getEjemplaresRestantes() == 0) {
                    System.out.println("Lo lamentamos, no esta disponible el libro \"" + libro1.getTitulo() + "\", han sido retirados todos ");
                }

                //le decimos cual libro ha seleccionado
                System.out.println("Ha elegido el libro \"" + libro1.getTitulo() + "\" ID: " + libro1.getId());
                // por defecro, por si deasea ingresar u numero negativo, el retiro va a ser 1
                int cantidadARetirar = 1;

                // try and catch para ver cuantos libros quiere retirar 
                try {

                    //cuantos a retirar
                    System.out.print("Cuantos desea retirar? puede retirar la candidad de : " + libro1.getEjemplaresRestantes() + "\n»");
                    cantidadARetirar = leer.nextInt();

                    // basoluto para que no ingrese negativos
                    cantidadARetirar = Math.abs(cantidadARetirar);

                    //SI ingresa un numero mayor larga la excepcion y le asigna 1
                    if (libro1.getEjemplaresRestantes() < cantidadARetirar) {
                        cantidadARetirar = 1;
                        throw new Exception();
                    }

                } catch (Exception e) {
                    System.out.println("Lo lamentamos, no puede retirar esa cantidad de libros. Por defecto le asignamos 1");

                }

                //aca le asignamos los nuevbos atributos de los ejemplares prestados y restestantes
                libro1.setEjemplaresPrestados(libro1.getEjemplaresPrestados() + cantidadARetirar);
                libro1.setEjemplaresRestantes(libro1.getEjemplaresRestantes() - cantidadARetirar);

                //si no hay libros restantes le cambiamos la disponibilidad
                if (libro1.getEjemplaresRestantes() == 0) {
                    libro1.setAlta(Boolean.FALSE);
                }

                // guardamos los cambios del objeto
                serv.guardarCambios(libro1);

            }
        }
    }
}
