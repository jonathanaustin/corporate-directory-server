package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Mapping utility between API objects and Entity types.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class MapperUtil {

	/**
	 * ID prefix for API.
	 */
	private static final String ID_PREFIX = "_";

	/**
	 * Private constructor to prevent instantiation.
	 */
	private MapperUtil() {
		// prevent instatiation
	}

	/**
	 *
	 * @param entity the entity
	 * @return the business key
	 */
	public static String getEntityBusinessKey(final PersistentKeyIdObject entity) {
		if (entity == null) {
			return null;
		}
		return entity.getBusinessKey();
	}

	/**
	 *
	 * @param id the entity id value
	 * @return the API id format
	 */
	public static String convertEntityIdforApi(final Long id) {
		if (id == null) {
			return null;
		}
		String convert = ID_PREFIX + String.valueOf(id);
		return convert;
	}

	/**
	 *
	 * @param id the API id format
	 * @return the entity id value
	 */
	public static Long convertApiIdforEntity(final String id) {
		if (id == null) {
			return null;
		}
		String value = id.substring(1);
		Long convert = Long.valueOf(value);
		return convert;
	}

	/**
	 *
	 * @param testId the id to test if its an API format entity ID
	 * @return true if an entity ID
	 */
	public static boolean isEntityId(final String testId) {
		if (testId == null || !testId.startsWith(ID_PREFIX)) {
			return false;
		}
		String regex = "^" + Pattern.quote(ID_PREFIX) + "\\d+$";
		return testId.matches(regex);
	}

	/**
	 * Copy common fields from Entity to API.
	 *
	 * @param from the persistent entity
	 * @param to the API object
	 */
	public static void handleCommonKeyedEntityToApi(final PersistentKeyIdObject from, final ApiKeyIdObject to) {
		to.setId(convertEntityIdforApi(from.getId()));
		to.setBusinessKey(from.getBusinessKey());
		to.setCustom(from.isCustom());
		to.setActive(from.isActive());
		to.setVersion(from.getVersion());
	}

	/**
	 * Copy common fields from API to Entity.
	 *
	 * @param from the API object
	 * @param to the persistent entity
	 */
	public static void handleCommonKeyedApiToEntity(final ApiKeyIdObject from, final PersistentKeyIdObject to) {
		to.setCustom(from.isCustom());
		to.setActive(from.isActive());
		Timestamp tmsp = from.getVersion() == null ? null : new Timestamp(from.getVersion().getTime());
		to.setVersion(tmsp);
	}

	/**
	 * Convert {@link Collection} of {@link PersistentKeyIdObject} to {@link List} of {@link String}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API Keys
	 */
	public static List<String> convertEntitiesToApiKeys(final Collection<? extends PersistentKeyIdObject> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		List<String> items = new ArrayList<>();
		for (PersistentKeyIdObject row : rows) {
			items.add(convertEntityIdforApi(row.getId()));
		}
		return items;
	}

	/**
	 * @param em the entity manager
	 * @param keyId the key or API id
	 * @param clazz the entity class
	 * @param <T> the entity
	 * @return the entity
	 */
	public static <T extends PersistentKeyIdObject> T getEntity(final EntityManager em, final String keyId, final Class<T> clazz) {
		T entity;
		if (isEntityId(keyId)) {
			Long id = convertApiIdforEntity(keyId);
			entity = em.find(clazz, id);
		} else {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<T> qry = cb.createQuery(clazz);
			Root<T> from = qry.from(clazz);
			qry.select(from);
			qry.where(cb.equal(from.get("businessKey"), keyId));
			try {
				entity = em.createQuery(qry).getSingleResult();
			} catch (NoResultException e) {
				entity = null;
			}
		}
		return entity;
	}

	/**
	 * Check the ID and businessKey match between the API and entity object.
	 *
	 * @param api the API object
	 * @param entity the entity object
	 */
	public static void checkIdentifiersMatch(final ApiKeyIdObject api, final PersistentKeyIdObject entity) {
		Long id = convertApiIdforEntity(api.getId());
		// Check ids
		if (!Objects.equals(id, entity.getId())) {
			throw new IllegalStateException("IDs do not match [" + id + "] and [" + entity.getId() + "].");
		}
		// Check business keys
		if (!Objects.equals(api.getBusinessKey(), entity.getBusinessKey())) {
			throw new IllegalStateException("Business Keys do not match [" + api.getBusinessKey() + "] and [" + entity.getBusinessKey() + "].");
		}
		// TODO Maybe check version as well
	}

	/**
	 * Check the API object IDs are valid for create.
	 *
	 * @param api the API object
	 */
	public static void checkApiIDsForCreate(final ApiKeyIdObject api) {
		// Check business key
		String key = api.getBusinessKey();
		if (key == null || key.isEmpty()) {
			throw new IllegalArgumentException("Business Key must be provided.");
		}
		if (key.startsWith(ID_PREFIX)) {
			throw new IllegalArgumentException("Business Key cannot start with a reserved character.");
		}
		// Ignore ID and version
		api.setId(null);
		api.setVersion(null);
	}

}
