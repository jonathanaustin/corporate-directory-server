package com.github.bordertech.flux.wc.view.smart.table;

import com.github.bordertech.flux.crud.actioncreator.CrudActionCreator;
import com.github.bordertech.flux.crud.view.consumer.CrudActionCreatorConsumer;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.dumb.table.TableInlineEditingView;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.wcomponents.addons.table.TableColumn;
import com.github.bordertech.wcomponents.addons.table.edit.RowActionable;
import com.github.bordertech.wcomponents.addons.table.edit.RowMode;
import java.util.List;

/**
 * Smart Table view with row edit actions and config for action creator.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TableInlineEditingSmartView<T> extends DefaultSmartView<List<T>> implements CrudActionCreatorConsumer<T>, RowActionable {

	private final TableInlineEditingView tableView;

	/**
	 * @param viewId the view id
	 * @param columns the table columns
	 */
	public TableInlineEditingSmartView(final String viewId, final List<TableColumn<?, T>> columns) {
		this(viewId, columns, true);
	}

	/**
	 * @param viewId the view id
	 * @param columns the table columns
	 * @param addActionColumn true if add action column or false if action column included in columns
	 */
	public TableInlineEditingSmartView(final String viewId, final List<TableColumn<?, T>> columns, final boolean addActionColumn) {
		super(viewId);
		tableView = new TableInlineEditingView<>(viewId + "_tbl", columns, addActionColumn);
		// Use the bean on the smart view
		tableView.setBeanProperty(".");
		tableView.setSearchAncestors(true);
		addComponentToTemplate(TemplateConstants.TAG_VW_CONTENT, tableView);
	}

	public final TableInlineEditingView<T> getTableView() {
		return tableView;
	}

	@Override
	public void setActionCreatorKey(final String actionCreatorKey) {
		getOrCreateComponentModel().actionCreatorKey = actionCreatorKey;
	}

	@Override
	public String getActionCreatorKey() {
		return getComponentModel().actionCreatorKey;
	}

	@Override
	public CrudActionCreator<T> getActionCreatorByKey() {
		return (CrudActionCreator<T>) getDispatcher().getActionCreator(getActionCreatorKey());
	}

	@Override
	public RowMode getCurrentRowMode() {
		return tableView.getCurrentRowMode();
	}

	@Override
	public RowMode getRowMode(final Object rowKey) {
		return tableView.getRowMode(rowKey);
	}

	@Override
	public void addRowModeKey(final Object rowKey, final RowMode mode) {
		tableView.addRowModeKey(rowKey, mode);
	}

	@Override
	public void removeRowModeKey(final Object rowKey) {
		tableView.removeRowModeKey(rowKey);
	}

	@Override
	public RowMode getRowModeKey(final Object rowKey) {
		return tableView.getRowModeKey(rowKey);
	}

	@Override
	public void clearRowModeKeys() {
		tableView.clearRowModeKeys();
	}

	@Override
	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (isEvent(event, ToolbarBaseEventType.ADD)) {
			handleAddEvent();
		}
	}

	protected void handleAddEvent() {
		T bean = getActionCreatorByKey().createInstance();
		tableView.addItem(bean);
	}

	@Override
	protected TableInlineSmartViewModel newComponentModel() {
		return new TableInlineSmartViewModel();
	}

	@Override
	protected TableInlineSmartViewModel getComponentModel() {
		return (TableInlineSmartViewModel) super.getComponentModel();
	}

	@Override
	protected TableInlineSmartViewModel getOrCreateComponentModel() {
		return (TableInlineSmartViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class TableInlineSmartViewModel extends SmartViewModel {

		private String actionCreatorKey;
	}

}
