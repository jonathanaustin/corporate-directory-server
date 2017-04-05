package com.github.bordertech.corpdir.resource.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
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
 * Organisation Unit REST Resource.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Path(value = "v1/orgunits")
public class OrgUnitResource implements OrgUnitService {

	private final OrgUnitService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public OrgUnitResource(final OrgUnitService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<OrgUnit>> getOrgUnits(@QueryParam("search") final String search, @QueryParam("assigned") final Boolean assigned) {
		return impl.getOrgUnits(search, assigned);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<OrgUnit> getOrgUnit(@PathParam("key") final String orgUnitKeyId) {
		return impl.getOrgUnit(orgUnitKeyId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<OrgUnit> createOrgUnit(final OrgUnit orgUnit) {
		return impl.createOrgUnit(orgUnit);
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<OrgUnit> updateOrgUnit(@PathParam("key") final String orgUnitKeyId, final OrgUnit orgUnit) {
		return impl.updateOrgUnit(orgUnitKeyId, orgUnit);
	}

	@DELETE
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public BasicResponse deleteOrgUnit(@PathParam("key") final String orgUnitKeyId) {
		return impl.deleteOrgUnit(orgUnitKeyId);
	}

	@GET
	@Path("/{key}/orgunits")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<OrgUnit>> getSubOrgUnits(@PathParam("key") final String orgUnitKeyId) {
		return impl.getSubOrgUnits(orgUnitKeyId);
	}

	@PUT
	@Path("/{key}/orgunits/{subKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<OrgUnit> addSubOrgUnit(@PathParam("key") final String orgUnitKeyId, @PathParam("subKey") final String subOrgUnitKeyId) {
		return impl.addSubOrgUnit(orgUnitKeyId, subOrgUnitKeyId);
	}

	@DELETE
	@Path("/{key}/orgunits/{subKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<OrgUnit> removeSubOrgUnit(@PathParam("key") final String orgUnitKeyId, @PathParam("subKey") final String subOrgUnitKeyId) {
		return impl.removeSubOrgUnit(orgUnitKeyId, subOrgUnitKeyId);
	}

	@GET
	@Path("/{key}/positions")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<List<Position>> getPositions(@PathParam("key") final String orgUnitKeyId) {
		return impl.getPositions(orgUnitKeyId);
	}

	@PUT
	@Path("/{key}/positions/{positionKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<OrgUnit> addPosition(@PathParam("key") final String orgUnitKeyId, @PathParam("positionKey") final String positionKeyId) {
		return impl.addPosition(orgUnitKeyId, positionKeyId);
	}

	@DELETE
	@Path("/{key}/positions/{positionKey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<OrgUnit> removePosition(@PathParam("key") final String orgUnitKeyId, @PathParam("positionKey") final String positionKeyId) {
		return impl.removePosition(orgUnitKeyId, positionKeyId);
	}

	@GET
	@Path("/{key}/managers")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public DataResponse<Position> getManagerPosition(@PathParam("key") final String orgUnitKeyId) {
		return impl.getManagerPosition(orgUnitKeyId);
	}

}
