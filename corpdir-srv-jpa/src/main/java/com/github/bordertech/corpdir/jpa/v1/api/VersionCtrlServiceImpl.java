package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.jpa.common.svc.JpaService;
import com.github.bordertech.corpdir.jpa.entity.SystemCtrlEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import com.github.bordertech.corpdir.jpa.v1.mapper.VersionCtrlMapper;
import java.util.List;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Organization unit type JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class VersionCtrlServiceImpl extends JpaService<VersionCtrl, VersionCtrlEntity> implements VersionCtrlService {

	private static final Logger LOG = LoggerFactory.getLogger(VersionCtrlServiceImpl.class);

	private static final VersionCtrlMapper MAPPER = new VersionCtrlMapper();

	@Override
	public DataResponse<List<VersionCtrl>> retrieveVersions() {
		EntityManager em = getEntityManager();
		try {
			// Query for all rows
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<VersionCtrlEntity> qry = cb.createQuery(VersionCtrlEntity.class);
			Root<VersionCtrlEntity> root = qry.from(VersionCtrlEntity.class);
			qry.orderBy(cb.asc(root.get("id")));
			List<VersionCtrlEntity> rows = em.createQuery(qry).getResultList();
			// Convert to API
			List<VersionCtrl> list = MAPPER.convertEntitiesToApis(em, rows);
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Long> getCurrentVersion() {
		EntityManager em = getEntityManager();
		try {
			VersionCtrlEntity vers = getSystemCtrlEntity().getCurrentVersion();
			if (vers == null) {
				throw new IllegalStateException("No current version configured.");
			}
			return new DataResponse<>(vers.getId());
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Long> setCurrentVersion(final Long versionId) {
		if (versionId == null) {
			throw new IllegalArgumentException("A version id value must be provided.");
		}
		EntityManager em = getEntityManager();
		try {
			// Check version exsits
			VersionCtrlEntity vers = getEntityManager().find(VersionCtrlEntity.class, versionId);
			if (vers == null) {
				throw new IllegalArgumentException("Version id [" + versionId + "] does not exist.");
			}
			// Update the System Control Record
			em.getTransaction().begin();
			SystemCtrlEntity ctrl = getSystemCtrlEntity();
			ctrl.setCurrentVersion(vers);
			em.merge(ctrl);
			em.getTransaction().commit();
			LOG.info("Current version is now [" + versionId + "].");
			return new DataResponse<>(versionId);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Long> createVersion(final String description) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			VersionCtrlEntity vers = new VersionCtrlEntity(null);
			vers.setDescription(description);
			em.persist(vers);
			em.getTransaction().commit();
			LOG.info("Created version [" + vers.getId() + "].");
			return new DataResponse<>(vers.getId());
		} finally {
			em.close();
		}
	}

	@Override
	public BasicResponse deleteVersion(final Long versionId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			VersionCtrlEntity vers = getEntityManager().find(VersionCtrlEntity.class, versionId);
			if (vers == null) {
				throw new IllegalArgumentException("Version id [" + versionId + "] does not exist.");
			}
			VersionCtrlEntity current = getSystemCtrlEntity().getCurrentVersion();
			if (Objects.equals(current, vers)) {
				throw new IllegalArgumentException("Cannot delete the current version.");
			}
			em.remove(vers);
			em.getTransaction().commit();
			LOG.info("Deleted version [" + versionId + "].");
			return new BasicResponse();
		} finally {
			em.close();
		}
	}

	@Override
	public BasicResponse copyVersion(final Long fromId, final Long toId, final boolean copySystem, final boolean copyCustom) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	protected SystemCtrlEntity getSystemCtrlEntity() {
		SystemCtrlEntity ctrl = getEntityManager().find(SystemCtrlEntity.class, 1);
		if (ctrl == null) {
			ctrl = createSystemCtrlEntity();
		}
		return ctrl;
	}

	protected SystemCtrlEntity createSystemCtrlEntity() {
		LOG.info("Creating System Control Record and First Version.");
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		// Create a version
		VersionCtrlEntity vers = new VersionCtrlEntity(null);
		vers.setDescription("Initial");
		em.persist(vers);
		// Create Control with Default Version
		SystemCtrlEntity ctrl = new SystemCtrlEntity();
		ctrl.setCurrentVersion(vers);
		em.persist(ctrl);
		em.getTransaction().commit();
		return ctrl;
	}

}
