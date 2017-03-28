package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.jpa.common.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.v1.entity.UnitTypeEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;

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
	 * @param em the entity manager
	 * @param from the API item
	 * @return the entity item
	 */
	public static OrgUnitEntity convertApiToEntity(final EntityManager em, final OrgUnit from) {
		if (from == null) {
			return null;
		}
		Long id = MapperUtil.convertApiIdforEntity(from.getId());
		OrgUnitEntity to = new OrgUnitEntity(id, from.getBusinessKey());
		copyApiToEntity(em, from, to);
		return to;
	}

	/**
	 *
	 * @param em the entity manager
	 * @param from the API item
	 * @param to the entity
	 */
	public static void copyApiToEntity(final EntityManager em, final OrgUnit from, final OrgUnitEntity to) {
		MapperUtil.handleCommonApiToEntity(from, to);
		to.setDescription(from.getDescription());
		UnitTypeEntity type = MapperUtil.getEntity(em, from.getTypeKey(), UnitTypeEntity.class);
		to.setType(type);
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
		to.setTypeKey(MapperUtil.getEntityBusinessKey(from.getType()));
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
