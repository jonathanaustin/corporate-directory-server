package com.github.bordertech.corpdir.jpa.common.map;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableTree;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link ApiVersionable} and {@link PersistVersionableTree}.
 *
 * @param <A> the nested API
 * @param <U> the version data type
 * @param <P> the tree entity
 * @author jonathan
 */
public abstract class AbstractMapperVersionTree<A extends ApiVersionable & ApiTreeable, U extends PersistVersionableTree<U, P>, P extends PersistVersionData<U>> extends AbstractMapperVersion<A, U, P> {

	@Override
	public void copyApiToEntity(final EntityManager em, final A from, final P to, final Long versionId) {
		super.copyApiToEntity(em, from, to, versionId);
		handleCopyTreeApiToEntity(em, from, to, getVersionCtrl(em, versionId));
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final P from, final A to, final Long versionId) {
		super.copyEntityToApi(em, from, to, versionId);
		handleCopyTreeEntityToApi(em, from, to, versionId);
	}

	protected void handleCopyTreeApiToEntity(final EntityManager em, final A from, final P to, final VersionCtrlEntity ctrl) {

		// Get the tree version for this entity
		U toTree = to.getOrCreateDataVersion(ctrl);

		// Parent Entity
		String origId = MapperUtil.convertEntityIdforApi(toTree.getParentItem());
		String newId = MapperUtil.cleanApiKey(from.getParentId());
		// Parents dont match, update the parent entities
		if (!MapperUtil.keyMatch(origId, newId)) {
			// Clear current parent
			toTree.setParentItem(null);
			// Remove from Orig Parent
			if (origId != null) {
				P ent = getEntity(em, origId);
				if (ent != null) {
					ent.getOrCreateDataVersion(ctrl).removeChildItem(to);
				}
			}
			// Add to New Parent
			if (newId != null) {
				P ent = getEntity(em, newId);
				if (ent == null) {
					throw new IllegalStateException("New Parent [" + newId + "] could not be found.");
				}
				ent.getOrCreateDataVersion(ctrl).addChildItem(to);
			}
		}

		// Sub Entities
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(toTree.getChildrenItems());
		List<String> newIds = MapperUtil.cleanApiKeys(from.getSubIds());
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				P ent = getEntity(em, id);
				if (ent != null) {
					toTree.removeChildItem(ent);
				}
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				P ent = getEntity(em, id);
				if (ent == null) {
					throw new IllegalStateException("New child [" + newId + "] could not be found.");
				}
				toTree.addChildItem(ent);
			}
		}
	}

	protected void handleCopyTreeEntityToApi(final EntityManager em, final P from, final A to, final Long versionId) {
		// Get the tree version for this entity
		U tree = from.getDataVersion(versionId);
		if (tree != null) {
			to.setParentId(MapperUtil.convertEntityIdforApi(tree.getParentItem()));
			to.setSubIds(MapperUtil.convertEntitiesToApiKeys(tree.getChildrenItems()));
		}
	}

}
