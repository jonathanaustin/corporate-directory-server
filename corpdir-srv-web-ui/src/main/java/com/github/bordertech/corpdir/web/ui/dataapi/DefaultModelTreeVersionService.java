package com.github.bordertech.corpdir.web.ui.dataapi;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicVersionTreeService;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jonathan
 * @param <T> the treeable Object
 * @param <B> the basic versionable tree service type
 */
public class DefaultModelTreeVersionService<T extends ApiTreeable & ApiVersionable, B extends BasicVersionTreeService<T>> extends DefaultModelTreeService<T, B> {

	private static final Log LOG = LogFactory.getLog(DefaultModelTreeVersionService.class);

	public DefaultModelTreeVersionService(final Class<T> apiClass, final Class<? extends B> serviceClass) {
		super(apiClass, serviceClass);
	}

	@Override
	public List<T> getChildren(final T entity) {
		try {
			DataResponse<List<T>> resp = getService().getSubs(entity.getVersionId(), entity.getId());
			return resp.getData();
		} catch (Exception e) {
			LOG.error("Error doing get children with version", e);
			throw new SystemException("Error doing get children with version. " + e.getMessage(), e);
		}
	}

}
