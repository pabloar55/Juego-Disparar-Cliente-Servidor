package Client;

import Elementos.Interfaz;
import Elementos.Player;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            Interfaz i = new Interfaz();
            i.setVisible(true);

            String host = "localhost";
            int puerto = 5000;

            System.out.println("Conectando al servidor...");
            Socket socket = new Socket(host, puerto);
            System.out.println("¡Conectado!");

            // ¡IMPORTANTE! Crear ObjectOutputStream PRIMERO
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            salida.flush();  // Necesario para evitar deadlock

            // Luego el ObjectInputStream
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

            // Hilo receptor: actualiza al enemigo
            new Thread(() -> {
                try {
                    while (true) {
                        Player enemigo = (Player) entrada.readObject();
                        i.setLocationJ2(enemigo.getX(), enemigo.getY());
                    }
                } catch (Exception e) {
                    System.out.println("Servidor cerrado o enemigo desconectado");
                }
            }).start();

            // Bucle principal: enviar nuestra posición
            while (true) {
                // Sincronizamos el Player con la posición REAL del JLabel (la que movemos con teclas)
                i.j1.setX(i.getXj1());
                i.j1.setY(i.getYj1());

                salida.writeObject(i.j1);
                salida.flush();

                Thread.sleep(16); // ~60 FPS
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}