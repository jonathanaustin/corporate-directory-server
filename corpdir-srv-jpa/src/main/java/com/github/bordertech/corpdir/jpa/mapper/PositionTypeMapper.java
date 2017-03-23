package com.github.bordertech.corpdir.jpa.mapper;

import com.github.bordertech.corpdir.api.data.PositionType;
import com.github.bordertech.corpdir.jpa.entity.PositionTypeEntity;

/**
 * Map {@link PositionType} and {@link PositionTypeEntity}.
 *
 * @author jonathan
 */
public final class PositionTypeMapper {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private PositionTypeMapper() {
		// prevent instatiation
	}

	/**
	 *
	 * @param from the API item
	 * @return the entity item
	 */
	public static PositionTypeEntity convertApiToEntity(final PositionType from) {
		if (from == null) {
			return null;
		}
		Long id = MapperUtil.convertApiIdforEntity(from.getId());
		PositionTypeEntity to = new PositionTypeEntity(id, from.getBusinessKey());
		MapperUtil.handleCommonApiToEntity(from, to);
		to.setDescription(from.getDescription());
		to.setTypeLevel(from.getTypeLevel());
		return to;
	}

}
