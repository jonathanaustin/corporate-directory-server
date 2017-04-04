package com.github.bordertech.corpdir.jpa.util;

import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Create Entity Manager Factory.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class EmfUtil {

	private static final EntityManagerFactory EMF;

	private static final long ROOT_OU_ID;

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
		OrgUnitEntity root = createRootOrgUnit();
		ROOT_OU_ID = root.getId();
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
	 * @return the Root Org Unit
	 */
	public static OrgUnitEntity getRootOrgUnit() {
		EntityManager em = EMF.createEntityManager();
		OrgUnitEntity entity = em.find(OrgUnitEntity.class, ROOT_OU_ID);
		return entity;
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
			OrgUnitEntity root = new OrgUnitEntity(null, "ROOT_ORG_UNIT");
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

}
