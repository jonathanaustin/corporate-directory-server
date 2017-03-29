package com.github.bordertech.corpdir.jpa.v1;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.jpa.common.AbstractJpaService;
import com.github.bordertech.corpdir.jpa.common.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.v1.entity.UnitTypeEntity;
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

	private final OrgUnitMapper orgUnitMapper = new OrgUnitMapper();
	private final UnitTypeMapper unitTypeMapper = new UnitTypeMapper();

	@Override
	public ServiceResponse<List<UnitType>> getUnitTypes(final String search) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<UnitTypeEntity> qry = cb.createQuery(UnitTypeEntity.class);

			// TODO Implement Search criteria
			Root<UnitTypeEntity> from = qry.from(UnitTypeEntity.class);
			qry.select(from);

			List<UnitTypeEntity> rows = em.createQuery(qry).getResultList();
			List<UnitType> data = unitTypeMapper.convertEntitiesToApis(em, rows);
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<List<OrgUnit>> getOrgUnits(final String typeKeyId) {
		EntityManager em = getEntityManager();
		try {
			UnitTypeEntity type = getUnitTypeEntity(em, typeKeyId);
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<OrgUnitEntity> qry = cb.createQuery(OrgUnitEntity.class);
			Root<OrgUnitEntity> from = qry.from(OrgUnitEntity.class);
			qry.select(from);
			qry.where(cb.equal(from.get("type"), type));

			List<OrgUnitEntity> rows = em.createQuery(qry).getResultList();
			List<OrgUnit> data = orgUnitMapper.convertEntitiesToApis(em, rows);
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<UnitType> getUnitType(final String typeKeyId) {
		EntityManager em = getEntityManager();
		try {
			UnitTypeEntity entity = getUnitTypeEntity(em, typeKeyId);
			UnitType data = unitTypeMapper.convertEntityToApi(em, entity);
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<UnitType> createUnitType(final UnitType type) {
		EntityManager em = getEntityManager();
		try {
			MapperUtil.checkApiIDsForCreate(type);
			// Check business key does not exist
			UnitTypeEntity other = MapperUtil.getEntity(em, type.getBusinessKey(), UnitTypeEntity.class);
			if (other != null) {
				throw new ServiceException("A unit type already exists with business key [" + type.getBusinessKey() + "].");
			}
			UnitTypeEntity entity = unitTypeMapper.convertApiToEntity(em, type);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			UnitType data = unitTypeMapper.convertEntityToApi(em, entity);
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceResponse<UnitType> updateUnitType(final String typeKeyId, final UnitType type) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			UnitTypeEntity entity = getUnitTypeEntity(em, typeKeyId);
			MapperUtil.checkIdentifiersMatch(type, entity);
			unitTypeMapper.copyApiToEntity(em, type, entity);
			em.merge(entity);
			em.getTransaction().commit();
			UnitType data = unitTypeMapper.convertEntityToApi(em, entity);
			return new ServiceResponse<>(data);
		} finally {
			em.close();
		}
	}

	@Override
	public ServiceBasicResponse deleteUnitType(final String typeKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			UnitTypeEntity entity = getUnitTypeEntity(em, typeKeyId);
			em.remove(entity);
			em.getTransaction().commit();
			return new ServiceBasicResponse();
		} finally {
			em.close();
		}
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
