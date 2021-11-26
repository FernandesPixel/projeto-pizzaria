/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author juann
 */
public class Pedido {
   private int idPedido; 
   private String sabor1;
   private String sabor2;
   private String sabor3;
   private String tamanho;

    public Pedido(String sabor1, String sabor2, String sabor3, String tamanho) {
        this.sabor1 = sabor1;
        this.sabor2 = sabor2;
        this.sabor3 = sabor3;
        this.tamanho = tamanho;
    }

    public Pedido() {
    }

    public String getSabor1() {
        return sabor1;
    }

    public void setSabor1(String sabor1) {
        this.sabor1 = sabor1;
    }

    public String getSabor2() {
        return sabor2;
    }

    public void setSabor2(String sabor2) {
        this.sabor2 = sabor2;
    }

    public String getSabor3() {
        return sabor3;
    }

    public void setSabor3(String sabor3) {
        this.sabor3 = sabor3;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
   
   
    
}
