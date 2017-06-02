package br.com.cadastropessoa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.cadastropessoa.model.Pessoa;

@Repository
public class PessoaRepository {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager manager;
	
	
	@Transactional
	public boolean registroDuplicado(String nome){

		Pessoa pessoa = manager.find(Pessoa.class, nome);
		 
		 return pessoa != null;
	}
	
	
	@Transactional
	public void incluir(Pessoa pessoa){
 
		manager.persist(pessoa);	
	}
 
	@Transactional
	public void alterar(Pessoa pessoa){
 
		manager.merge(pessoa);	

	}
 
	@Transactional
	public void excluir(String nome){
 
	    Pessoa pessoa =	manager.find(Pessoa.class, nome);
		manager.remove(pessoa);
	}
	
	public List<Pessoa> todosUsuarios(){
 
		return manager.createQuery("SELECT p FROM Pessoa p ORDER BY p.nome ", Pessoa.class).getResultList();	

	}
	
	
}
