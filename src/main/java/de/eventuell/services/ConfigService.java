package de.eventuell.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class ConfigService {

	@Produces 
	@ApplicationScoped
	EntityManager createEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2");
		return emf.createEntityManager();
	}

	public void disposeEm (@Disposes EntityManager em) {
	  em.close();
	}


}
