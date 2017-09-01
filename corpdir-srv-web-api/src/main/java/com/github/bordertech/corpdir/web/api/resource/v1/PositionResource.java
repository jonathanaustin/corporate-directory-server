package com.github.bordertech.corpdir.web.api.resource.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
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
 * Position REST Resource.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Path(value = "v1/positions")
public class PositionResource implements PositionService {

	private final PositionService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public PositionResource(final PositionService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<Position>> search(@QueryParam("search") final String search) {
		return impl.search(search);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Position> retrieve(@PathParam("key") final String keyId) {
		return impl.retrieve(keyId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Position> create(final Position position) {
		return impl.create(position);
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Position> update(@PathParam("key") final String keyId, final Position position) {
		return impl.update(keyId, position);
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
	public DataResponse<List<Position>> getSubs(@PathParam("key") final String keyId) {
		return impl.getSubs(keyId);
	}

	@PUT
	@Path("/{key}/positions/{subKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Position> addSub(@PathParam("key") final String keyId, @PathParam("subKey") final String subKeyId) {
		return impl.addSub(keyId, subKeyId);
	}

	@DELETE
	@Path("/{key}/positions/{subKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Position> removeSub(@PathParam("key") final String keyId, @PathParam("subKey") final String subKeyId) {
		return impl.removeSub(keyId, subKeyId);
	}

	@GET
	@Path("/{key}/contacts")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<Contact>> getContacts(@PathParam("key") final String keyId) {
		return impl.getContacts(keyId);
	}

	@PUT
	@Path("/{key}/contacts/{subKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Position> addContact(@PathParam("key") final String keyId, @PathParam("subKey") final String contactKeyId) {
		return impl.addContact(keyId, contactKeyId);
	}

	@DELETE
	@Path("/{key}/contacts/{subKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Position> removeContact(@PathParam("key") final String keyId, @PathParam("subKey") final String contactKeyId) {
		return impl.removeContact(keyId, contactKeyId);
	}

	@GET
	@Path("/{key}/manages")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<OrgUnit>> getManages(@PathParam("key") final String keyId) {
		return impl.getManages(keyId);
	}

	@Override
	public DataResponse<List<OrgUnit>> getManages(Integer versionId, String keyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<List<Position>> getSubs(Integer versionId, String keyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<Position> addSub(Integer versionId, String keyId, String subKeyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<Position> removeSub(Integer versionId, String keyId, String subKeyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<List<Position>> search(Integer versionId, String search) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<Position> retrieve(Integer versionId, String keyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<List<Contact>> getContacts(Integer versionId, String keyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<Position> addContact(Integer versionId, String keyId, String contactKeyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public DataResponse<Position> removeContact(Integer versionId, String keyId, String contactKeyId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
