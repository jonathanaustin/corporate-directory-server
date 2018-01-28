package com.github.bordertech.corpdir.web.api.resource.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.SystemCtrlService;
import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
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
 * System Control REST Resource.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Path(value = "v1/ctrl")
public class SystemCtrlResource implements SystemCtrlService {

	private final SystemCtrlService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public SystemCtrlResource(final SystemCtrlService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<SystemCtrl>> search(@QueryParam("search") final String search) {
		return impl.search(search);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<SystemCtrl> retrieve(@PathParam("key") final String keyId) {
		// TODO Only supports one key anyway
		return impl.retrieve(keyId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<SystemCtrl> create(final SystemCtrl type) {
		return impl.create(type);
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<SystemCtrl> update(@PathParam("key") final String keyId, final SystemCtrl type) {
		return impl.update(keyId, type);
	}

	@DELETE
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public BasicResponse delete(@PathParam("key") final String keyId) {
		throw new UnsupportedOperationException("Delete not supported.");
	}

	@GET
	@Path("/current")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Long> getCurrentVersion() {
		return impl.getCurrentVersion();
	}

	@PUT
	@Path("/current/{vers}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Long> setCurrentVersion(@PathParam("vers") final Long versionId) {
		return impl.setCurrentVersion(versionId);
	}

}
