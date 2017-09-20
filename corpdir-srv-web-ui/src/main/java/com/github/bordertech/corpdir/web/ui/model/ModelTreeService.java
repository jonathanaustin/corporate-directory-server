package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicTreeService;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.app.model.TreeModel;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jonathan
 * @param <T> the treeable Object
 */
public class ModelTreeService<T extends ApiTreeable> implements TreeModel<String, T> {

	private static final Log LOG = LogFactory.getLog(ModelTreeService.class);

	private final BasicTreeService<T> service;

	public ModelTreeService(final Class<? extends BasicTreeService<T>> serviceClass) {
		this.service = LocatorUtil.getService(serviceClass);
	}

	@Override
	public List<T> retrieveCollection(final String criteria) {
		try {
			DataResponse<List<T>> resp = service.search(criteria);
			return resp.getData();
		} catch (Exception e) {
			LOG.error("Error doing get children", e);
			throw new SystemException("Error doing get children. " + e.getMessage(), e);
		}
	}

	@Override
	public List<T> getChildren(final T entity) {
		try {
			DataResponse<List<T>> resp = service.getSubs(entity.getId());
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
		return entity.getDescription();
	}

}
