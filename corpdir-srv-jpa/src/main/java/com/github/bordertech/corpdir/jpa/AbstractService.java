package com.github.bordertech.corpdir.jpa;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Abstract class that provides the entity manager.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class AbstractService {

	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist-unit");

	/**
	 * @return the entity manager
	 */
	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
