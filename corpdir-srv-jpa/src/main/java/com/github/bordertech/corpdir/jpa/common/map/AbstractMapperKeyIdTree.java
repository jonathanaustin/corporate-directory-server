package com.github.bordertech.corpdir.jpa.common.map;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdTree;

/**
 * Map ApiTreeObject to a PersistentTreeObject.
 *
 * @param <A> the API tree
 * @param <P> the Persistent tree
 * @author jonathan
 */
public abstract class AbstractMapperKeyIdTree<A extends ApiKeyIdObject & ApiTreeable, P extends PersistKeyIdTree<P> & PersistKeyIdObject> extends AbstractMapperKeyId<A, P> {

	@Override
	public void copyApiToEntity(final EntityManager em, final A from, final P to) {
		super.copyApiToEntity(em, from, to);
		handleCopyTreeApiToEntity(em, from, to);
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final P from, final A to) {
		super.copyEntityToApi(em, from, to);
		handleCopyTreeEntityToApi(em, from, to);
	}

	protected void handleCopyTreeApiToEntity(final EntityManager em, final A from, final P to) {

		// Parent Entity
		String origId = MapperUtil.convertEntityIdforApi(to.getParent());
		String newId = MapperUtil.cleanApiKey(from.getParentId());
		if (!MapperUtil.keyMatch(origId, newId)) {
			// Remove from Orig Parent
			if (origId != null) {
				P ent = getEntity(em, origId);
				if (ent != null) {
					ent.removeChild(to);
				}
			}
			// Add to New Parent
			if (newId != null) {
				P ent = getEntity(em, newId);
				if (ent == null) {
					throw new IllegalStateException("New Parent [" + newId + "] could not be found.");
				}
				ent.addChild(to);
			}
		}

		// Sub Entity
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(to.getChildren());
		List<String> newIds = MapperUtil.cleanApiKeys(from.getSubIds());
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				P ent = getEntity(em, id);
				if (ent != null) {
					to.removeChild(ent);
				}
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				P ent = getEntity(em, id);
				if (ent == null) {
					throw new IllegalStateException("New child [" + newId + "] could not be found.");
				}
				to.addChild(ent);
			}
		}
	}

	protected void handleCopyTreeEntityToApi(final EntityManager em, final P from, final A to) {
		to.setParentId(MapperUtil.convertEntityIdforApi(from.getParent()));
		to.setSubIds(MapperUtil.convertEntitiesToApiKeys(from.getChildren()));
	}

	protected P getEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntityByKeyId(em, keyId, getEntityClass());
	}

	protected abstract Class<P> getEntityClass();

}
