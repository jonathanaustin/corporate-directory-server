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

	private final OrgUnitMapper orgUnitMapper = new OrgUnitMapper();
	private final PositionMapper positionMapper = new PositionMapper();

	@Override
	public ServiceResponse<List<OrgUnit>> getOrgUnits(final String search, final Boolean topOnly) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<OrgUnitEntity> qry = cb.createQuery(OrgUnitEntity.class);

			// TODO Implement Search criteria and topOnly
			Root<OrgUnitEntity> from = qry.from(OrgUnitEntity.class);
			qry.select(from);

			List<OrgUnitEntity> rows = em.createQuery(qry).getResultList();
			List<OrgUnit> data = orgUnitMapper.convertEntitiesToApis(em, rows);
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<OrgUnit> getOrgUnit(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			OrgUnit data = orgUnitMapper.convertEntityToApi(em, entity);
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<List<OrgUnit>> getSubOrgUnits(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			List<OrgUnit> data = orgUnitMapper.convertEntitiesToApis(em, entity.getSubOrgUnits());
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<List<Position>> getAssignedPositions(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			List<Position> data = positionMapper.convertEntitiesToApis(em, entity.getPositions());
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<Position> getOrgUnitManager(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			Position data = positionMapper.convertEntityToApi(em, entity.getManagerPosition());
			return new ServiceResponse<>(data);
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
			OrgUnitEntity entity = orgUnitMapper.convertApiToEntity(em, orgUnit);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			OrgUnit data = orgUnitMapper.convertEntityToApi(em, entity);
			return new ServiceResponse<>(data);
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
			orgUnitMapper.copyApiToEntity(em, orgUnit, entity);
			em.merge(entity);
			em.getTransaction().commit();
			OrgUnit data = orgUnitMapper.convertEntityToApi(em, entity);
			return new ServiceResponse<>(data);
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
	public ServiceBasicResponse assignOrgUnitToOrgUnit(final String orgUnitKeyId, final String parentOrgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitKeyId);
			// Get the new parent entity
			OrgUnitEntity parent = getOrgUnitEntity(em, parentOrgUnitKeyId);
			// Remove Org Unit from its OLD parent (if it had one)
			OrgUnitEntity oldParent = orgUnit.getParentOrgUnit();
			if (oldParent != null) {
				oldParent.removeSubOrgUnit(orgUnit);
				em.merge(oldParent);
			}
			// Add to the new parent
			parent.addSubOrgUnit(orgUnit);
			em.merge(parent);
			em.getTransaction().commit();
			return new ServiceBasicResponse();
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceBasicResponse assignPosition(final String orgUnitKeyId, final String positionKeyId) {
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
			em.merge(orgUnit);
			em.getTransaction().commit();
			return new ServiceBasicResponse();
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceBasicResponse unassignPosition(final String orgUnitKeyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitKeyId);
			// Get the position
			PositionEntity position = getPositionEntity(em, positionKeyId);
			// Remove the position from the org unit
			orgUnit.removePosition(position);
			em.merge(orgUnit);
			em.getTransaction().commit();
			return new ServiceBasicResponse();
		} finally {
			em.close();
		}
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
