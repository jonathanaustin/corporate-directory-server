package com.github.bordertech.corpdir.jpa;

import com.github.bordertech.corpdir.api.OrgUnitTypeService;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.OrgUnitType;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity_;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitTypeEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitTypeEntity_;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Organization unit type JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrgUnitTypeServiceImpl implements OrgUnitTypeService {

	private EntityManagerFactory emf;

	/**
	 * @param emf the entity manager factory
	 */
	@PersistenceUnit
	public void setEmf(final EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<OrgUnitType> getOrgUnitTypes() {

		EntityManager em = emf.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<OrgUnitTypeEntity> qry = cb.createQuery(OrgUnitTypeEntity.class);

			Root<OrgUnitTypeEntity> from = qry.from(OrgUnitTypeEntity.class);
			qry.select(from);

			List<OrgUnitTypeEntity> rows = em.createQuery(qry).getResultList();

			return MapperUtil.convertListOrgUnitTypeEntityToApi(rows);
		} finally {
			em.close();
		}

	}

	@Override
	public List<OrgUnit> getOrgUnits(final Long typeId) {
		EntityManager em = emf.createEntityManager();
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
		EntityManager em = emf.createEntityManager();
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
	public OrgUnitType getOrgUnitType(final Long typeId) {
		EntityManager em = emf.createEntityManager();
		try {
			OrgUnitTypeEntity entity = em.find(OrgUnitTypeEntity.class, typeId);
			if (entity == null) {
				return null;
			}
			return MapperUtil.convertOrgUnitTypeEntityToApi(entity);
		} finally {
			em.close();
		}
	}

	@Override
	public OrgUnitType getOrgUnitType(final String typeAltKey) {
		EntityManager em = emf.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<OrgUnitTypeEntity> qry = cb.createQuery(OrgUnitTypeEntity.class);
			Root<OrgUnitTypeEntity> from = qry.from(OrgUnitTypeEntity.class);
			qry.select(from);
			qry.where(cb.equal(from.get(OrgUnitTypeEntity_.alternateKey), typeAltKey));

			OrgUnitTypeEntity entity = em.createQuery(qry).getSingleResult();
			if (entity == null) {
				return null;
			}

			return MapperUtil.convertOrgUnitTypeEntityToApi(entity);
		} finally {
			em.close();
		}
	}

	@Override
	public Long createOrgUnitType(final OrgUnitType type) {
		EntityManager em = emf.createEntityManager();
		try {
			OrgUnitTypeEntity entity = MapperUtil.convertOrgUnitTypeApiToEntity(type);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return entity.getId();
		} finally {
			em.close();
		}
	}

	@Override
	public OrgUnitType updateOrgUnitType(final Long typeId, final OrgUnitType type) {
		EntityManager em = emf.createEntityManager();
		try {
			if (typeId == null || type == null || !Objects.equals(typeId, type.getId())) {
				// TODO throw an exception
			}

			em.getTransaction().begin();
			OrgUnitTypeEntity entity = em.find(OrgUnitTypeEntity.class, typeId);
			if (entity == null) {
				// TODO throw an exception
			}
			MapperUtil.copyOrgUnitTypeApiToEntity(type, entity);
			em.merge(entity);
			em.getTransaction().commit();
			OrgUnitType updated = MapperUtil.convertOrgUnitTypeEntityToApi(entity);
			return updated;
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteOrgUnitType(final Long typeId) {
		EntityManager em = emf.createEntityManager();
		try {
			OrgUnitTypeEntity entity = em.find(OrgUnitTypeEntity.class, typeId);
			if (entity != null) {
				em.getTransaction().begin();
				em.remove(entity);
				em.getTransaction().commit();
			}
		} finally {
			em.close();
		}
	}

}
