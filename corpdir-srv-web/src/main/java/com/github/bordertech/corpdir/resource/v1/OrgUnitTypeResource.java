package com.github.bordertech.corpdir.resource.v1;

import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
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
public class OrgUnitTypeResource implements OrgUnitService {

	private final OrgUnitService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public OrgUnitTypeResource(final OrgUnitService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<List<OrgUnit>> getOrgUnits(@QueryParam("search") final String search, @QueryParam("topOnly") final Boolean topOnly) {
		return impl.getOrgUnits(search, topOnly);
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<OrgUnit> getOrgUnit(@PathParam("key") final String orgUnitKeyId) {
		return impl.getOrgUnit(orgUnitKeyId);
	}

	@GET
	@Path("/{key}/orgunits")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<List<OrgUnit>> getSubOrgUnits(@PathParam("key") final String orgUnitKeyId) {
		return impl.getSubOrgUnits(orgUnitKeyId);
	}

	@GET
	@Path("/{key}/positions")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<List<Position>> getAssignedPositions(@PathParam("key") final String orgUnitKeyId) {
		return impl.getAssignedPositions(orgUnitKeyId);
	}

	@GET
	@Path("/{key}/managers")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<Position> getOrgUnitManager(@PathParam("key") final String orgUnitKeyId) {
		return impl.getOrgUnitManager(orgUnitKeyId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<OrgUnit> createOrgUnit(final OrgUnit orgUnit) {
		return impl.createOrgUnit(orgUnit);
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<OrgUnit> updateOrgUnit(@PathParam("key") final String orgUnitKeyId, final OrgUnit orgUnit) {
		return impl.updateOrgUnit(orgUnitKeyId, orgUnit);
	}

	@DELETE
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceBasicResponse deleteOrgUnit(final String orgUnitKeyId) {
		return impl.deleteOrgUnit(orgUnitKeyId);
	}

	@Override
	public ServiceBasicResponse assignOrgUnitToOrgUnit(final String orgUnitKeyId, final String parentOrgUnitKeyId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ServiceBasicResponse assignPosition(final String orgUnitKeyId, final String positionKeyId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ServiceBasicResponse unassignPosition(final String orgUnitKeyId, final String positionKeyId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
