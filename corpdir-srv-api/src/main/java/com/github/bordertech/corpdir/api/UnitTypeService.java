package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.UnitType;
import java.util.List;

/**
 * Organisation Unit Type Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface UnitTypeService {

	List<UnitType> getUnitTypes();

	List<OrgUnit> getOrgUnits(final Long typeId);

	List<OrgUnit> getOrgUnits(final String typeAltKey);

	UnitType getUnitType(final Long typeId);

	UnitType getUnitType(final String typeAltKey);

	Long createUnitType(final UnitType type);

	UnitType updateUnitType(final Long typeId, final UnitType type);

	void deleteUnitType(final Long typeId);

}
