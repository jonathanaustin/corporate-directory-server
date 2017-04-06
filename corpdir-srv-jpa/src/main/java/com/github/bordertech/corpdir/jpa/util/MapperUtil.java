package com.github.bordertech.corpdir.jpa.util;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.common.ApiNestedObject;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.jpa.common.PersistentKeyIdObject;
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
	 * @return the API id format
	 */
	public static String convertEntityIdforApi(final PersistentKeyIdObject entity) {
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
		to.setId(convertEntityIdforApi(from));
		to.setBusinessKey(from.getBusinessKey());
		to.setDescription(from.getDescription());
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
		to.setBusinessKey(from.getBusinessKey());
		to.setDescription(from.getDescription());
		to.setCustom(from.isCustom());
		to.setActive(from.isActive());
		to.setVersion(from.getVersion());
	}

	/**
	 * Convert {@link Collection} of {@link PersistentKeyIdObject} to {@link List} of {@link String}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API business keys
	 */
	public static List<String> convertEntitiesToApiKeys(final Collection<? extends PersistentKeyIdObject> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		List<String> items = new ArrayList<>();
		for (PersistentKeyIdObject row : rows) {
			items.add(convertEntityIdforApi(row));
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
		if (keyId == null) {
			return null;
		}
		if (isEntityId(keyId)) {
			Long id = convertApiIdforEntity(keyId);
			return getEntityById(em, id, clazz);
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
	public static <T extends PersistentKeyIdObject> T getEntityById(final EntityManager em, final Long id, final Class<T> clazz) {
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
	public static <T extends PersistentKeyIdObject> T getEntityByBusinessKey(final EntityManager em, final String businessKey, final Class<T> clazz) {
		if (businessKey == null) {
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
	 * Check the API object IDs are valid for create.
	 *
	 * @param em the entity manager
	 * @param api the API object
	 * @param entityClass the entity class
	 * @param <T> the entity type
	 */
	public static <T extends PersistentKeyIdObject> void checkIdentifiersForCreate(final EntityManager em, final ApiKeyIdObject api, final Class<T> entityClass) {
		checkBusinessKey(em, api.getBusinessKey(), entityClass);
		// Ignore ID and version
		api.setId(null);
		api.setVersion(null);
	}

	/**
	 * Check the ID and businessKey match between the API and entity object.
	 *
	 * @param em the entity manager
	 * @param api the API object
	 * @param entity the entity object
	 * @param <T> the entity type
	 */
	public static <T extends PersistentKeyIdObject> void checkIdentifiersForUpdate(final EntityManager em, final ApiKeyIdObject api, final T entity) {
		// Check API/Entity ids match
		Long apiId = convertApiIdforEntity(api.getId());
		if (!Objects.equals(apiId, entity.getId())) {
			throw new IllegalStateException("IDs do not match [" + apiId + "] and [" + entity.getId() + "].");
		}
		// Check if business key changed
		if (!Objects.equals(api.getBusinessKey(), entity.getBusinessKey())) {
			checkBusinessKey(em, api.getBusinessKey(), entity.getClass());
		}
		if (api instanceof ApiNestedObject) {
			ApiNestedObject nested = (ApiNestedObject) api;
			if (Objects.equals(nested.getId(), nested.getParentId())) {
				throw new IllegalArgumentException("Cannot have itself as a parent OU.");
			}
			if (nested.getSubIds().contains(nested.getId())) {
				throw new IllegalArgumentException("Cannot have itself as a child OU.");
			}
		}
	}

	/**
	 * Check the Business Key.
	 *
	 * @param em the entity manager
	 * @param key the business key
	 * @param entityClass the entity class
	 * @param <T> the entity type
	 */
	public static <T extends PersistentKeyIdObject> void checkBusinessKey(final EntityManager em, final String key, final Class<T> entityClass) {
		// Check business key
		if (key == null || key.isEmpty()) {
			throw new IllegalArgumentException("Business Key must be provided.");
		}
		if (key.startsWith(ID_PREFIX)) {
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
			return Collections.EMPTY_LIST;
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
			return Collections.EMPTY_LIST;
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
