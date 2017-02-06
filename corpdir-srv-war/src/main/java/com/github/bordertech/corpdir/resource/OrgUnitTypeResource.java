package com.github.bordertech.corpdir.resource;

import com.github.bordertech.corpdir.api.OrgUnitTypeService;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.OrgUnitType;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Organisation Unit Type REST Resource.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Path(value = "orgunittypes")
public class OrgUnitTypeResource implements OrgUnitTypeService {

	private OrgUnitTypeService impl;

	/**
	 * @param impl the service implementation
	 */
	@Inject
	public OrgUnitTypeResource(final OrgUnitTypeService impl) {
		this.impl = impl;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<OrgUnitType> getOrgUnitTypes() {
		try {
			return impl.getOrgUnitTypes();
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@GET
	@Path("/{id}/units")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<OrgUnit> getOrgUnits(@PathParam("id") final Long typeId) {
		try {
			return impl.getOrgUnits(typeId);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@GET
	@Path("/key/{key}/units")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<OrgUnit> getOrgUnits(@PathParam("key") final String typeAltKey) {
		try {
			return impl.getOrgUnits(typeAltKey);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public OrgUnitType getOrgUnitType(@PathParam("id") final Long typeId) {
		try {
			return impl.getOrgUnitType(typeId);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@GET
	@Path("/key/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public OrgUnitType getOrgUnitType(@PathParam("key") final String typeAltKey) {
		try {
			return impl.getOrgUnitType(typeAltKey);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Long createOrgUnitType(final OrgUnitType type) {
		try {
			return impl.createOrgUnitType(type);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public OrgUnitType updateOrgUnitType(@PathParam("id") final Long typeId, final OrgUnitType type) {
		try {
			return impl.updateOrgUnitType(typeId, type);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public void deleteOrgUnitType(@PathParam("id") final Long typeId) {
		try {
			impl.deleteOrgUnitType(typeId);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

}
