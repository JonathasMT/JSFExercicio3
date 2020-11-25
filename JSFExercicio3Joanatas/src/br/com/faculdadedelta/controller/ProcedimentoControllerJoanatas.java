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
	
	private static final String PAGINA_CADASTRO = "cadastroProcedimento.xhtml";
	private static final String PAGINA_LISTAR = "listarProcedimento.xhtml";
	
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
							exibirMensagem("Inclusão realizada com sucesso!");
							limparCampos();
						} else {
							dao.alterar(procedimento);
							exibirMensagem("Alteração realizada com sucesso!");
							limparCampos();
						}
					}else {
						exibirMensagem("A Data de Início deve ser menor que a Data de Fim.");
					}
				}else {
					exibirMensagem("A Data de Início deve ser menor que 01/01/2022");
				}
			}else {
				exibirMensagem("A Data de Início deve ser maior que a data de hoje.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			exibirMensagem("Erro ao realizar a operação. Tente novamente mais tarde. " + e.getMessage());
			e.printStackTrace();
		}
		return PAGINA_CADASTRO;
	}
	
	public String editar() {
		return PAGINA_CADASTRO;
	}
	
	public String excluir() {
		try {
			dao.excluir(procedimento);
			exibirMensagem("Exclusão realizada com sucesso!");
			limparCampos();
		} catch (ClassNotFoundException | SQLException e) {
			exibirMensagem("Erro ao realizar a operação. Tente novamente mais tarde. " + e.getMessage());
			e.printStackTrace();
		}
		return PAGINA_LISTAR;
	}
	
	public List<ProcedimentoJoanatas> getLista() {
		List<ProcedimentoJoanatas> listaRetorno = new ArrayList<ProcedimentoJoanatas>();
		try {
			listaRetorno = dao.listar();
		} catch (ClassNotFoundException | SQLException e) {
			exibirMensagem("Erro ao realizar a operação. Tente novamente mais tarde. " + e.getMessage());
			e.printStackTrace();
		}
		return listaRetorno;
	}
	
	public void exibirMensagem(String msg) {
		FacesMessage mensagem = new FacesMessage(msg);
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
	}
}
