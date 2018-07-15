package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();
	private Integer livroId;
	private Integer autorId;
	private List<Livro> livros;

	@Inject
	LivroDao livroDao;
	
	@Inject
	AutorDao autorDao;
	
	@Inject
	FacesContext context;
	
	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Autor> getAutores(){
		return this.autorDao.listaTodos();
	}
	
	public List<Livro> getLivros(){
		if (this.livros == null) {
			this.livros = this.livroDao.listaTodos();
		}
		return livros; 
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(autorId);
		this.livro.adicionaAutor(autor);
	}
	
	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
//			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			context.addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor"));;
		}

		if (this.livro.getId() == null) {
			this.livroDao.adiciona(this.livro);
			this.livros = this.livroDao.listaTodos();
		} else {
			this.livroDao.atualiza(this.livro);
		}
		
		this.livro = new Livro();
	}
	
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}
	
	@Transacional
	public void remover(Livro livro) {
		this.livroDao.remove(livro);
	}
	
	public void carregar(Livro livro) {
		this.livro = this.livroDao.buscaPorId(livro.getId());
	}
	
	public String formAutor() {
		return "autor?faces-redirect=true";
	}

	public void carregarLivroPeloId() {
		this.livro = this.livroDao.buscaPorId(livroId);
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException{
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}
	
}
