package com.github.bordertech.corpdir.jpa;

import com.github.bordertech.corpdir.api.UnitTypeService;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.UnitType;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity_;
import com.github.bordertech.corpdir.jpa.entity.UnitTypeEntity;
import com.github.bordertech.corpdir.jpa.entity.UnitTypeEntity_;
import java.util.List;
import java.util.Objects;
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
public class UnitTypeServiceImpl extends AbstractService implements UnitTypeService {

	@Override
	public List<UnitType> getUnitTypes() {

		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<UnitTypeEntity> qry = cb.createQuery(UnitTypeEntity.class);

			Root<UnitTypeEntity> from = qry.from(UnitTypeEntity.class);
			qry.select(from);

			List<UnitTypeEntity> rows = em.createQuery(qry).getResultList();

			return MapperUtil.convertListOrgUnitTypeEntityToApi(rows);
		} finally {
			em.close();
		}

	}

	@Override
	public List<OrgUnit> getOrgUnits(final Long typeId) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<OrgUnitEntity> qry = cb.createQuery(OrgUnitEntity.class);
			Root<OrgUnitEntity> from = qry.from(OrgUnitEntity.class);
			qry.select(from);
			qry.where(cb.equal(from.get(OrgUnitEntity_.id), typeId));

			List<OrgUnitEntity> rows = em.createQuery(qry).getResultList();

			return MapperUtil.convertListOrgUnitEntityToApi(rows);
		} finally {
			em.close();
		}
	}

	@Override
	public List<OrgUnit> getOrgUnits(final String typeAltKey) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<OrgUnitEntity> qry = cb.createQuery(OrgUnitEntity.class);
			Root<OrgUnitEntity> from = qry.from(OrgUnitEntity.class);
			qry.select(from);
			qry.where(cb.equal(from.get(OrgUnitEntity_.alternateKey), typeAltKey));

			List<OrgUnitEntity> rows = em.createQuery(qry).getResultList();

			return MapperUtil.convertListOrgUnitEntityToApi(rows);
		} finally {
			em.close();
		}
	}

	@Override
	public UnitType getUnitType(final Long typeId) {
		EntityManager em = getEntityManager();
		try {
			UnitTypeEntity entity = getOrgUnitTypeEntity(em, typeId);
			return MapperUtil.convertOrgUnitTypeEntityToApi(entity);
		} finally {
			em.close();
		}
	}

	@Override
	public UnitType getUnitType(final String typeAltKey) {
		EntityManager em = getEntityManager();
		try {
			UnitTypeEntity entity = getOrgUnitTypeEntity(em, typeAltKey);
			return MapperUtil.convertOrgUnitTypeEntityToApi(entity);
		} finally {
			em.close();
		}
	}

	@Override
	public Long createUnitType(final UnitType type) {
		EntityManager em = getEntityManager();
		try {
			UnitTypeEntity entity = MapperUtil.convertOrgUnitTypeApiToEntity(type);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return entity.getId();
		} finally {
			em.close();
		}
	}

	@Override
	public UnitType updateUnitType(final Long typeId, final UnitType type) {
		EntityManager em = getEntityManager();
		try {
			if (typeId == null || type == null || !Objects.equals(typeId, type.getId())) {
				// TODO throw an exception
				return type;
			}

			em.getTransaction().begin();
			UnitTypeEntity entity = getOrgUnitTypeEntity(em, typeId);
			if (entity == null) {
				// TODO throw an exception
			}
			MapperUtil.copyOrgUnitTypeApiToEntity(type, entity);
			em.merge(entity);
			em.getTransaction().commit();
			UnitType updated = MapperUtil.convertOrgUnitTypeEntityToApi(entity);
			return updated;
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteUnitType(final Long typeId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			UnitTypeEntity entity = getOrgUnitTypeEntity(em, typeId);
			if (entity != null) {
				em.remove(entity);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	/**
	 * @param em the entity manager
	 * @param id the record id
	 * @return the entity
	 */
	private UnitTypeEntity getOrgUnitTypeEntity(final EntityManager em, final Long id) {
		UnitTypeEntity entity = em.find(UnitTypeEntity.class, id);
		return entity;
	}

	/**
	 * @param em the entity manager
	 * @param altKey the record alternate key
	 * @return the entity
	 */
	private UnitTypeEntity getOrgUnitTypeEntity(final EntityManager em, final String altKey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<UnitTypeEntity> qry = cb.createQuery(UnitTypeEntity.class);
		Root<UnitTypeEntity> from = qry.from(UnitTypeEntity.class);
		qry.select(from);
		qry.where(cb.equal(from.get(UnitTypeEntity_.alternateKey), altKey));

		UnitTypeEntity entity = em.createQuery(qry).getSingleResult();
		return entity;
	}

}
