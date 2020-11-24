package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.modelo.ProcedimentoJoanatas;
import br.edu.faculdadedelta.util.Conexao;

public class ProcedimentoDaoJoanatas {

	public void incluir(ProcedimentoJoanatas procedimento) throws ClassNotFoundException, SQLException {
		Conexao conexao = new Conexao();
		Connection conn = conexao.conectarNoBanco();
		String sql = "INSERT INTO procedimentos(" + 
				"	paciente_desc, procedimento_desc, "
				+ "valor_procedimento, data_inicio_procedimento, "
				+ "data_fim_procedimento, quantidade_exame_procedimento)" + 
				"	VALUES (?, ?, ?, ?, ?, ?);";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, procedimento.getPaciente().trim());
		ps.setString(2, procedimento.getProcedimento().trim());
		ps.setDouble(3, procedimento.getValor());
	    ps.setDate(4, new java.sql.Date(procedimento.getDataInicio().getTime()));
	    ps.setDate(5, new java.sql.Date(procedimento.getDataFim().getTime()));
	    ps.setInt(6, procedimento.getQuantidade());
	    
	    ps.executeUpdate();
	    
	    ps.close();
	    conn.close();
	}
	
	public void alterar(ProcedimentoJoanatas procedimento) throws ClassNotFoundException, SQLException {
		Conexao conexao = new Conexao();
		Connection conn = conexao.conectarNoBanco();
		String sql = "UPDATE procedimentos" + 
				"	SET paciente_desc=?, "
				+ "procedimento_desc=?, valor_procedimento=?, "
				+ "data_inicio_procedimento=?, data_fim_procedimento=?, "
				+ "quantidade_exame_procedimento=?" + 
				"	WHERE id_procedimento=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, procedimento.getPaciente().trim());
		ps.setString(2, procedimento.getProcedimento().trim());
		ps.setDouble(3, procedimento.getValor());
	    ps.setDate(4, new java.sql.Date(procedimento.getDataInicio().getTime()));
	    ps.setDate(5, new java.sql.Date(procedimento.getDataFim().getTime()));
	    ps.setInt(6, procedimento.getQuantidade());
	    ps.setLong(7, procedimento.getId());
	    
	    ps.executeUpdate();
	    
	    ps.close();
	    conn.close();
	}
	
	public void excluir(ProcedimentoJoanatas procedimento) throws ClassNotFoundException, SQLException {
		Conexao conexao = new Conexao();
		Connection conn = conexao.conectarNoBanco();
		String sql = "DELETE FROM procedimentos WHERE id_procedimento=?";
		PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setLong(1, procedimento.getId());
	    
	    ps.executeUpdate();
	    
	    ps.close();
	    conn.close();
	}
	
	public List<ProcedimentoJoanatas> listar() throws ClassNotFoundException, SQLException {
		Conexao conexao = new Conexao();
		Connection conn = conexao.conectarNoBanco();
		
		String sql = "SELECT id_procedimento, paciente_desc, "
				+ "procedimento_desc, valor_procedimento, "
				+ "data_inicio_procedimento, data_fim_procedimento, "
				+ "quantidade_exame_procedimento" + 
				"	FROM procedimentos;";
		PreparedStatement ps = conn.prepareStatement(sql);
		List<ProcedimentoJoanatas> listaRetorno = new ArrayList<>();
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ProcedimentoJoanatas procedimento = new ProcedimentoJoanatas();
			procedimento.setId(rs.getLong("id_procedimento"));
			procedimento.setPaciente(rs.getString("paciente_desc").trim());
			procedimento.setProcedimento(rs.getString("procedimento_desc").trim());
			procedimento.setValor(rs.getDouble("valor_procedimento"));
			procedimento.setDataInicio(rs.getDate("data_inicio_procedimento"));
			procedimento.setDataFim(rs.getDate("data_fim_procedimento"));
			procedimento.setQuantidade(rs.getInt("quantidade_exame_procedimento"));
			listaRetorno.add(procedimento);
		}
		rs.close();
		ps.close();
		conn.close();
		
		return listaRetorno;
	}

}
