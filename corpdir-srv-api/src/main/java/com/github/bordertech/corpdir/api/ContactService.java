package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.Contact;
import com.github.bordertech.corpdir.api.data.Image;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.Position;
import java.util.List;

/**
 * Contact Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ContactService {

	Contact getContact(final Long contactId);

	Contact getContact(final String contactAltKey);

	Long createContact(final Contact contact);

	Contact updateContact(final Contact contact);

	void deleteContact(final Long contactId);

	Image getContactImage(final Long contactId);

	Image getContactImage(final String contactAltKey);

	void deleteContactImage(final Long contactId);

	void assignContactImage(final Long contactId, final Long imageid);

	List<Contact> searchContacts(final String searchText, final Boolean assigned);

	List<Position> getAssignedPositions(final Long contactId);

	List<Position> getAssignedPositions(final String contactAltKey);

	List<OrgUnit> getLinkedOrgUnits(final Long contactId);

	List<OrgUnit> getLinkedOrgUnits(final String contactAltKey);

}
