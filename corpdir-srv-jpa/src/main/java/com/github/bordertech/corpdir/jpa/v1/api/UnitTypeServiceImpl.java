package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.jpa.common.AbstractJpaService;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.UnitTypeEntity;
import com.github.bordertech.corpdir.jpa.util.EmfUtil;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.mapper.OrgUnitMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.UnitTypeMapper;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Organization unit type JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class UnitTypeServiceImpl extends AbstractJpaService implements UnitTypeService {

	private static final OrgUnitMapper ORGUNIT_MAPPER = new OrgUnitMapper();
	private static final UnitTypeMapper UNITTYPE_MAPPER = new UnitTypeMapper();

	@Override
	public DataResponse<List<UnitType>> getUnitTypes(final String search) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<UnitTypeEntity> qry = cb.createQuery(UnitTypeEntity.class);

			Root<UnitTypeEntity> from = qry.from(UnitTypeEntity.class);
			qry.select(from);

			// Search
			if (search != null && !search.isEmpty()) {
				qry.where(EmfUtil.createSearchTextCriteria(cb, from, search));
			}

			// Order by
			qry.orderBy(EmfUtil.getDefaultOrderBy(cb, from));

			List<UnitTypeEntity> rows = em.createQuery(qry).getResultList();
			List<UnitType> list = UNITTYPE_MAPPER.convertEntitiesToApis(em, rows);
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<UnitType> getUnitType(final String typeKeyId) {
		EntityManager em = getEntityManager();
		try {
			UnitTypeEntity entity = getUnitTypeEntity(em, typeKeyId);
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<UnitType> createUnitType(final UnitType type) {
		EntityManager em = getEntityManager();
		try {
			MapperUtil.checkApiIDsForCreate(type);
			// Check business key does not exist
			UnitTypeEntity other = MapperUtil.getEntity(em, type.getBusinessKey(), UnitTypeEntity.class);
			if (other != null) {
				throw new ServiceException("A unit type already exists with business key [" + type.getBusinessKey() + "].");
			}
			UnitTypeEntity entity = UNITTYPE_MAPPER.convertApiToEntity(em, type);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<UnitType> updateUnitType(final String typeKeyId, final UnitType type) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			UnitTypeEntity entity = getUnitTypeEntity(em, typeKeyId);
			MapperUtil.checkIdentifiersMatch(type, entity);
			UNITTYPE_MAPPER.copyApiToEntity(em, type, entity);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public BasicResponse deleteUnitType(final String typeKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			UnitTypeEntity entity = getUnitTypeEntity(em, typeKeyId);
			em.remove(entity);
			em.getTransaction().commit();
			return new BasicResponse();
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<List<OrgUnit>> getOrgUnits(final String typeKeyId) {
		EntityManager em = getEntityManager();
		try {
			UnitTypeEntity type = getUnitTypeEntity(em, typeKeyId);
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<OrgUnitEntity> qry = cb.createQuery(OrgUnitEntity.class);
			Root<OrgUnitEntity> from = qry.from(OrgUnitEntity.class);
			qry.select(from);
			qry.where(cb.equal(from.get("type"), type));

			qry.orderBy(cb.asc(from.get("description")));

			List<OrgUnitEntity> rows = em.createQuery(qry).getResultList();
			List<OrgUnit> list = ORGUNIT_MAPPER.convertEntitiesToApis(em, rows);
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	/**
	 *
	 * @param em the entity manager
	 * @param entity the unit type entity
	 * @return the service response with unit type API object
	 */
	protected DataResponse<UnitType> buildResponse(final EntityManager em, final UnitTypeEntity entity) {
		UnitType data = UNITTYPE_MAPPER.convertEntityToApi(em, entity);
		return new DataResponse<>(data);
	}

	/**
	 * @param em the entity manager
	 * @param keyId the unit type key or API id
	 * @return the unit type entity
	 */
	protected UnitTypeEntity getUnitTypeEntity(final EntityManager em, final String keyId) {
		UnitTypeEntity entity = MapperUtil.getEntity(em, keyId, UnitTypeEntity.class);
		if (entity == null) {
			throw new NotFoundException("Unit Type [" + keyId + "] not found.");
		}
		return entity;
	}
}
