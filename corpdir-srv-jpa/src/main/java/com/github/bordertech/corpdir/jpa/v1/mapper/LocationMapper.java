package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import com.github.bordertech.corpdir.jpa.util.EmfUtil;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link Location} and {@link LocationEntity}.
 *
 * @author jonathan
 */
public class LocationMapper extends AbstractKeyIdApiEntityMapper<Location, LocationEntity> {

	private static final AddressMapper ADDRESS_MAPPER = new AddressMapper();

	@Override
	public void copyApiToEntityFields(final EntityManager em, final Location from, final LocationEntity to) {

		boolean root = EmfUtil.isRootLocation(to);

		to.setAddress(ADDRESS_MAPPER.convertApiToEntity(em, from.getAddress()));

		// Parent Location - (Never changes for ROOT)
		if (!root) {
			String origId = MapperUtil.convertEntityIdforApi(to.getParentLocation());
			String newId = from.getParentId();
			if (!MapperUtil.keyMatch(origId, newId)) {
				// Remove from Orig Parent
				if (origId != null) {
					LocationEntity loc = getLocationEntity(em, origId);
					loc.removeSubLocation(to);
				}
				// Add to New Parent
				if (newId != null) {
					LocationEntity loc = getLocationEntity(em, newId);
					loc.addSubLocation(to);
				}
			}
		}

		// Sub Locations
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(to.getSubLocations());
		List<String> newIds = from.getSubIds();
		// Make Sure ROOT always has itself as a sub unit
		if (root) {
			newIds.add(MapperUtil.convertEntityIdforApi(to));
		}
		if (!MapperUtil.keysMatch(newIds, origIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				LocationEntity loc = getLocationEntity(em, id);
				to.removeSubLocation(loc);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				LocationEntity loc = getLocationEntity(em, id);
				to.addSubLocation(loc);
			}
		}

	}

	@Override
	public void copyEntityToApiFields(final EntityManager em, final LocationEntity from, final Location to) {
		boolean root = EmfUtil.isRootLocation(from);

		to.setAddress(ADDRESS_MAPPER.convertEntityToApi(em, from.getAddress()));
		to.setParentId(MapperUtil.convertEntityIdforApi(from.getParentLocation()));

		List<String> subIds = MapperUtil.convertEntitiesToApiKeys(from.getSubLocations());
		// For ROOT - Dont send itself as a child to the API
		if (root) {
			subIds.remove(MapperUtil.convertEntityIdforApi(from));
		}
		to.setSubIds(subIds);
	}

	@Override
	protected Location createApiObject() {
		return new Location();
	}

	@Override
	protected LocationEntity createEntityObject(final Long id) {
		return new LocationEntity(id);
	}

	protected LocationEntity getLocationEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, LocationEntity.class);
	}

}
