package com.github.bordertech.corpdir.web.ui.flux.impl;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicTreeService;
import com.github.bordertech.corpdir.web.ui.flux.CorpCrudTreeDataApi;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CRUD Tree API calling CorpDir Services.
 *
 * @param <T> the CorpDir Treeable API object
 * @param <S> the CorpDir tree service type
 *
 * @author jonathan
 */
public class DefaultCorpCrudTreeDataApi<T extends ApiTreeable, S extends BasicTreeService<T>> extends DefaultCorpCrudDataApi<T, S> implements CorpCrudTreeDataApi<T, S> {

	private static final Log LOG = LogFactory.getLog(DefaultCorpCrudTreeDataApi.class);

	public DefaultCorpCrudTreeDataApi(final Class<T> apiClass, final S service) {
		super(apiClass, service);
	}

	@Override
	public List<T> search(final String criteria) {
		try {
			DataResponse<List<T>> resp = getService().search(criteria);
			return resp.getData();
		} catch (Exception e) {
			LOG.error("Error searching items", e);
			throw new SystemException("Error searching items. " + e.getMessage(), e);
		}
	}

	@Override
	public List<T> getChildren(final T entity) {
		try {
			DataResponse<List<T>> resp = getService().getSubs(entity.getId());
			return resp.getData();
		} catch (Exception e) {
			LOG.error("Error doing get children", e);
			throw new SystemException("Error doing get children. " + e.getMessage(), e);
		}
	}

	@Override
	public boolean hasChildren(final T entity) {
		return !entity.getSubIds().isEmpty();
	}

	@Override
	public String getItemLabel(final T entity) {
		return entity.getDescription() + " [" + entity.getBusinessKey() + "]";
	}

	@Override
	public String getItemId(final T entity) {
		return entity.getId();
	}

	@Override
	public List<T> getRootItems() {
		try {
			DataResponse<List<T>> resp = getService().getRootItems();
			return resp.getData();
		} catch (Exception e) {
			LOG.error("Error retrieving root items", e);
			throw new SystemException("Error retrieveing root items. " + e.getMessage(), e);
		}
	}

	@Override
	public T addChild(final T parent, final T child) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public T removeChild(final T parent, final T child) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
