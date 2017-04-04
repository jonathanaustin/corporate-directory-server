package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
import com.github.bordertech.corpdir.api.v1.model.Channel;
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

	ServiceResponse<List<Contact>> getContacts(final String search, final Boolean assigned);

	ServiceResponse<Contact> getContact(final String contactKeyId);

	ServiceResponse<Contact> createContact(final Contact contact);

	ServiceResponse<Contact> updateContact(final String contactKeyId, final Contact contact);

	ServiceBasicResponse deleteContact(final String contactKeyId);

	ServiceResponse<byte[]> getContactImage(final String contactKeyId);

	ServiceBasicResponse deleteContactImage(final String contactKeyId);

	ServiceBasicResponse setContactImage(final String contactKeyId, final byte[] image);

	ServiceResponse<byte[]> getContactThumbnail(final String contactKeyId);

	ServiceBasicResponse deleteContactThumbnail(final String contactKeyId);

	ServiceBasicResponse setContactThumbnail(final String contactKeyId, final byte[] image);

	ServiceResponse<List<Channel>> getChannels(final String contactKeyId);

	ServiceResponse<List<Position>> getPositions(final String contactKeyId);

}
