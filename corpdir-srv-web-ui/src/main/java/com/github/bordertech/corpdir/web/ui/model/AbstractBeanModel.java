package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.wcomponents.lib.app.model.ActionModel;

/**
 * Bean bound model (ie no service call).
 *
 * @author jonathan
 */
public abstract class AbstractBeanModel<T> implements ActionModel<T> {

	@Override
	public T create(final T entity) {
		return entity;
	}

	@Override
	public T update(final T entity) {
		return entity;
	}

	@Override
	public void delete(final T entity) {
	}

	@Override
	public T refresh(final T entity) {
		return entity;
	}

}
