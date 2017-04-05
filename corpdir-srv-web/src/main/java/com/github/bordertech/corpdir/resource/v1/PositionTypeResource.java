package com.github.bordertech.corpdir.resource.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
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
 * Position Type REST Resource.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Path(value = "v1/positiontypes")
public class PositionTypeResource implements PositionTypeService {

	private final PositionTypeService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public PositionTypeResource(final PositionTypeService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<PositionType>> getPositionTypes(@QueryParam("search") final String search) {
		return impl.getPositionTypes(search);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<PositionType> getPositionType(@PathParam("key") final String typeKeyId) {
		return impl.getPositionType(typeKeyId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<PositionType> createPositionType(final PositionType type) {
		return impl.createPositionType(type);
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<PositionType> updatePositionType(@PathParam("key") final String typeKeyId, final PositionType type) {
		return impl.updatePositionType(typeKeyId, type);
	}

	@DELETE
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public BasicResponse deletePositionType(@PathParam("key") final String typeKeyId) {
		return impl.deletePositionType(typeKeyId);
	}

	@GET
	@Path("/{key}/positions")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<Position>> getPositions(@PathParam("key") final String typeKeyId) {
		return impl.getPositions(typeKeyId);
	}

}
