/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.animais.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsavel por criar uma conexão com o banco de dados
 * @author User
 */
public class ConnectionFactory {

    public static Connection getConnection() {
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager
            .getConnection("jdbc:mysql://us-cdbr-east-03.cleardb.com:3306/heroku_046f70cb3d931a9?reconnect=true&useTimezone=true&serverTimezone=UTC","xxxxxx","xxxxxx");
            
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Erro ao criar conexão");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver não localizado");
        }
        
        return con;
        
    }
}
