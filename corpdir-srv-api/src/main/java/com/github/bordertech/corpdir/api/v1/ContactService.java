package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
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

	DataResponse<List<Contact>> getContacts(final String search, final Boolean assigned);

	DataResponse<Contact> getContact(final String contactKeyId);

	DataResponse<Contact> createContact(final Contact contact);

	DataResponse<Contact> updateContact(final String contactKeyId, final Contact contact);

	BasicResponse deleteContact(final String contactKeyId);

	DataResponse<byte[]> getContactImage(final String contactKeyId);

	BasicResponse deleteContactImage(final String contactKeyId);

	BasicResponse setContactImage(final String contactKeyId, final byte[] image);

	DataResponse<byte[]> getContactThumbnail(final String contactKeyId);

	BasicResponse deleteContactThumbnail(final String contactKeyId);

	BasicResponse setContactThumbnail(final String contactKeyId, final byte[] image);

	DataResponse<List<Channel>> getChannels(final String contactKeyId);

	DataResponse<List<Position>> getPositions(final String contactKeyId);

}
