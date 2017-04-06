package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.AbstractJpaService;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.util.CriteriaUtil;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.mapper.OrgUnitMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.PositionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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
	public DataResponse<List<OrgUnit>> getOrgUnits(final String search, final Boolean assigned) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<OrgUnitEntity> qry = cb.createQuery(OrgUnitEntity.class);

			Root<OrgUnitEntity> from = qry.from(OrgUnitEntity.class);
			qry.select(from);

			// Search
			List<Predicate> preds = new ArrayList<>();
			if (search != null && !search.isEmpty()) {
				preds.add(CriteriaUtil.createSearchTextCriteria(cb, from, search));
			}
			// Assigned
			if (assigned != null) {
				preds.add(CriteriaUtil.createAssignedCriteria(cb, from, assigned));
			}
			// AND
			if (!preds.isEmpty()) {
				qry.where(CriteriaUtil.createAndCriteria(cb, preds));
			}

			// Order by
			qry.orderBy(CriteriaUtil.getDefaultOrderBy(cb, from));

			List<OrgUnitEntity> rows = em.createQuery(qry).getResultList();
			List<OrgUnit> list = ORGUNIT_MAPPER.convertEntitiesToApis(em, rows);
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<OrgUnit> getOrgUnit(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<OrgUnit> createOrgUnit(final OrgUnit orgUnit) {
		EntityManager em = getEntityManager();
		try {
			MapperUtil.checkIdentifiersForCreate(em, orgUnit, OrgUnitEntity.class);
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
	public DataResponse<OrgUnit> updateOrgUnit(final String orgUnitKeyId, final OrgUnit orgUnit) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			MapperUtil.checkIdentifiersForUpdate(em, orgUnit, entity);
			ORGUNIT_MAPPER.copyApiToEntity(em, orgUnit, entity);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public BasicResponse deleteOrgUnit(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			em.remove(entity);
			em.getTransaction().commit();
			return new BasicResponse();
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<List<OrgUnit>> getSubOrgUnits(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			List<OrgUnit> list = ORGUNIT_MAPPER.convertEntitiesToApis(em, entity.getChildren());
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<OrgUnit> addSubOrgUnit(final String orgUnitKeyId, final String subOrgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			// Get the sub org unit
			OrgUnitEntity subOrgUnit = getOrgUnitEntity(em, subOrgUnitKeyId);
			// Cant add an OU to itself
			if (Objects.equals(entity, subOrgUnit)) {
				throw new IllegalArgumentException("Cannot add an OU to itself");
			}
			// Remove Sub Org Unit from its OLD parent (if it had one)
			OrgUnitEntity oldParent = subOrgUnit.getParent();
			if (oldParent != null) {
				oldParent.removeChild(subOrgUnit);
			}
			// Add to the new parent
			entity.addChild(subOrgUnit);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<OrgUnit> removeSubOrgUnit(final String orgUnitKeyId, final String subOrgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			if (Objects.equals(orgUnitKeyId, subOrgUnitKeyId)) {
				throw new IllegalArgumentException("Cannot remove an OU from itself");
			}
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			// Get the sub org unit
			OrgUnitEntity subOrgUnit = getOrgUnitEntity(em, subOrgUnitKeyId);
			// Cant add an OU to itself
			if (Objects.equals(entity, subOrgUnit)) {
				throw new IllegalArgumentException("Cannot add an OU to itself");
			}
			// Remove the sub org unit
			entity.removeChild(subOrgUnit);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<List<Position>> getPositions(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			List<Position> list = POSITION_MAPPER.convertEntitiesToApis(em, entity.getPositions());
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<OrgUnit> addPosition(final String orgUnitKeyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitKeyId);
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
	public DataResponse<OrgUnit> removePosition(final String orgUnitKeyId, final String positionKeyId) {
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
	public DataResponse<Position> getManagerPosition(final String orgUnitKeyId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitKeyId);
			Position data = POSITION_MAPPER.convertEntityToApi(em, entity.getManagerPosition());
			return new DataResponse<>(data);
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
	protected DataResponse<OrgUnit> buildResponse(final EntityManager em, final OrgUnitEntity entity) {
		OrgUnit data = ORGUNIT_MAPPER.convertEntityToApi(em, entity);
		return new DataResponse<>(data);
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
