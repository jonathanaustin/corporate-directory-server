package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Position;
import java.util.List;

/**
 * Contact Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ContactService {

	ServiceResponse<Contact> getContact(final String contactKeyId);

	ServiceResponse<Contact> createContact(final Contact contact);

	ServiceResponse<Contact> updateContact(final String contactKeyId, final Contact contact);

	ServiceBasicResponse deleteContact(final String contactKeyId);

	ServiceResponse<byte[]> getContactImage(final String contactKeyId);

	ServiceResponse<byte[]> getContactThumbnail(final String contactKeyId);

	ServiceBasicResponse deleteContactImage(final String contactKeyId);

	ServiceBasicResponse updateContactImage(final String contactKeyId, final byte[] image);

	ServiceResponse<List<Contact>> searchContacts(final String searchText, final Boolean assigned);

	ServiceResponse<List<Position>> getAssignedPositions(final String contactKeyId);

}
