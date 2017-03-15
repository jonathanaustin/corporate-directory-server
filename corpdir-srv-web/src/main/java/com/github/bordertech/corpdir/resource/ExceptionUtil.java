package com.github.bordertech.corpdir.resource;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Exception helper class.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class ExceptionUtil {

	/**
	 * Private constructor.
	 */
	private ExceptionUtil() {
		// Dont allow instantiation
	}

	/**
	 * @param excp the original exception
	 * @return the web application exception
	 */
	public static RuntimeException convertException(final Exception excp) {
		if (excp instanceof ServiceException) {
			Response.Status status = Response.Status.fromStatusCode(((ServiceException) excp).getCode());
			if (status == null) {
				status = Response.Status.INTERNAL_SERVER_ERROR;
			}
			return new WebApplicationException(excp.getMessage(), excp, status);
		} else if (excp instanceof NotFoundException) {
			return new javax.ws.rs.NotFoundException(excp.getMessage());
		} else {
			return new WebApplicationException(excp.getMessage(), excp, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}
