package Server;

import Elementos.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    private static final int PUERTO = 5000;
    private static final int MAX_JUGADORES = 2;

    // Almacenamos los dos jugadores y sus streams
    private static final Player[] jugadores = new Player[MAX_JUGADORES];
    private static final ObjectOutputStream[] salidas = new ObjectOutputStream[MAX_JUGADORES];
    private static final ObjectInputStream[] entradas = new ObjectInputStream[MAX_JUGADORES];

    public static void main(String[] args) {
        System.out.println("Servidor iniciado en puerto " + PUERTO + "...");

        ExecutorService pool = Executors.newFixedThreadPool(MAX_JUGADORES);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                // Esperamos a que se conecten los 2 jugadores
                for (int i = 0; i < MAX_JUGADORES; i++) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Jugador " + (i + 1) + " conectado desde " + socket.getInetAddress());

                    // Creamos los streams
                    ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                    salida.flush();
                    ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

                    salidas[i] = salida;
                    entradas[i] = entrada;

                    // Creamos un jugador "vacío" hasta que recibamos datos reales
                    jugadores[i] = new Player(100, 300); // posición inicial arbitraria
                }

                System.out.println("¡Ambos jugadores conectados! Comienza el juego.");

                // Lanzamos un hilo para cada cliente que reciba continuamente sus datos
                for (int i = 0; i < MAX_JUGADORES; i++) {
                    final int indice = i;
                    pool.execute(() -> manejarCliente(indice));
                }

                // No aceptamos más conexiones una vez que tenemos los 2 jugadores
                // (si quieres reiniciar partida tendrías que cerrar y volver a lanzar el servidor)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void manejarCliente(int miIndice) {
        try {
            while (true) {
                // 1. Recibimos el Player del cliente
                Player jugadorActualizado = (Player) entradas[miIndice].readObject();
                jugadores[miIndice] = jugadorActualizado;

                // 2. Enviamos al cliente la información del oponente
                int indiceOponente = (miIndice + 1) % 2;
                salidas[miIndice].writeObject(jugadores[indiceOponente]);
                salidas[miIndice].flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Jugador " + (miIndice + 1) + " desconectado.");
            // Opcional: cerrar todo y terminar partida
        }
    }
}