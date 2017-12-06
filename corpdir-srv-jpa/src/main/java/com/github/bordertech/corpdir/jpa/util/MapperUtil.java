package com.github.bordertech.corpdir.jpa.util;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.jpa.common.feature.PersistIdObject;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import java.util.ArrayList;
import java.util.Collection;
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
	 * Private constructor to prevent instantiation.
	 */
	private MapperUtil() {
		// prevent instatiation
	}

	/**
	 *
	 * @param entity the entity
	 * @return the API id format
	 */
	public static String convertEntityIdforApi(final PersistIdObject entity) {
		if (entity == null) {
			return null;
		}
		if (entity.getId() == null) {
			throw new IllegalStateException("Entity does not have an ID.");
		}
		return convertEntityIdforApi(entity.getId());
	}

	/**
	 *
	 * @param id the entity id
	 * @return the API id format
	 */
	public static String convertEntityIdforApi(final Long id) {
		if (id == null) {
			return null;
		}
		String convert = ApiIdObject.ID_PREFIX + String.valueOf(id);
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
		if (id.startsWith(ApiIdObject.TEMP_NEW_ID_PREFIX)) {
			return null;
		}
		String value = id.substring(1);
		Long convert = Long.valueOf(value);
		return convert;
	}

	/**
	 *
	 * @param testId the id to test if its an API temporary ID
	 * @return true if a temporary id
	 */
	public static boolean isTempId(final String testId) {
		if (testId == null || testId.startsWith(ApiIdObject.TEMP_NEW_ID_PREFIX)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param testId the id to test if its an API format entity ID
	 * @return true if an entity ID
	 */
	public static boolean isEntityId(final String testId) {
		if (testId == null || !testId.startsWith(ApiIdObject.ID_PREFIX)) {
			return false;
		}
		String regex = "^" + Pattern.quote(ApiIdObject.ID_PREFIX) + "\\d+$";
		return testId.matches(regex);
	}

	/**
	 * Convert {@link Collection} of {@link PersistKeyIdObject} to {@link List} of {@link String}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API business keys
	 */
	public static List<String> convertEntitiesToApiKeys(final Collection<? extends PersistIdObject> rows) {
		List<String> items = new ArrayList<>();
		if (rows != null) {
			for (PersistIdObject row : rows) {
				if (row != null) {
					items.add(convertEntityIdforApi(row));
				}
			}
		}
		return items;
	}

	/**
	 * Clean out any null or empty API keys.
	 *
	 * @param keys the list of API keys
	 * @return the list of clean API keys
	 */
	public static List<String> cleanApiKeys(final Collection<String> keys) {
		List<String> items = new ArrayList<>();
		if (keys != null) {
			for (String key : keys) {
				if (key != null && !key.isEmpty()) {
					items.add(key);
				}
			}
		}
		return items;
	}

	/**
	 * Clean API key.
	 *
	 * @param key the API key
	 * @return the clean API key
	 */
	public static String cleanApiKey(final String key) {
		if (key == null || key.isEmpty()) {
			return null;
		}
		return key;
	}

	/**
	 * @param em the entity manager
	 * @param keyId the key or API id
	 * @param clazz the entity class
	 * @param <T> the entity
	 * @return the entity
	 */
	public static <T extends PersistKeyIdObject> T getEntityByKeyId(final EntityManager em, final String keyId, final Class<T> clazz) {
		if (keyId == null || keyId.isEmpty()) {
			return null;
		}
		if (isEntityId(keyId)) {
			return getEntityByApiId(em, keyId, clazz);
		} else {
			return getEntityByBusinessKey(em, keyId, clazz);
		}
	}

	/**
	 * @param em the entity manager
	 * @param id the record id
	 * @param clazz the entity class
	 * @param <T> the entity
	 * @return the entity
	 */
	public static <T extends PersistIdObject> T getEntityByApiId(final EntityManager em, final String id, final Class<T> clazz) {
		if (id == null) {
			return null;
		}
		Long longId = convertApiIdforEntity(id);
		return getEntityById(em, longId, clazz);
	}

	/**
	 * @param em the entity manager
	 * @param id the record id
	 * @param clazz the entity class
	 * @param <T> the entity
	 * @return the entity
	 */
	public static <T extends PersistIdObject> T getEntityById(final EntityManager em, final Long id, final Class<T> clazz) {
		if (id == null) {
			return null;
		}
		T entity = em.find(clazz, id);
		return entity;
	}

	/**
	 * @param em the entity manager
	 * @param businessKey the key or API id
	 * @param clazz the entity class
	 * @param <T> the entity
	 * @return the entity
	 */
	public static <T extends PersistKeyIdObject> T getEntityByBusinessKey(final EntityManager em, final String businessKey, final Class<T> clazz) {
		if (businessKey == null || businessKey.isEmpty()) {
			return null;
		}
		T entity;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> qry = cb.createQuery(clazz);
		Root<T> from = qry.from(clazz);
		qry.select(from);
		qry.where(cb.equal(from.get("businessKey"), businessKey));
		try {
			entity = em.createQuery(qry).getSingleResult();
		} catch (NoResultException e) {
			entity = null;
		}
		return entity;
	}

	/**
	 * Check the Business Key.
	 *
	 * @param em the entity manager
	 * @param key the business key
	 * @param entityClass the entity class
	 * @param <T> the entity type
	 */
	public static <T extends PersistKeyIdObject> void checkBusinessKey(final EntityManager em, final String key, final Class<T> entityClass) {
		// Check business key
		if (key == null || key.isEmpty()) {
			throw new IllegalArgumentException("Business Key must be provided.");
		}
		if (key.startsWith(ApiIdObject.ID_PREFIX)) {
			throw new IllegalArgumentException("Business Key cannot start with a reserved character.");
		}
		T other = getEntityByBusinessKey(em, key, entityClass);
		if (other != null) {
			throw new ServiceException("Business key [" + key + "] already in use.");
		}
	}

	/**
	 * Check if keys match.
	 *
	 * @param key1 entity key
	 * @param key2 entity key
	 * @return true if keys are equal
	 */
	public static boolean keyMatch(final String key1, final String key2) {
		// Treat Null and Empty as the same
		if ((key1 == null || key1.isEmpty()) && (key2 == null || key2.isEmpty())) {
			return true;
		}
		return Objects.equals(key1, key2);
	}

	/**
	 * Check if two collection of keys match.
	 *
	 * @param keys1 set of keys
	 * @param keys2 set of keys
	 * @return true if collection of keys match
	 */
	public static boolean keysMatch(final Collection<String> keys1, final Collection<String> keys2) {
		// Treat Null and Empty as the same
		if ((keys1 == null || keys1.isEmpty()) && (keys2 == null || keys2.isEmpty())) {
			return true;
		}
		return (keys1 != null && keys2 != null && keys1.size() == keys2.size() && keys1.containsAll(keys2));
	}

	/**
	 * Return the Keys that have been removed from the original collection.
	 *
	 * @param origKeys collection of original keys
	 * @param newKeys collection of new keys
	 * @return list of keys no longer in the original collection
	 */
	public static List<String> keysRemoved(final Collection<String> origKeys, final Collection<String> newKeys) {
		// All Keys new (ie none removed)
		if (origKeys == null || origKeys.isEmpty()) {
			return new ArrayList<>();
		}
		// All keys removed
		if (newKeys == null || newKeys.isEmpty()) {
			return new ArrayList<>(origKeys);
		}
		// Check for those removed
		List<String> copy = new ArrayList<>(origKeys);
		copy.removeAll(newKeys);
		return copy;
	}

	/**
	 * Return the Keys that have been added since the original collection.
	 *
	 * @param origKeys collection of original keys
	 * @param newKeys collection of new keys
	 * @return list of keys added to the original collection
	 */
	public static List<String> keysAdded(final Collection<String> origKeys, final Collection<String> newKeys) {
		// No Keys added
		if (newKeys == null || newKeys.isEmpty()) {
			return new ArrayList<>();
		}
		// All Keys new (ie all added)
		if (origKeys == null || origKeys.isEmpty()) {
			return new ArrayList<>(newKeys);
		}
		// Check for those added
		List<String> copy = new ArrayList<>(newKeys);
		copy.removeAll(origKeys);
		return copy;
	}

}
