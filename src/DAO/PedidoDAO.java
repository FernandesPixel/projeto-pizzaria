/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Aluno
 */
public class PedidoDAO {

    private static Connection c;

    public PedidoDAO() {
        PedidoDAO.c = ConnectionFactory.getConnection();
    }

    public static void inserePedido(Pedido pizza) {

        String sql = "INSERT INTO pedido(sabor1, sabor2, sabor3, tamanho)"
                + "VALUES(?,?,?,?);";
        try {
            
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, pizza.getSabor1());
            stmt.setString(2, pizza.getSabor2());
            stmt.setString(3, pizza.getSabor3());
            stmt.setString(4, pizza.getTamanho());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*
    public static void atualizaPedido(Pedido pizza) {

        String sql = "UPDATE pedido SET pedido(sabor1, sabor2, sabor3, tamanho)"
                + "VALUES(?,?,?,?);";
        try {
            
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, pizza.getSabor1());
            stmt.setString(2, pizza.getSabor2());
            stmt.setString(3, pizza.getSabor3());
            stmt.setString(4, pizza.getTamanho());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/

    public void deletaPedido(Pedido pizza) {
        String sql = "DELETE FROM pedido WHERE pkcodpedido=?";
        try {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, pizza.getIdPedido());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ObservableList<Pedido>getPedido(){
        try{
            ObservableList<Pedido> pedido = FXCollections.observableArrayList();
            PreparedStatement stmt = this.c.prepareStatement("SELECT * FROM pedido");
           
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Pedido pizza = new Pedido();
                pizza.setSabor1(rs.getString("sabor1"));
                pizza.setSabor2(rs.getString("sabor2"));
                pizza.setSabor3(rs.getString("sabor3"));
                pizza.setTamanho(rs.getString("tamanho"));
                pizza.setIdPedido(rs.getInt("pkcodpedido"));
                pedido.add(pizza);

                }
                stmt.executeQuery();
                rs.close();
                return pedido;
            }catch(SQLException e){
                    throw new RuntimeException(e);
                    }
        }

    private static void fecharConexao() {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
