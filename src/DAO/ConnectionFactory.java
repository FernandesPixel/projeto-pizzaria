/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Aluno
 */
public class ConnectionFactory {
    public static Connection getConnection(){
        
        Connection c = null;
        try{
            c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/Pizzaria","postgres","juan");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return c;
    }
}
