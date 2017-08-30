package com.github.bordertech.corpdir.jpa.common.map;

import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;
import com.github.bordertech.corpdir.jpa.common.PersistentKeyIdTreeObject;
import com.github.bordertech.corpdir.api.common.ApiKeyIdTreeObject;

/**
 * Map ApiTreeObject to a PersistentTreeObject.
 *
 * @param <A> the API tree
 * @param <P> the Persistent tree
 * @author jonathan
 */
public abstract class AbstractMapperKeyIdTree<A extends ApiKeyIdTreeObject, P extends PersistentKeyIdTreeObject<P>> extends AbstractMapperKeyId<A, P> {

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
				P ou = getEntity(em, origId);
				if (ou != null) {
					ou.removeChild(to);
				}
			}
			// Add to New Parent
			if (newId != null) {
				P ou = getEntity(em, newId);
				if (ou == null) {
					throw new IllegalStateException("New Parent [" + newId + "] could not be found.");
				}
				ou.addChild(to);
			}
		}

		// Sub Entity
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(to.getChildren());
		List<String> newIds = MapperUtil.cleanApiKeys(from.getSubIds());
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				P ou = getEntity(em, id);
				if (ou != null) {
					to.removeChild(ou);
				}
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				P ou = getEntity(em, id);
				if (ou == null) {
					throw new IllegalStateException("New child [" + newId + "] could not be found.");
				}
				to.addChild(ou);
			}
		}
	}

	protected void handleCopyTreeEntityToApi(final EntityManager em, final P from, final A to) {
		to.setParentId(MapperUtil.convertEntityIdforApi(from.getParent()));
		to.setSubIds(MapperUtil.convertEntitiesToApiKeys(from.getChildren()));
	}

	protected P getEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, getEntityClass());
	}

	protected abstract Class<P> getEntityClass();

}
