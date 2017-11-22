package com.github.bordertech.flux.wc.app.common.table;

import com.github.bordertech.wcomponents.lib.table.AbstractTableColumn;

/**
 * View button column.
 *
 * @author jonathan
 * @param <T> the row bean type
 */
public class ViewButtonColumn<T> extends AbstractTableColumn<T, T> {

	public ViewButtonColumn() {
		super("Description", new ViewButtonPanel());
	}

	@Override
	public T getValue(final T bean) {
		return bean;
	}

	public ViewButtonPanel getViewButtonPanel() {
		return (ViewButtonPanel) getRenderer();
	}

}
