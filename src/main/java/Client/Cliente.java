/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Elementos.Interfaz;
import Elementos.Player;
import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diurno
 */
public class Cliente {
    Player jugadorLocal;
    Interfaz i;
    public static void main(String[] args) {

    
        try {
            Cliente c = new Cliente();
            c.i = new Interfaz();
            c.i.setVisible(true);
            c.jugadorLocal= new Player( 30, 40);
            String Host = "10.2.8.16";
            int Puerto = 5000;//puerto remoto
            
            System.out.println("PROGRAMA CLIENTE INICIADO....");
            Socket cliente = new Socket(Host, Puerto);
            
            //Flujo de entrada para objetos
            ObjectInputStream perEnt = new ObjectInputStream(
                    cliente.getInputStream());
            //Se recibe un objeto
            Player jugadorRemoto = (Player) perEnt.readObject();//recibo objeto
            
            while(true){
            //FLUJO DE salida para objetos
            ObjectOutputStream perSal = new ObjectOutputStream(
                    cliente.getOutputStream());
            // Se envia el objeto
            perSal.writeObject(c.i.j1);
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
            public void haDisparado(){
            jugadorLocal.setEstaDisparando(true);
        }
        
           
        
}
