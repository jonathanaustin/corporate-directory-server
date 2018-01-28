package com.github.bordertech.corpdir.web.api.resource.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
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
 * Version Control REST Resource.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Path(value = "v1/version")
public class VersionCtrlResource implements VersionCtrlService {

	private final VersionCtrlService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public VersionCtrlResource(final VersionCtrlService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<VersionCtrl>> search(@QueryParam("search") final String search) {
		return impl.search(search);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<VersionCtrl> retrieve(@PathParam("key") final String keyId) {
		return impl.retrieve(keyId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<VersionCtrl> create(final VersionCtrl type) {
		return impl.create(type);
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<VersionCtrl> update(@PathParam("key") final String keyId, final VersionCtrl type) {
		return impl.update(keyId, type);
	}

	@DELETE
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public BasicResponse delete(@PathParam("key") final String keyId) {
		return impl.delete(keyId);
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Long> createVersion(@QueryParam("description") final String description) {
		return impl.createVersion(description);
	}

	@PUT
	@Path("/copy/{fromVers}/to/{toVers}")
	@Override
	public BasicResponse copyVersion(@PathParam("fromVers") final Long fromId, @PathParam("toVers") final Long toId, @QueryParam("system") final boolean copySystem, @QueryParam("custom") final boolean copyCustom) {
		return impl.copyVersion(fromId, toId, copySystem, copyCustom);
	}

}
