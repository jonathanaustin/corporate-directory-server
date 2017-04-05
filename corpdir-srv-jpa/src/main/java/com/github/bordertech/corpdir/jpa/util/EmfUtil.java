package com.github.bordertech.corpdir.jpa.util;

import com.github.bordertech.corpdir.jpa.common.PersistentKeyIdObject;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Create Entity Manager Factory.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class EmfUtil {

	public static final String DEFAULT_SORT_COLUMN = "description";

	private static final EntityManagerFactory EMF;

	private static final long ROOT_OU_ID;
	private static final long ROOT_LOCATION_ID;

	static {
		Properties prop = null;
		InputStream is = EmfUtil.class.getClassLoader().getResourceAsStream("database.properties");
		if (is != null) {
			try {
				prop = new Properties();
				prop.load(is);
			} catch (Exception e) {
				prop = null;
			}
		}
		EMF = Persistence.createEntityManagerFactory("persist-unit", prop);
		OrgUnitEntity ou = createRootOrgUnit();
		ROOT_OU_ID = ou.getId();
		LocationEntity loc = createRootLocation();
		ROOT_LOCATION_ID = loc.getId();
	}

	/**
	 * Private constructor to prevent instantiation.
	 */
	private EmfUtil() {
		// prevent instatiation
	}

	/**
	 * @return the EntityManagerFactory
	 */
	public static EntityManagerFactory getEMF() {
		return EMF;
	}

	/**
	 *
	 * @param entity the entity to check
	 * @return true if root org unit
	 */
	public static boolean isRootOrgUnit(final OrgUnitEntity entity) {
		if (entity == null) {
			return false;
		}
		return Objects.equals(ROOT_OU_ID, entity.getId());
	}

	/**
	 * @return the root Org Unit
	 */
	public static OrgUnitEntity getRootOrgUnit() {
		EntityManager em = EMF.createEntityManager();
		try {
			OrgUnitEntity entity = em.find(OrgUnitEntity.class, ROOT_OU_ID);
			return entity;
		} finally {
			em.close();
		}

	}

	/**
	 *
	 * @return the root org unit id
	 */
	public static long getRootOrgUnitId() {
		return ROOT_OU_ID;
	}

	/**
	 * Create the Root Org Unit.
	 *
	 * @return the root org unit
	 */
	public static OrgUnitEntity createRootOrgUnit() {
		EntityManager em = EMF.createEntityManager();
		try {
			// Check if already exists
			String qry = "select * from OrgUnit o where o.id = o.parentOrgUnit_Id";
			List<OrgUnitEntity> rows = em.createNativeQuery(qry, OrgUnitEntity.class).getResultList();
			if (rows != null && !rows.isEmpty()) {
				if (rows.size() > 1) {
					throw new IllegalStateException("More than one root org unit.");
				}
				return rows.get(0);
			}

			// Create Root OU
			OrgUnitEntity root = new OrgUnitEntity(null);
			root.setBusinessKey("ROOT_ORG_UNIT");
			root.setDescription("Root organisation unit");
			em.getTransaction().begin();
			em.persist(root);
			root.setParentOrgUnit(root);
			em.getTransaction().commit();
			return root;
		} finally {
			em.close();
		}
	}

	/**
	 *
	 * @param entity the entity to check
	 * @return true if root location
	 */
	public static boolean isRootLocation(final LocationEntity entity) {
		if (entity == null) {
			return false;
		}
		return Objects.equals(ROOT_LOCATION_ID, entity.getId());
	}

	/**
	 * @return the Root Location
	 */
	public static LocationEntity getRootLocation() {
		EntityManager em = EMF.createEntityManager();
		try {
			LocationEntity entity = em.find(LocationEntity.class, ROOT_LOCATION_ID);
			return entity;
		} finally {
			em.close();
		}
	}

	/**
	 *
	 * @return the root location id
	 */
	public static long getRootLocationId() {
		return ROOT_LOCATION_ID;
	}

	/**
	 * Create the Root Location.
	 *
	 * @return the root location
	 */
	public static LocationEntity createRootLocation() {
		EntityManager em = EMF.createEntityManager();
		try {
			// Check if already exists
			String qry = "select * from Location l where l.id = l.parentLocation_Id";
			List<LocationEntity> rows = em.createNativeQuery(qry, LocationEntity.class).getResultList();
			if (rows != null && !rows.isEmpty()) {
				if (rows.size() > 1) {
					throw new IllegalStateException("More than one root location.");
				}
				return rows.get(0);
			}

			// Create Root Location
			LocationEntity root = new LocationEntity(null);
			root.setBusinessKey("ROOT_LOCATION");
			root.setDescription("Root location");
			em.getTransaction().begin();
			em.persist(root);
			root.setParentLocation(root);
			em.getTransaction().commit();
			return root;
		} finally {
			em.close();
		}
	}

	/**
	 *
	 * @param <T> the entity type
	 * @param cb the criteria builder
	 * @param root the root entity
	 * @param search the search text
	 * @return the predicate search
	 */
	public static <T extends PersistentKeyIdObject> Predicate createSearchTextCriteria(final CriteriaBuilder cb, final Root<T> root, final String search) {
		Predicate p1 = cb.like(root.<String>get("description"), "%" + search + "%");
		Predicate p2 = cb.like(root.<String>get("businessKey"), "%" + search + "%");
		Predicate or = cb.or(p1, p2);
		return or;
	}

	/**
	 *
	 * @param <T> the entity type
	 * @param cb the criteria builder
	 * @param root the root entity
	 * @return the default order by
	 */
	public static <T extends PersistentKeyIdObject> Order getDefaultOrderBy(final CriteriaBuilder cb, final Root<T> root) {
		return cb.asc(root.get("description"));
	}

	/**
	 *
	 * @param <T> the entity type
	 * @param cb the criteria builder
	 * @param root the root entity
	 * @param assigned flag if assigned
	 * @param parentColumn the parent column
	 * @return the predicate for assigned
	 */
	public static <T extends PersistentKeyIdObject> Predicate createAssignedCriteria(final CriteriaBuilder cb, final Root<T> root, final boolean assigned, final String parentColumn) {
		Path path = root.<String>get(parentColumn);
		Predicate pred = assigned ? cb.isNotNull(path) : cb.isNull(path);
		return pred;
	}

	/**
	 * Create an AND predicate with the list of predicates.
	 *
	 * @param <T> the entity type
	 * @param cb the criteria builder
	 * @param predicates the list of predicates
	 * @return the predicates as an AND
	 */
	public static <T extends PersistentKeyIdObject> Predicate createAndCriteria(final CriteriaBuilder cb, final List<Predicate> predicates) {
		if (predicates == null || predicates.isEmpty()) {
			return null;
		}
		if (predicates.size() == 1) {
			return predicates.get(0);
		}
		Predicate pred = cb.and(predicates.toArray(new Predicate[]{}));
		return pred;
	}

}
