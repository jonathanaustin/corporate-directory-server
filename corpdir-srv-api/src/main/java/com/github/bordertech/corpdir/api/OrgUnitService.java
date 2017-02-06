package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.Contact;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.Position;
import java.util.List;

/**
 * Organisation Unit Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitService {

	OrgUnit getOrgUnit(final Long orgUnitId);

	OrgUnit getOrgUnit(final String orgUnitAltKey);

	List<OrgUnit> getSubOrgUnits(final Long orgUnitId);

	List<OrgUnit> getSubOrgUnits(final String orgUnitAltKey);

	List<Position> getAssignedPositions(final Long orgUnitId);

	List<Position> getAssignedPositions(final String orgUnitAltKey);

	List<Contact> getLinkedContacts(final Long orgUnitId);

	List<Contact> getLinkedContacts(final String orgUnitAltKey);

	Long createOrgUnit(final OrgUnit orgUnit);

	OrgUnit updateOrgUnit(final OrgUnit orgUnit);

	void deleteOrgUnit(final Long orgUnitId);

	void assignOrgUnitToOrgUnit(final Long orgUnitId, final Long parentOrgUnitId);

	void assignPosition(final Long orgUnitId, final Long positionId);

	void unassignPosition(final Long orgUnitId, final Long positionId);

	void linkContact(final Long orgUnitId, final Long contactId);

	void unlinkContact(final Long orgUnitId, final Long contactId);

}
