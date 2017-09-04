package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicVersionService;
import com.github.bordertech.corpdir.api.v1.func.PositionFunctions;
import com.github.bordertech.corpdir.api.v1.model.Contact;

/**
 * Contact Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ContactService extends BasicVersionService<Contact>, PositionFunctions<Contact> {

	DataResponse<byte[]> getImage(final String keyId);

	BasicResponse deleteImage(final String keyId);

	BasicResponse setImage(final String keyId, final byte[] image);

	DataResponse<byte[]> getThumbnail(final String keyId);

	BasicResponse deleteThumbnail(final String keyId);

	BasicResponse setThumbnail(final String keyId, final byte[] image);

}
