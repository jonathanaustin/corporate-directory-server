package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.api.common.ApiNestedObject;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link OrgUnit} and {@link OrgUnitEntity}.
 *
 * @param <A> the nested API
 * @param <P> the nested entity
 * @author jonathan
 */
public abstract class AbstractMapperNested<A extends ApiNestedObject, P extends PersistentNestedObject<P>> extends AbstractMapperKeyId<A, P> {

	@Override
	public P convertApiToEntity(final EntityManager em, final A from) {
		P to = super.convertApiToEntity(em, from);
		handleCopyNestedApiToEntity(em, from, to);
		return to;
	}

	@Override
	public A convertEntityToApi(final EntityManager em, final P from) {
		A to = super.convertEntityToApi(em, from);
		handleCopyNestedEntityToApi(em, from, to);
		return to;
	}

	protected void handleCopyNestedApiToEntity(final EntityManager em, final A from, final P to) {

		// Parent Entity
		String origId = MapperUtil.convertEntityIdforApi(to.getParent());
		String newId = from.getParentId();
		if (!MapperUtil.keyMatch(origId, newId)) {
			// Remove from Orig Parent
			if (origId != null) {
				P ou = getEntity(em, origId);
				ou.removeChild(to);
			}
			// Add to New Parent
			if (newId != null) {
				P ou = getEntity(em, newId);
				ou.addChild(to);
			}
		}

		// Sub Entity
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(to.getChildren());
		List<String> newIds = from.getSubIds();
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				P ou = getEntity(em, id);
				to.removeChild(ou);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				P ou = getEntity(em, id);
				to.addChild(ou);
			}
		}
	}

	protected void handleCopyNestedEntityToApi(final EntityManager em, final P from, final A to) {
		to.setParentId(MapperUtil.convertEntityIdforApi(from.getParent()));
		to.setSubIds(MapperUtil.convertEntitiesToApiKeys(from.getChildren()));
	}

	protected P getEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, getEntityClass());
	}

	protected abstract Class<P> getEntityClass();

}
