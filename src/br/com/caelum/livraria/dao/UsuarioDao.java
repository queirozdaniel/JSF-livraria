package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	public boolean existe(Usuario usuario) {
		
		TypedQuery<Usuario> createQuery = em.createQuery("select u from Usuario u "
				+ "where u.email = :pEmail "
				+ "and u.senha = :pSenha",Usuario.class);

		createQuery.setParameter("pEmail", usuario.getEmail());
		createQuery.setParameter("pSenha", usuario.getSenha());
		try {
			Usuario resultado = createQuery.getSingleResult();
		}catch (NoResultException e) {
			return false;
		}

		return true;
	}

	
	
}
