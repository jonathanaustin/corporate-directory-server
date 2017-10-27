package com.github.bordertech.wcomponents.lib.app.view.table;

import com.github.bordertech.wcomponents.lib.table.AbstractTableColumn;

/**
 * Text bean column.
 *
 * @author jonathan
 * @param <T> the row bean type
 */
public class TextBeanColumn<T> extends AbstractTableColumn<String, T> {

	public TextBeanColumn() {
		super("Description");
	}

	@Override
	public String getValue(final T bean) {
		return bean.toString();
	}

}
