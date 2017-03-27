package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.jpa.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.entity.UnitTypeEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Map {@link UnitType} and {@link UnitTypeEntity}.
 *
 * @author jonathan
 */
public final class UnitTypeMapper {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private UnitTypeMapper() {
		// prevent instatiation
	}

	/**
	 *
	 * @param from the API item
	 * @return the entity item
	 */
	public static UnitTypeEntity convertApiToEntity(final UnitType from) {
		if (from == null) {
			return null;
		}
		Long id = MapperUtil.convertApiIdforEntity(from.getId());
		UnitTypeEntity to = new UnitTypeEntity(id, from.getBusinessKey());
		copyApiToEntity(from, to);
		return to;
	}

	/**
	 *
	 * @param from the API item
	 * @param to the entity
	 */
	public static void copyApiToEntity(final UnitType from, final UnitTypeEntity to) {
		MapperUtil.handleCommonApiToEntity(from, to);
		to.setDescription(from.getDescription());
	}

	/**
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static UnitType convertEntityToApi(final UnitTypeEntity from) {
		if (from == null) {
			return null;
		}
		UnitType to = new UnitType();
		MapperUtil.handleCommonEntityToApi(from, to);
		to.setDescription(from.getDescription());
		return to;
	}

	/**
	 * Convert {@link List} of {@link UnitTypeEntity} to {@link UnitType}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<UnitType> convertEntitiesToApis(final Collection<UnitTypeEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<UnitType> items = new ArrayList<>();
		for (UnitTypeEntity row : rows) {
			items.add(convertEntityToApi(row));
		}
		return items;
	}

}
