package com.github.bordertech.corpdir.jpa.util;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.github.bordertech.corpdir.jpa.common.PersistentNestedSet;

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

	public static <T extends PersistentNestedSet> void rebuildLeftRight(final EntityManager em, final T entity, final Class<T> entityClass) {
		if (entity.getParentId() == null) {
			rebuildLeftRightThreadBasedOnParent(Arrays.asList(entity));
		} else {
			PersistentNestedSet parent = em.find(entityClass, entity.getParentId());
			List<T> entities = findByThreadId(em, parent.getThreadId(), entityClass);
			entities.add(entity);
			rebuildLeftRightThreadBasedOnParent(entities);
		}
	}

	private static <T extends PersistentNestedSet> List<T> findByThreadId(final EntityManager em, final Long threadId, final Class<T> objectClass) {

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

	private static void rebuildLeftRightThreadBasedOnParent(final List<? extends PersistentNestedSet> entities) {
		PersistentNestedSet root = findRootFromList(entities);
		root.setLeftIdx(0L);
		root.setThreadId(root.getId());
		long current = rebuildLeftRightThreadBasedOnParentRec(entities, root.getId(), root, 1);
		root.setRightIdx(current);
	}

	private static long rebuildLeftRightThreadBasedOnParentRec(final List<? extends PersistentNestedSet> entities, final Long threadId, final PersistentNestedSet parent,
			final long current) {
		long tmpCurrent = current;
		for (PersistentNestedSet entity : entities) {
			if (entity.getParentId() != null && entity.getParentId().equals(parent.getId())) {
				entity.setThreadId(threadId);
				entity.setLeftIdx(tmpCurrent++);
				tmpCurrent = rebuildLeftRightThreadBasedOnParentRec(entities, threadId, entity, tmpCurrent);
				entity.setRightIdx(tmpCurrent++);
			}

		}
		return tmpCurrent;
	}

	private static PersistentNestedSet findRootFromList(final List<? extends PersistentNestedSet> entities) {
		for (PersistentNestedSet entity : entities) {
			if (entity.getParentId() == null || entity.getParentId().equals(entity.getId())) {
				return entity;
			}
		}
		return null;
	}

}
