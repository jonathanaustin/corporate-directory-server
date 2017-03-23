package com.github.bordertech.corpdir.jpa;

import com.github.bordertech.corpdir.api.OrgUnitService;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.Position;
import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.mapper.MapperUtil;
import com.github.bordertech.corpdir.jpa.mapper.OrgUnitMapper;
import com.github.bordertech.corpdir.jpa.mapper.PositionMapper;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * Organization unit JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class OrgUnitServiceImpl extends AbstractJpaService implements OrgUnitService {

	@Override
	public ServiceResponse<OrgUnit> getOrgUnit(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			OrgUnit data = OrgUnitMapper.convertEntityToApi(entity);
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
			List<OrgUnit> data = OrgUnitMapper.convertEntitiesToApis(entity.getSubOrgUnits());
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
			List<Position> data = PositionMapper.convertEntitiesToApis(entity.getPositions());
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
			Position data = PositionMapper.convertEntityToApi(entity.getManagerPosition());
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<String> createOrgUnit(final OrgUnit orgUnit) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = OrgUnitMapper.convertApiToEntity(orgUnit);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			String apiId = MapperUtil.convertEntityIdforApi(entity.getId());
			return new ServiceResponse<>(apiId);
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
			OrgUnitMapper.copyApiToEntity(orgUnit, entity);
			em.merge(entity);
			em.getTransaction().commit();
			OrgUnit data = OrgUnitMapper.convertEntityToApi(entity);
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
	private OrgUnitEntity getOrgUnitEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, OrgUnitEntity.class);
	}

	/**
	 * @param em the entity manager
	 * @param keyId the position key or API id
	 * @return the position entity
	 */
	private PositionEntity getPositionEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, PositionEntity.class);
	}

}
