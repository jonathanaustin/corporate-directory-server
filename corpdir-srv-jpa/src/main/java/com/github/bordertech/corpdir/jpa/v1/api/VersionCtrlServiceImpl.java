package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.jpa.common.map.MapperApi;
import com.github.bordertech.corpdir.jpa.common.svc.JpaBasicIdService;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import com.github.bordertech.corpdir.jpa.v1.mapper.VersionCtrlMapper;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Version Control JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class VersionCtrlServiceImpl extends JpaBasicIdService<VersionCtrl, VersionCtrlEntity> implements VersionCtrlService {

	private static final Logger LOG = LoggerFactory.getLogger(VersionCtrlServiceImpl.class);

	private static final VersionCtrlMapper MAPPER = new VersionCtrlMapper();

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
	public BasicResponse copyVersion(final Long fromId, final Long toId, final boolean copySystem, final boolean copyCustom) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected MapperApi<VersionCtrl, VersionCtrlEntity> getMapper() {
		return MAPPER;
	}

	@Override
	protected Class<VersionCtrlEntity> getEntityClass() {
		return VersionCtrlEntity.class;
	}

}
