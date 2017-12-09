package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.SystemCtrlService;
import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.jpa.common.map.MapperApi;
import com.github.bordertech.corpdir.jpa.common.svc.JpaBasicIdService;
import com.github.bordertech.corpdir.jpa.entity.SystemCtrlEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import com.github.bordertech.corpdir.jpa.v1.mapper.SystemCtrlMapper;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * System Control JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class SystemCtrlServiceImpl extends JpaBasicIdService<SystemCtrl, SystemCtrlEntity> implements SystemCtrlService {

	private static final Logger LOG = LoggerFactory.getLogger(SystemCtrlServiceImpl.class);

	private static final SystemCtrlMapper MAPPER = new SystemCtrlMapper();

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

	@Override
	protected Class<SystemCtrlEntity> getEntityClass() {
		return SystemCtrlEntity.class;
	}

	@Override
	protected MapperApi<SystemCtrl, SystemCtrlEntity> getMapper() {
		return MAPPER;
	}

}
