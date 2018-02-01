package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicVersionTreeService;
import com.github.bordertech.corpdir.web.ui.dataapi.CorpCrudTreeVersionApi;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jonathan
 * @param <T> the treeable API type
 * @param <S> the versionable tree service type
 */
public class DefaultCorpCrudTreeVersionApi<T extends ApiTreeable & ApiVersionable, S extends BasicVersionTreeService<T>> extends DefaultCorpCrudTreeApi<T, S> implements CorpCrudTreeVersionApi<T, S> {

	private static final Log LOG = LogFactory.getLog(DefaultCorpCrudTreeVersionApi.class);

	public DefaultCorpCrudTreeVersionApi(final Class<T> apiClass, final S service) {
		super(apiClass, service);
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
