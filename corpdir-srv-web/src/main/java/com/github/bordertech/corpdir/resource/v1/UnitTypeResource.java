package com.github.bordertech.corpdir.resource.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
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
 * Organisation Unit Type REST Resource.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Path(value = "v1/unittypes")
public class UnitTypeResource implements UnitTypeService {

	private final UnitTypeService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public UnitTypeResource(final UnitTypeService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<UnitType>> getUnitTypes(@QueryParam("search") final String search) {
		return impl.getUnitTypes(search);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<UnitType> getUnitType(@PathParam("key") final String typeKeyId) {
		return impl.getUnitType(typeKeyId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<UnitType> createUnitType(final UnitType type) {
		return impl.createUnitType(type);
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<UnitType> updateUnitType(@PathParam("key") final String typeKeyId, final UnitType type) {
		return impl.updateUnitType(typeKeyId, type);
	}

	@DELETE
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public BasicResponse deleteUnitType(@PathParam("key") final String typeKeyId) {
		return impl.deleteUnitType(typeKeyId);
	}

	@GET
	@Path("/{key}/units")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<OrgUnit>> getOrgUnits(@PathParam("key") final String typeKeyId) {
		return impl.getOrgUnits(typeKeyId);
	}

}
