package com.github.bordertech.corpdir.web.ui.columns;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.wcomponents.addons.table.AbstractTableColumn;
import com.github.bordertech.wcomponents.util.ComparableComparator;

/**
 * Description table column.
 *
 * @author jonathan
 * @param <T> the row bean type
 */
public class DescriptionColumn<T extends ApiKeyIdObject> extends AbstractTableColumn<String, T> {

	public DescriptionColumn() {
		super("Description", new ComparableComparator());
	}

	@Override
	public String getValue(final T bean) {
		return bean.getBusinessKey();
	}

}
