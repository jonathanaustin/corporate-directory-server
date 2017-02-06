package com.github.bordertech.corpdir.jpa;

import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.OrgUnitType;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitTypeEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mapping utility between API objects and Entity types.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class MapperUtil {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private MapperUtil() {
		// prevent instatiation
	}

	/**
	 * Convert {@link List} of {@link OrgUnitTypeEntity} to {@link OrgUnitType}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<OrgUnitType> convertListOrgUnitTypeEntityToApi(final List<OrgUnitTypeEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<OrgUnitType> items = new ArrayList<>();
		for (OrgUnitTypeEntity row : rows) {
			items.add(convertOrgUnitTypeEntityToApi(row));
		}
		return items;
	}

	/**
	 * Convert {@link OrgUnitType} to {@link OrgUnitTypeEntity}.
	 *
	 * @param from the API item
	 * @return the entity item
	 */
	public static OrgUnitTypeEntity convertOrgUnitTypeApiToEntity(final OrgUnitType from) {
		if (from == null) {
			return null;
		}
		return copyOrgUnitTypeApiToEntity(from, new OrgUnitTypeEntity());
	}

	/**
	 * Convert {@link OrgUnitType} to {@link OrgUnitTypeEntity}.
	 *
	 * @param from the API item
	 * @param to the entity item
	 * @return the entity item
	 */
	public static OrgUnitTypeEntity copyOrgUnitTypeApiToEntity(final OrgUnitType from, final OrgUnitTypeEntity to) {
		if (from == null) {
			return to;
		}
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setCustom(from.isCustom());
		to.setDescription(from.getDescription());
		return to;
	}

	/**
	 * Convert {@link OrgUnitTypeEntity} to {@link OrgUnitType}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static OrgUnitType convertOrgUnitTypeEntityToApi(final OrgUnitTypeEntity from) {
		if (from == null) {
			return null;
		}
		OrgUnitType to = new OrgUnitType();
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setCustom(from.isCustom());
		to.setDescription(from.getDescription());
		return to;
	}

	/**
	 * Convert {@link List} of {@link OrgUnitEntity} to {@link OrgUnit}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<OrgUnit> convertListOrgUnitEntityToApi(final List<OrgUnitEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<OrgUnit> items = new ArrayList<>();
		for (OrgUnitEntity row : rows) {
			items.add(convertOrgUnitEntityToApi(row));
		}
		return items;
	}

	/**
	 * Convert {@link OrgUnit} to {@link OrgUnitEntity}.
	 *
	 * @param from the API item
	 * @return the entity item
	 */
	public static OrgUnitEntity convertOrgUnitApiToEntity(final OrgUnit from) {
		if (from == null) {
			return null;
		}
		return copyOrgUnitApiToEntity(from, new OrgUnitEntity());
	}

	/**
	 * Convert {@link OrgUnit} to {@link OrgUnitEntity}.
	 *
	 * @param from the API item
	 * @param to the entity item
	 * @return the entity item
	 */
	public static OrgUnitEntity copyOrgUnitApiToEntity(final OrgUnit from, final OrgUnitEntity to) {
		if (from == null) {
			return to;
		}
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setDescription(from.getDescription());
		to.setActive(from.isActive());
		to.setCustom(from.isCustom());
		OrgUnitTypeEntity type = convertOrgUnitTypeApiToEntity(from.getType());
		to.setType(type);
		return to;
	}

	/**
	 * Convert {@link OrgUnitEntity} to {@link OrgUnit}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static OrgUnit convertOrgUnitEntityToApi(final OrgUnitEntity from) {
		if (from == null) {
			return null;
		}
		OrgUnit to = new OrgUnit();
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setDescription(from.getDescription());
		to.setActive(from.isActive());
		to.setCustom(from.isCustom());
		OrgUnitType type = convertOrgUnitTypeEntityToApi(from.getType());
		to.setType(type);
		return to;
	}

}
