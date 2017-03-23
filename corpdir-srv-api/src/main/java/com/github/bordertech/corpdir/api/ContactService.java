package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.Contact;
import com.github.bordertech.corpdir.api.data.Position;
import java.util.List;

/**
 * Contact Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ContactService {

	Contact getContact(final String contactKeyId);

	String createContact(final Contact contact);

	Contact updateContact(final String contactKeyId, final Contact contact);

	void deleteContact(final String contactKeyId);

	byte[] getContactImage(final String contactKeyId);

	byte[] getContactThumbnail(final String contactKeyId);

	void deleteContactImage(final String contactKeyId);

	void updateContactImage(final String contactKeyId, final byte[] image);

	List<Contact> searchContacts(final String searchText, final Boolean assigned);

	List<Position> getAssignedPositions(final String contactKeyId);

}
