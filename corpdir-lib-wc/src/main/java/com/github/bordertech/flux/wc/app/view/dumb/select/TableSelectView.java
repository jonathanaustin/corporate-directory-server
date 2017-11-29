package com.github.bordertech.flux.wc.app.view.dumb.select;

import com.github.bordertech.flux.wc.app.common.table.TextBeanColumn;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.lib.table.TableBeanModel;
import com.github.bordertech.wcomponents.lib.table.TableColumn;
import java.util.Arrays;
import java.util.List;

/**
 * Table with view button.
 *
 * @author Jonathan Austin
 * @param <T> the item type
 * @since 1.0.0
 */
public class TableSelectView<T> extends AbstractListSingleSelectView<T> {

	private final WTable table = new WTable();

	public TableSelectView(final String viewId) {
		this(viewId, new TextBeanColumn<T>());
	}

	public TableSelectView(final String viewId, final TableColumn<?, T>... columns) {
		this(viewId, Arrays.asList(columns));
	}

	public TableSelectView(final String viewId, final List<TableColumn<?, T>> columns) {
		super(viewId);
		table.setBeanProperty(".");
		getContent().add(table);
		TableBeanModel model = new TableBeanModel<>(columns);
		TableBeanModel.configTable(table);
		table.setTableModel(model);
		// FIXME JA - Not sure why select mode as is using button to "select"
		table.setSelectMode(WTable.SelectMode.SINGLE);
	}

	public final WTable getTable() {
		return table;
	}

}
