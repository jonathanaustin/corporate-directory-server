package com.github.bordertech.corpdir.api.service;

import com.github.bordertech.corpdir.api.data.Contact;
import com.github.bordertech.corpdir.api.data.Image;
import com.github.bordertech.corpdir.api.data.Location;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.OrgUnitType;
import com.github.bordertech.corpdir.api.data.Position;
import java.util.List;

/**
 * Corporate Directory Service Admin Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CorpDirAdminService {

	Contact getContact(final Long contactId);

	Long createContact(final Contact contact);

	void updateContact(final Contact contact);

	void deleteContact(final Long contactId);

	void assignContactToPosition(final Long contactId, final Long positionId);

	void unassignContactFromPosition(final Long contactId, final Long positionId);

	void addContactToOrgUnit(final Long contactId, final Long positionId);

	void removeContactFromOrgUnit(final Long contactId, final Long orgUnitId);

	Image getImage(final Long imageId);

	Long createImage(final Image image);

	void updateImage(final Image image);

	void deleteImage(final Long imageId);

	Location getLocation(final Long locationId);

	List<Location> getSubLocations(final Long locationId);

	Long createLocation(final Location location, final Long parentId);

	void moveLocation(final Long id, final Long newParentId);

	void updateLocation(final Location location);

	void deleteLocation(final Long locationId);

	OrgUnit getOrgUnit(final Long orgUnitId);

	List<OrgUnit> getSubOrgUnits(final Long orgUnitId);

	List<Position> getOrgUnitPositions(final Long orgUnitId);

	List<Contact> getOrgUnitContacts(final Long orgUnitId);

	Long createOrgUnit(final OrgUnit orgUnit, final Long parentId);

	void moveOrgUnit(final Long id, final Long newParentId);

	void updateOrgUnit(final OrgUnit orgUnit);

	void deleteOrgUnit(final Long orgUnitId);

	OrgUnitType getOrgUnitType(final Long typeId);

	Long createOrgUnitType(final OrgUnitType type);

	void updateOrgUnitType(final OrgUnitType type);

	void deleteOrgUnitType(final Long typeId);

	Position getPosition(final Long positionId);

	List<Position> getSubPositions(final Long positionId);

	List<Contact> getPositionContacts(final Long positionId);

	void movePosition(final Long id, final Long parentId);

	void movePositionToOrgUnit(final Long id, final Long orgUnitId);

	Long createPosition(final Position position, final Long parentId, final Long orgUnitId);

}
