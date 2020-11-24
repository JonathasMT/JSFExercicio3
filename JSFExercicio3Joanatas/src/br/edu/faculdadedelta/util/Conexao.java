package br.edu.faculdadedelta.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	public Connection conectarNoBanco() throws ClassNotFoundException, SQLException {
		
		Class.forName("org.postgresql.Driver");
		
		Connection conn = null;
		
		String url = "jdbc:postgresql://localhost/Exercicio3";
		String usuario = "postgres";
		String senha = "1602";
		
		conn = DriverManager.getConnection(url, usuario, senha);
		
		
		return conn;
	}
	
	public static void main(String[] args) {
		Conexao conexao = new Conexao();
		try {
			conexao.conectarNoBanco();
			System.out.println("Conectou no banco de dados!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
