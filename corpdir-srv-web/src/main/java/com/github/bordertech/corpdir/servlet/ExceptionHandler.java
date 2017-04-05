package com.github.bordertech.corpdir.servlet;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.api.response.ErrorDetail;
import com.github.bordertech.corpdir.api.response.ErrorResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Return exceptions with a standard error response.
 *
 * @author jonathan
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(final Throwable exception) {
		ErrorDetail detail = createErrorDetail(exception);
		ErrorResponse resp = new ErrorResponse(detail);
		return Response.status(detail.getStatus()).
				entity(resp).
				type("application/json").
				build();
	}

	/**
	 * @param excp the original exception
	 * @return the error details
	 */
	protected ErrorDetail createErrorDetail(final Throwable excp) {
		// Default to Internal Error
		int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
		if (excp instanceof ServiceException) {
			Response.Status respStatus = Response.Status.fromStatusCode(((ServiceException) excp).getCode());
			if (respStatus != null) {
				status = respStatus.getStatusCode();
			}
		} else if (excp instanceof NotFoundException) {
			status = Response.Status.NOT_FOUND.getStatusCode();
		}
		return new ErrorDetail(status, excp.getMessage());
	}

}
