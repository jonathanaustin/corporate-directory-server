package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.AbstractJpaTreeService;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.mapper.OrgUnitMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.PositionMapper;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import com.github.bordertech.corpdir.jpa.common.MapperApiEntity;

/**
 * Organization unit JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class OrgUnitServiceImpl extends AbstractJpaTreeService<OrgUnit, OrgUnitEntity> implements OrgUnitService {

	private static final OrgUnitMapper ORGUNIT_MAPPER = new OrgUnitMapper();
	private static final PositionMapper POSITION_MAPPER = new PositionMapper();

	@Override
	public DataResponse<List<Position>> getPositions(final String keyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getEntity(em, keyId);
			List<Position> list = POSITION_MAPPER.convertEntitiesToApis(em, entity.getPositions());
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<OrgUnit> addPosition(final String keyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getEntity(em, keyId);
			// Get the position
			PositionEntity position = getPositionEntity(em, positionKeyId);
			// Remove it from the old org unit (if had one)
			if (position.getOrgUnit() != null) {
				position.getOrgUnit().removePosition(position);
			}
			// Add the position to the org unit
			orgUnit.addPosition(position);
			em.getTransaction().commit();
			return buildResponse(em, orgUnit);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<OrgUnit> removePosition(final String keyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getEntity(em, keyId);
			// Get the position
			PositionEntity position = getPositionEntity(em, positionKeyId);
			// Remove the position from the org unit
			orgUnit.removePosition(position);
			em.getTransaction().commit();
			return buildResponse(em, orgUnit);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Position> getManagerPosition(final String keyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getEntity(em, keyId);
			Position data = POSITION_MAPPER.convertEntityToApi(em, entity.getManagerPosition());
			return new DataResponse<>(data);
		} finally {
			em.close();
		}
	}

	/**
	 * @param em the entity manager
	 * @param keyId the position key or API id
	 * @return the position entity
	 */
	protected PositionEntity getPositionEntity(final EntityManager em, final String keyId) {
		PositionEntity entity = MapperUtil.getEntity(em, keyId, PositionEntity.class);
		if (entity == null) {
			throw new NotFoundException("Position [" + keyId + "] not found.");
		}
		return entity;
	}

	@Override
	protected Class<OrgUnitEntity> getEntityClass() {
		return OrgUnitEntity.class;
	}

	@Override
	protected MapperApiEntity<OrgUnit, OrgUnitEntity> getMapper() {
		return ORGUNIT_MAPPER;
	}

}
