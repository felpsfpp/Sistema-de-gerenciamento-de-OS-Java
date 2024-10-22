package br.com.informatica.dal;

import java.sql.*;


public class Conexao {
    //conecta com o banco de dados
    public static Connection conectar() {
        java.sql.Connection conexao = null;
        
        //chama o driver (Deprecated)
        //String driver = "com.mysql.jdbc.Driver";
        
        String url="jdbc:mysql://localhost:3306/dbinformatica";
        String user = "root";
        String password = "1234";
        try {
            //Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
//para ver erro de conexao            
//System.out.println(e);
            return null;
        }
        }
    
}
