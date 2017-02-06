package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.OrgUnitType;
import java.util.List;

/**
 * Organisation Unit Type Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitTypeService {

	List<OrgUnitType> getOrgUnitTypes();

	List<OrgUnit> getOrgUnits(final Long typeId);

	List<OrgUnit> getOrgUnits(final String typeAltKey);

	OrgUnitType getOrgUnitType(final Long typeId);

	OrgUnitType getOrgUnitType(final String typeAltKey);

	Long createOrgUnitType(final OrgUnitType type);

	OrgUnitType updateOrgUnitType(final Long typeId, final OrgUnitType type);

	void deleteOrgUnitType(final Long typeId);

}
