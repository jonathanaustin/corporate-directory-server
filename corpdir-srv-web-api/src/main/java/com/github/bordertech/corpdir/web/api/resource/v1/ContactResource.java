package com.github.bordertech.corpdir.web.api.resource.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Position;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Contact REST Resource.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Path(value = "v1/contacts")
public class ContactResource implements ContactService {

	private final ContactService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public ContactResource(final ContactService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<Contact>> search(@QueryParam("search") final String search) {
		return impl.search(search);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Contact> retrieve(@PathParam("key") final String keyId) {
		return impl.retrieve(keyId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Contact> create(final Contact contact) {
		return impl.create(contact);
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Contact> update(@PathParam("key") final String keyId, final Contact contact) {
		return impl.update(keyId, contact);
	}

	@DELETE
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public BasicResponse delete(@PathParam("key") final String keyId) {
		return impl.delete(keyId);
	}

	@GET
	@Path("/{key}/positions")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<Position>> getPositions(@PathParam("key") final String keyId) {
		return impl.getPositions(keyId);
	}

	@PUT
	@Path("/{key}/positions/{subKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Contact> addPosition(@PathParam("key") final String keyId, @PathParam("subKey") final String positionKeyId) {
		return impl.addPosition(keyId, positionKeyId);
	}

	@DELETE
	@Path("/{key}/positions/{subKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Contact> removePosition(@PathParam("key") final String keyId, @PathParam("subKey") final String positionKeyId) {
		return impl.removePosition(keyId, positionKeyId);
	}

	@Override
	public DataResponse<byte[]> getImage(final String keyId) {
		return impl.getImage(keyId);
	}

	@Override
	public BasicResponse deleteImage(final String keyId) {
		return impl.deleteImage(keyId);
	}

	@Override
	public BasicResponse setImage(final String keyId, final byte[] image) {
		return impl.setImage(keyId, image);
	}

	@Override
	public DataResponse<byte[]> getThumbnail(final String keyId) {
		return impl.getThumbnail(keyId);
	}

	@Override
	public BasicResponse deleteThumbnail(final String keyId) {
		return impl.deleteThumbnail(keyId);
	}

	@Override
	public BasicResponse setThumbnail(final String keyId, final byte[] image) {
		return impl.setThumbnail(keyId, image);
	}

	@Override
	public DataResponse<List<Contact>> search(Integer versionId, String search) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<Contact> retrieve(Integer versionId, String keyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<List<Position>> getPositions(Integer versionId, String keyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<Contact> addPosition(Integer versionId, String keyId, String positionKeyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<Contact> removePosition(Integer versionId, String keyId, String positionKeyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
