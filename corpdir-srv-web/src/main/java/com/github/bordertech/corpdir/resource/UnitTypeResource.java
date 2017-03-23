package com.github.bordertech.corpdir.resource;

import com.github.bordertech.corpdir.api.UnitTypeService;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.UnitType;
import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
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
	public ServiceResponse<List<UnitType>> getUnitTypes() {
		try {
			return impl.getUnitTypes();
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@GET
	@Path("/{key}/units")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<List<OrgUnit>> getOrgUnits(@PathParam("key") final String typeKeyId) {
		try {
			return impl.getOrgUnits(typeKeyId);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<UnitType> getUnitType(@PathParam("key") final String typeKeyId) {
		try {
			return impl.getUnitType(typeKeyId);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<String> createUnitType(final UnitType type) {
		try {
			return impl.createUnitType(type);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@PUT
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceResponse<UnitType> updateUnitType(@PathParam("key") final String typeKeyId, final UnitType type) {
		try {
			return impl.updateUnitType(typeKeyId, type);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

	@DELETE
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public ServiceBasicResponse deleteUnitType(@PathParam("key") final String typeKeyId) {
		try {
			return impl.deleteUnitType(typeKeyId);
		} catch (Exception e) {
			throw ExceptionUtil.convertException(e);
		}
	}

}
