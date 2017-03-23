package com.github.bordertech.corpdir.jpa.mapper;

import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Map {@link OrgUnit} and {@link OrgUnitEntity}.
 *
 * @author jonathan
 */
public final class OrgUnitMapper {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private OrgUnitMapper() {
		// prevent instatiation
	}

	/**
	 *
	 * @param from the API item
	 * @return the entity item
	 */
	public static OrgUnitEntity convertApiToEntity(final OrgUnit from) {
		if (from == null) {
			return null;
		}
		Long id = MapperUtil.convertApiIdforEntity(from.getId());
		OrgUnitEntity to = new OrgUnitEntity(id, from.getBusinessKey());
		copyApiToEntity(from, to);
		return to;
	}

	/**
	 *
	 * @param from the API item
	 * @param to the entity
	 */
	public static void copyApiToEntity(final OrgUnit from, final OrgUnitEntity to) {
		MapperUtil.handleCommonApiToEntity(from, to);
		to.setDescription(from.getDescription());
		to.setType(UnitTypeMapper.convertApiToEntity(from.getType()));
	}

	/**
	 * @param from the entity item
	 * @return the API item
	 */
	public static OrgUnit convertEntityToApi(final OrgUnitEntity from) {
		if (from == null) {
			return null;
		}
		OrgUnit to = new OrgUnit();
		MapperUtil.handleCommonEntityToApi(from, to);
		to.setDescription(from.getDescription());
		to.setType(UnitTypeMapper.convertEntityToApi(from.getType()));
		return to;
	}

	/**
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<OrgUnit> convertEntitiesToApis(final Collection<OrgUnitEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<OrgUnit> items = new ArrayList<>();
		for (OrgUnitEntity row : rows) {
			items.add(convertEntityToApi(row));
		}
		return items;
	}

}
