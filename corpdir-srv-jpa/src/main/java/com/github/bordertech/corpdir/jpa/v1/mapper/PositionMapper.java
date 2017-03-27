package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.entity.PositionEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Map {@link Position} and {@link PositionEntity}.
 *
 * @author jonathan
 */
public final class PositionMapper {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private PositionMapper() {
		// prevent instatiation
	}

	/**
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static Position convertEntityToApi(final PositionEntity from) {
		if (from == null) {
			return null;
		}
		Position to = new Position();
		MapperUtil.handleCommonEntityToApi(from, to);
		to.setDescription(from.getDescription());
		if (from.getBelongsToOrgUnit() != null) {
			Long id = from.getBelongsToOrgUnit().getId();
			to.setBelongsToOrgUnitId(MapperUtil.convertEntityIdforApi(id));
		}
		to.setContactIds(MapperUtil.convertEntitiesToApiIDs(from.getContacts()));
		to.setManagesOrgUnitIds(MapperUtil.convertEntitiesToApiIDs(from.getManageOrgUnits()));
		to.setReportPositionIds(MapperUtil.convertEntitiesToApiIDs(from.getReportPositions()));
		return to;
	}

	/**
	 * Convert {@link List} of {@link PositionEntity} to {@link Position}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<Position> convertEntitiesToApis(final Collection<PositionEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<Position> items = new ArrayList<>();
		for (PositionEntity row : rows) {
			items.add(convertEntityToApi(row));
		}
		return items;
	}

}
