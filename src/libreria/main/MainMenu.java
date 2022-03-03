package libreria.main;

import java.io.IOException;
import java.util.Scanner;
import libreria.servicio.AutorServicio;

import libreria.servicio.LibroServicio;

public class MainMenu {

    public static final Scanner leer = new Scanner(System.in).useDelimiter("\n");
    public static final LibroServicio serv = new LibroServicio();
    public static final AutorServicio servAutor= new AutorServicio();

    public static void main(String[] args) throws Exception {
        
        limpiarPantalla();
        //menu 
        System.out.println("\n\t\t\t\tBienvenido a la Libreria!!");
        System.out.println("\t\tTiene muchas opciones tanto como administrador y cliente!\n");

        try {

            int opcion;
            boolean salida = true;

            do {
                
      
                
                
                limpiarPantalla();
                System.out.println((char)27 +"\n\033[33m••Que desea hacer?\n");
                System.out.println((char)27 +"[33m1- \033[0m Libreria Menu");
                System.out.println((char)27 +"[33m2 - \033[0m Autor Menu");
                System.out.println((char)27 +"[33m3 - \033[0m Editorial menu");
                System.out.print((char)27 +"[33m00 - \033[0m Salir\n» ");

                opcion = leer.nextInt();

                switch (opcion) {
                    case 00:
                        System.out.println("\t\tMuchas gracias! Hasta que vuelvas nuevamente!");
                        System.out.println("\n\t\t\t\tSaludos de la Libreria!!");
                        break;

                    case 1:
                        LibreriaMenu.menuLibreria();
                        break;

                }

            } while (salida);

        } catch (Exception e) {
        }

    }

    public static void limpiarPantalla() throws IOException {
        for (int i = 0; i < 200; i++) {
            System.out.print("*");
        }

      
    }

}
