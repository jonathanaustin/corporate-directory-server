package com.github.bordertech.corpdir.jpa.v1;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.AbstractJpaService;
import com.github.bordertech.corpdir.jpa.common.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.v1.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.v1.mapper.OrgUnitMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.PositionMapper;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Organization unit JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class OrgUnitServiceImpl extends AbstractJpaService implements OrgUnitService {

	private static final OrgUnitMapper ORGUNIT_MAPPER = new OrgUnitMapper();
	private static final PositionMapper POSITION_MAPPER = new PositionMapper();

	@Override
	public ServiceResponse<List<OrgUnit>> getOrgUnits(final String search, final Boolean topLevel) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<OrgUnitEntity> qry = cb.createQuery(OrgUnitEntity.class);

			// TODO Implement Search criteria and topOnly
			Root<OrgUnitEntity> from = qry.from(OrgUnitEntity.class);
			qry.select(from);

			List<OrgUnitEntity> rows = em.createQuery(qry).getResultList();
			List<OrgUnit> list = ORGUNIT_MAPPER.convertEntitiesToApis(em, rows);
			return new ServiceResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<OrgUnit> getOrgUnit(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<OrgUnit> createOrgUnit(final OrgUnit orgUnit) {
		EntityManager em = getEntityManager();
		try {
			MapperUtil.checkApiIDsForCreate(orgUnit);
			// Check business key does not exist
			OrgUnitEntity other = MapperUtil.getEntity(em, orgUnit.getBusinessKey(), OrgUnitEntity.class);
			if (other != null) {
				throw new ServiceException("An org unit already exists with business key [" + orgUnit.getBusinessKey() + "].");
			}
			OrgUnitEntity entity = ORGUNIT_MAPPER.convertApiToEntity(em, orgUnit);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<OrgUnit> updateOrgUnit(final String orgUnitKeyId, final OrgUnit orgUnit) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			MapperUtil.checkIdentifiersMatch(orgUnit, entity);
			ORGUNIT_MAPPER.copyApiToEntity(em, orgUnit, entity);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceBasicResponse deleteOrgUnit(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			em.remove(entity);
			em.getTransaction().commit();
			return new ServiceBasicResponse();
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<List<OrgUnit>> getSubOrgUnits(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			List<OrgUnit> list = ORGUNIT_MAPPER.convertEntitiesToApis(em, entity.getSubOrgUnits());
			return new ServiceResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<OrgUnit> addSubOrgUnit(final String orgUnitKeyId, final String subOrgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			// Get the sub org unit
			OrgUnitEntity subOrgUnit = getOrgUnitEntity(em, subOrgUnitKeyId);
			// Remove Sub Org Unit from its OLD parent (if it had one)
			OrgUnitEntity oldParent = subOrgUnit.getParentOrgUnit();
			if (oldParent != null) {
				oldParent.removeSubOrgUnit(subOrgUnit);
			}
			// Add to the new parent
			entity.addSubOrgUnit(subOrgUnit);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<OrgUnit> removeSubOrgUnit(final String orgUnitKeyId, final String subOrgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			// Get the sub org unit
			OrgUnitEntity subOrgUnit = getOrgUnitEntity(em, subOrgUnitKeyId);
			// Remove the sub org unit
			entity.removeSubOrgUnit(subOrgUnit);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<List<Position>> getPositions(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			List<Position> list = POSITION_MAPPER.convertEntitiesToApis(em, entity.getPositions());
			return new ServiceResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<OrgUnit> addPosition(final String orgUnitKeyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitKeyId);
			// Get the position
			PositionEntity position = getPositionEntity(em, positionKeyId);
			// Remove it from the old org unit (if had one)
			if (position.getBelongsToOrgUnit() != null) {
				position.getBelongsToOrgUnit().removePosition(position);
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
	public ServiceResponse<OrgUnit> removePosition(final String orgUnitKeyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitKeyId);
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
	public ServiceResponse<Position> getManagerPosition(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			Position data = POSITION_MAPPER.convertEntityToApi(em, entity.getManagerPosition());
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	/**
	 *
	 * @param em the entity manager
	 * @param entity the org unit entity
	 * @return the service response with org unit API object
	 */
	protected ServiceResponse<OrgUnit> buildResponse(final EntityManager em, final OrgUnitEntity entity) {
		OrgUnit data = ORGUNIT_MAPPER.convertEntityToApi(em, entity);
		return new ServiceResponse<>(data);
	}

	/**
	 * @param em the entity manager
	 * @param keyId the org unit key or API id
	 * @return the org unit entity
	 */
	protected OrgUnitEntity getOrgUnitEntity(final EntityManager em, final String keyId) {
		OrgUnitEntity entity = MapperUtil.getEntity(em, keyId, OrgUnitEntity.class);
		if (entity == null) {
			throw new NotFoundException("Org unit [" + keyId + "] not found.");
		}
		return entity;
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

}
