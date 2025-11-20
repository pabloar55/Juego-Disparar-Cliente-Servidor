package Elementos;

import java.io.Serializable;
import javax.swing.JLabel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Diurno
 */
public class Player implements Serializable{
    private int x, y;
    private boolean estaDisparando = false; 

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean EstaDisparando() {
        return estaDisparando;
    }

    public void setEstaDisparando(boolean estaDisparando) {
        this.estaDisparando = estaDisparando;
    }
    
}
