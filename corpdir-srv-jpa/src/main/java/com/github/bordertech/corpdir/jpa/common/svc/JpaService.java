package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.jpa.common.feature.PersistIdObject;
import com.github.bordertech.corpdir.jpa.entity.SystemCtrlEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import com.github.bordertech.corpdir.jpa.util.EmfUtil;
import java.io.Serializable;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class that provides the entity manager.
 *
 * @param <A> the API object type
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class JpaService<A extends ApiIdObject, P extends PersistIdObject> implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(JpaService.class);

	/**
	 * @return the entity manager
	 */
	protected EntityManager getEntityManager() {
		return EmfUtil.getEMF().createEntityManager();
	}

	protected Long getCurrentVersionId() {
		SystemCtrlEntity ctrl = getEntityManager().find(SystemCtrlEntity.class, 1, LockModeType.NONE);
		if (ctrl == null) {
			throw new IllegalStateException("No System Control Record Available.");
		}
		VersionCtrlEntity version = ctrl.getCurrentVersion();
		if (version == null) {
			throw new IllegalStateException("No Default Version Conifgured.");
		}
		return version.getId();
	}

}
