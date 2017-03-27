package com.github.bordertech.corpdir.jpa.common;

import java.io.InputStream;
import java.util.Properties;
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
}
