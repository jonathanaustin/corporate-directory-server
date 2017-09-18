package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.lib.app.view.table.TextBeanColumn;
import com.github.bordertech.wcomponents.lib.table.TableBeanModel;
import com.github.bordertech.wcomponents.lib.table.TableColumn;
import java.util.Arrays;
import java.util.List;

/**
 * Basic table view.
 *
 * @author Jonathan Austin
 * @param <T> the item type
 * @since 1.0.0
 */
public class TableBasicView<T> extends AbstractListView<T> {

	private final WTable table = new WTable();

	public TableBasicView() {
		this(new TextBeanColumn<T>());
	}

	public TableBasicView(final TableColumn<?, T>... columns) {
		this(Arrays.asList(columns));
	}

	public TableBasicView(final List<TableColumn<?, T>> columns) {
		table.setBeanProperty(".");
		getContent().add(table);
		TableBeanModel model = new TableBeanModel<>(columns);
		TableBeanModel.configTable(table);
		table.setTableModel(model);
	}

	public final WTable getTable() {
		return table;
	}

}
