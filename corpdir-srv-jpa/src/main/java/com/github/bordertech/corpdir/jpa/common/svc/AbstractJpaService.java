package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.jpa.util.EmfUtil;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * Abstract class that provides the entity manager.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class AbstractJpaService {

	/**
	 * @return the entity manager
	 */
	protected EntityManager getEntityManager() {
		return EmfUtil.getEMF().createEntityManager();
	}

	protected Integer getCurrentVersionId() {
		return 1;
	}

}
