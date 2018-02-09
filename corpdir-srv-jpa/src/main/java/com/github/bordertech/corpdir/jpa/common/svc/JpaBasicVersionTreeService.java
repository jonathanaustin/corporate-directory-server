package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicVersionTreeService;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyId;
import com.github.bordertech.corpdir.jpa.common.version.ItemTreeVersion;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;

/**
 * Tree Entity service.
 *
 * @param <A> the API object type
 * @param <U> the versionable type
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class JpaBasicVersionTreeService<A extends ApiTreeable & ApiVersionable, U extends ItemTreeVersion<P, U>, P extends PersistVersionableKeyId<P, U>> extends JpaBasicVersionKeyIdService<A, U, P> implements BasicVersionTreeService<A> {

	@Override
	public DataResponse<List<A>> getRootItems() {
		return getRootItems(getCurrentVersionId());
	}

	@Override
	public DataResponse<List<A>> getSubs(final String keyId) {
		return getSubs(getCurrentVersionId(), keyId);
	}

	@Override
	public DataResponse<A> addSub(final String keyId, final String subKeyId) {
		return addSub(getCurrentVersionId(), keyId, subKeyId);
	}

	@Override
	public DataResponse<A> removeSub(final String keyId, final String subKeyId) {
		return removeSub(getCurrentVersionId(), keyId, subKeyId);
	}

	@Override
	public DataResponse<List<A>> getSubs(final Long versionId, final String keyId) {
		EntityManager em = getEntityManager();
		try {
			P entity = getEntity(em, keyId);
			ItemTreeVersion tree = entity.getVersion(versionId);
			List<A> list;
			if (tree == null) {
				list = Collections.emptyList();
			} else {
				list = getMapper().convertEntitiesToApis(em, tree.getChildrenItems(), versionId);
			}
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> addSub(final Long versionId, final String keyId, final String subKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the entity
			P entity = getEntity(em, keyId);
			// Get the sub entity
			P subEntity = getEntity(em, subKeyId);
			// Cant add an entity to itself
			if (Objects.equals(entity, subEntity)) {
				throw new IllegalArgumentException("Cannot add an Entity as a child to itself");
			}
			// Get version
			VersionCtrlEntity ctrl = getVersionCtrl(em, versionId);
			// Remove subEntity from its OLD parent (if it had one)
			U tree = subEntity.getVersion(versionId);
			if (tree != null) {
				if (Objects.equals(tree.getParentItem(), subEntity)) {
					throw new IllegalArgumentException("A entity cannot be a child and parent of the same entity.");
				}
				P oldParent = tree.getParentItem();
				if (oldParent != null) {
					oldParent.getOrCreateVersion(ctrl).removeChildItem(subEntity);
				}
			}
			// Add Child to the new parent
			entity.getOrCreateVersion(ctrl).addChildItem(subEntity);
			em.merge(entity);
			em.getTransaction().commit();
			return buildResponse(em, entity, versionId);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> removeSub(final Long versionId, final String keyId, final String subKeyId) {
		EntityManager em = getEntityManager();
		try {
			if (Objects.equals(keyId, subKeyId)) {
				throw new IllegalArgumentException("Cannot remove an entity from itself");
			}
			em.getTransaction().begin();
			// Get the entity
			P entity = getEntity(em, keyId);
			// Get the sub entity
			P subEntity = getEntity(em, subKeyId);
			// Get the version
			VersionCtrlEntity ctrl = getVersionCtrl(em, versionId);
			// Remove the sub entity
			entity.getOrCreateVersion(ctrl).removeChildItem(subEntity);
			em.merge(entity);
			em.getTransaction().commit();
			return buildResponse(em, entity, versionId);
		} finally {
			em.close();
		}
	}

	@Override
	protected void handleUpdateVerify(final EntityManager em, final A api, final P entity) {
		super.handleUpdateVerify(em, api, entity);
		if (Objects.equals(api.getId(), api.getParentId())) {
			throw new IllegalArgumentException("Cannot have itself as a parent.");
		}
		if (api.getSubIds().contains(api.getId())) {
			throw new IllegalArgumentException("Cannot have itself as a child.");
		}
		if (api.getParentId() != null && api.getSubIds().contains(api.getParentId())) {
			throw new IllegalArgumentException("A entity cannot be a child and parent of the same entity.");
		}
	}

	@Override
	public DataResponse<List<A>> getRootItems(final Long versionId) {
		EntityManager em = getEntityManager();
		try {
			List<P> rows = handleRootItems(em, versionId);
			List<A> list = getMapper().convertEntitiesToApis(em, rows, versionId);
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	protected List<P> handleRootItems(final EntityManager em, final Long versionId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E.* FROM ").append(getEntityTableName()).append(" AS E");
		sql.append(" LEFT JOIN ").append(getVersionTableName()).append(" AS V");
		sql.append(" ON E.id = V.item_id");
		sql.append(" AND V.versionCtrl_id = ").append(" :vid ");
		sql.append(" WHERE V.item_id IS NULL");
		sql.append(" OR (");
		sql.append(" V.item_id IS NOT NULL");
		sql.append(" AND V.parent_item_id IS NULL");
		sql.append(" )");
		sql.append(" ORDER BY E.description");
		Query qry = em.createNativeQuery(sql.toString(), getEntityClass());
		qry.setParameter("vid", versionId);
		return qry.getResultList();
	}

	protected String getEntityTableName() {
		Table tbl = getEntityClass().getAnnotation(Table.class);
		return tbl.name();
	}

	protected String getVersionTableName() {
		Table tbl = getVersionEntityClass().getAnnotation(Table.class);
		return tbl.name();
	}

}
