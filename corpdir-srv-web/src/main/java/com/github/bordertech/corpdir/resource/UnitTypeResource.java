package com.github.bordertech.corpdir.resource;

import com.github.bordertech.corpdir.api.UnitTypeService;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.UnitType;
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
@Path(value = "unittypes")
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
	public List<UnitType> getUnitTypes() {
		try {
			return impl.getUnitTypes();
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
	public UnitType getUnitType(@PathParam("id") final Long typeId) {
		try {
			return impl.getUnitType(typeId);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@GET
	@Path("/key/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public UnitType getUnitType(@PathParam("key") final String typeAltKey) {
		try {
			return impl.getUnitType(typeAltKey);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Long createUnitType(final UnitType type) {
		try {
			return impl.createUnitType(type);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public UnitType updateUnitType(@PathParam("id") final Long typeId, final UnitType type) {
		try {
			return impl.updateUnitType(typeId, type);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public void deleteUnitType(@PathParam("id") final Long typeId) {
		try {
			impl.deleteUnitType(typeId);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

}
