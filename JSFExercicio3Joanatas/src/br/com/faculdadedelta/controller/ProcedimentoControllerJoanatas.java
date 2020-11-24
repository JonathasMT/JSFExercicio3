package br.com.faculdadedelta.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.faculdadedelta.dao.ProcedimentoDaoJoanatas;
import br.edu.faculdadedelta.modelo.ProcedimentoJoanatas;

@ManagedBean
@SessionScoped
public class ProcedimentoControllerJoanatas {

	private ProcedimentoJoanatas procedimento = new ProcedimentoJoanatas();
	private ProcedimentoDaoJoanatas dao = new ProcedimentoDaoJoanatas();
	
	public ProcedimentoJoanatas getProcedimentoJoanatas() {
		return procedimento;
	}
	public void setProcedimentoJoanatas(ProcedimentoJoanatas procedimento) {
		this.procedimento = procedimento;
	}
	
	public void limparCampos() {
		procedimento = new ProcedimentoJoanatas();
	}
	
	public String salvar() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date limite = sdf.parse("01/01/2022");
		try {
			if(procedimento.getDataInicio().after(new Date())) {
				if(procedimento.getDataInicio().before(limite)) {
					if(procedimento.getDataInicio().before(procedimento.getDataFim())) {
						if(procedimento.getId() == null) {
							dao.incluir(procedimento);
							FacesMessage mensagem = new FacesMessage("Inclusão realizada com sucesso!");
							FacesContext.getCurrentInstance().addMessage(null, mensagem);
							limparCampos();
						} else {
							dao.alterar(procedimento);
							FacesMessage mensagem = new FacesMessage("Alteração realizada com sucesso!");
							FacesContext.getCurrentInstance().addMessage(null, mensagem);
							limparCampos();
						}
					}else {
						FacesMessage mensagem = new FacesMessage("A Data de Início deve ser menor que a Data de Fim.");
						FacesContext.getCurrentInstance().addMessage(null, mensagem);
					}
				}else {
					FacesMessage mensagem = new FacesMessage("A Data de Início deve ser menor que 01/01/2022");
					FacesContext.getCurrentInstance().addMessage(null, mensagem);
				}
			}else {
				FacesMessage mensagem = new FacesMessage("A Data de Início deve ser maior que a data de hoje.");
				FacesContext.getCurrentInstance().addMessage(null, mensagem);
			}
		} catch (ClassNotFoundException | SQLException e) {
			FacesMessage mensagem = new FacesMessage("Erro ao realizar a operação. Tente novamente mais tarde. " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			e.printStackTrace();
		}
		return "cadastroProcedimento.xhtml";
	}
	
	public String editar() {
		return "cadastroProcedimento.xhtml";
	}
	
	public String excluir() {
		try {
			dao.excluir(procedimento);
			FacesMessage mensagem = new FacesMessage("Exclusão realizada com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			limparCampos();
		} catch (ClassNotFoundException | SQLException e) {
			FacesMessage mensagem = new FacesMessage("Erro ao realizar a operação. Tente novamente mais tarde. " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			e.printStackTrace();
		}
		return "listarProcedimento.xhtml";
	}
	
	public List<ProcedimentoJoanatas> getLista() {
		List<ProcedimentoJoanatas> listaRetorno = new ArrayList<ProcedimentoJoanatas>();
		try {
			listaRetorno = dao.listar();
		} catch (ClassNotFoundException | SQLException e) {
			FacesMessage mensagem = new FacesMessage("Erro ao realizar a operação. Tente novamente mais tarde. " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			e.printStackTrace();
		}
		return listaRetorno;
	}
}
