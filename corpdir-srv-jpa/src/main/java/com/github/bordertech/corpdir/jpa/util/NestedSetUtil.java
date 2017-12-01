package com.github.bordertech.corpdir.jpa.util;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * NestedSet processing helper.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class NestedSetUtil {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private NestedSetUtil() {
		// prevent instatiation
	}

	public static <T extends PersistentNestedObject> void rebuildLeftRight(final EntityManager em, final T entity, final Class<T> entityClass) {
		if (entity.getParentId() == null) {
			rebuildLeftRightThreadBasedOnParent(Arrays.asList(entity));
		} else {
			PersistentNestedObject parent = em.find(entityClass, entity.getParentId());
			List<T> entities = findByThreadId(em, parent.getThreadId(), entityClass);
			entities.add(entity);
			rebuildLeftRightThreadBasedOnParent(entities);
		}
	}

	private static <T extends PersistentNestedObject> List<T> findByThreadId(final EntityManager em, final Long threadId, final Class<T> objectClass) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> qry = cb.createQuery(objectClass);

		Root<T> from = qry.from(objectClass);
		qry.select(from);

		Predicate pred1 = cb.equal(from.get("threadId"), threadId);
		Predicate pred2 = cb.equal(from.get("id"), threadId);
		qry.where(cb.or(pred1, pred2));

		List<T> rows = em.createQuery(qry).getResultList();
		return rows;
	}

	private static void rebuildLeftRightThreadBasedOnParent(final List<? extends PersistentNestedObject> entities) {
		PersistentNestedObject root = findRootFromList(entities);
		if(root == null) {
			throw new NotFoundException("No root found for entity list");
		}
		root.setLeftIdx(0L);
		root.setThreadId(root.getId());
		long current = rebuildLeftRightThreadBasedOnParentRec(entities, root.getId(), root, 1);
		root.setRightIdx(current);
	}

	private static long rebuildLeftRightThreadBasedOnParentRec(final List<? extends PersistentNestedObject> entities, final Long threadId, final PersistentNestedObject parent,
			final long current) {
		long tmpCurrent = current;
		for (PersistentNestedObject entity : entities) {
			if (entity.getParentId() != null && entity.getParentId().equals(parent.getId())) {
				entity.setThreadId(threadId);
				entity.setLeftIdx(tmpCurrent++);
				tmpCurrent = rebuildLeftRightThreadBasedOnParentRec(entities, threadId, entity, tmpCurrent);
				entity.setRightIdx(tmpCurrent++);
			}

		}
		return tmpCurrent;
	}

	private static PersistentNestedObject findRootFromList(final List<? extends PersistentNestedObject> entities) {
		for (PersistentNestedObject entity : entities) {
			if (entity.getParentId() == null || entity.getParentId().equals(entity.getId())) {
				return entity;
			}
		}
		return null;
	}

	/**
	 *
	 * @param <T> the entity type
	 * @param cb the criteria builder
	 * @param root the root entity
	 * @param assigned flag if assigned
	 * @return the predicate for assigned
	 */
	public static <T extends PersistentNestedObject> Predicate createAssignedCriteria(final CriteriaBuilder cb, final Root<T> root, final boolean assigned) {
		Path path = root.<String>get("parent");
		Predicate pred = assigned ? cb.isNotNull(path) : cb.isNull(path);
		return pred;
	}

	public static interface PersistentNestedTreeObject<T extends PersistentNestedTreeObject> extends PersistKeyIdObject {

		void setParent(final T parent);

		T getParent();

		Set<T> getChildren();

		void addChild(final T child);

		void removeChild(final T child);
	}

	public static interface PersistentNestedObject<T extends PersistentNestedObject> extends PersistentNestedTreeObject<T> {

		Long getParentId();

		Long getThreadId();

		void setThreadId(final Long threadId);

		Long getLeftIdx();

		void setLeftIdx(final Long left);

		Long getRightIdx();

		void setRightIdx(final Long right);

	}
}
