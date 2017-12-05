package com.github.bordertech.wcomponents.lib.table.edit;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WConfirmationButton;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.icons.IconConstants;
import com.github.bordertech.wcomponents.util.TableUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Table with row edit actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class RowActionPanel<T> extends WDiv {

	private static final String EDIT_ROWS_ATTR_KEY = "wc-edit-rows";

	private final WButton editButton = new WButton("Edit") {
		@Override
		public boolean isVisible() {
			Object key = TableUtil.getCurrentRowKey();
			return isAllowEdit() && !isEditRow(key);
		}
	};
	private final WButton cancelButton = new WButton("Cancel") {
		@Override
		public boolean isVisible() {
			Object key = TableUtil.getCurrentRowKey();
			return isEditRow(key);
		}
	};
	private final WConfirmationButton deleteButton = new WConfirmationButton("Delete") {
		@Override
		public boolean isVisible() {
			Object key = TableUtil.getCurrentRowKey();
			return isAllowEdit() && !isEditRow(key);
		}
	};

	private final WAjaxControl editAjax = new WAjaxControl(editButton) {
		@Override
		public boolean isVisible() {
			return editButton.isVisible();
		}
	};

	private final WAjaxControl cancelAjax = new WAjaxControl(cancelButton) {
		@Override
		public boolean isVisible() {
			return cancelButton.isVisible();
		}
	};

	public RowActionPanel() {

		// Buttons Panel
		WDiv buttonPanel = new WDiv();
		add(buttonPanel);

		// Edit button
		editButton.setImageUrl(IconConstants.EDIT_IMAGE);
		editButton.setRenderAsLink(true);
		editButton.setToolTip("Edit");
		editButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleEditButtonAction();
			}
		});

		// Cancel Button
		cancelButton.setImageUrl(IconConstants.CANCEL_IMAGE);
		cancelButton.setRenderAsLink(true);
		cancelButton.setToolTip("Cancel");
		cancelButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleCancelButtonAction();
			}
		});

		// Delete Button
		deleteButton.setMessage("Do you want to delete row?");
		deleteButton.setImageUrl(IconConstants.REMOVE_IMAGE);
		deleteButton.setRenderAsLink(true);
		deleteButton.setToolTip("Delete");
		deleteButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleDeleteButtonAction();
			}
		});

		buttonPanel.add(editButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(deleteButton);

		// Ajax Controls
		buttonPanel.add(editAjax);
		buttonPanel.add(cancelAjax);
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			setupColumnAjaxTargets();
			setInitialised(true);
		}
	}

	/**
	 * Setup the row action AJAX targets (ie each column)
	 */
	protected void setupColumnAjaxTargets() {
		WTable table = getTable();
		editAjax.addTargets(getColumnAjaxTargets());
		cancelAjax.addTargets(getColumnAjaxTargets());
		deleteButton.setAjaxTarget(table);
	}

	/**
	 * @param rowKey row key to include in edits.
	 */
	protected void addEditRow(final Object rowKey) {
		WTable table = getTable();
		HashSet<Object> editRows = (HashSet<Object>) table.getAttribute(EDIT_ROWS_ATTR_KEY);
		if (editRows == null) {
			editRows = new HashSet<>();
			table.setAttribute(EDIT_ROWS_ATTR_KEY, editRows);
		}
		editRows.add(rowKey);
	}

	/**
	 * @param rowKey the row key to remove from the edits.
	 */
	protected void removeEditRow(final Object rowKey) {
		WTable table = getTable();
		Set<Object> editRows = (Set<Object>) table.getAttribute(EDIT_ROWS_ATTR_KEY);
		if (editRows != null) {
			editRows.remove(rowKey);
		}
	}

	/**
	 * @param key the row key to test
	 * @return true if row key is being edited
	 */
	protected boolean isEditRow(final Object key) {
		WTable table = getTable();
		Set<Object> editRows = (Set<Object>) table.getAttribute(EDIT_ROWS_ATTR_KEY);
		return editRows != null && editRows.contains(key);
	}

	protected void handleEditButtonAction() {
		Object key = TableUtil.getCurrentRowKey();
		addEditRow(key);
	}

	protected void handleDeleteButtonAction() {
		WTable table = getTable();
		Object key = TableUtil.getCurrentRowKey();
		removeEditRow(key);
		T bean = (T) getBean();
		List<T> beans = (List<T>) table.getBean();
		beans.remove(bean);
		table.handleDataChanged();
	}

	protected void handleCancelButtonAction() {
		Object key = TableUtil.getCurrentRowKey();
		removeEditRow(key);
		for (AjaxTarget target : getColumnAjaxTargets()) {
			target.reset();
		}
	}

	protected List<AjaxTarget> getColumnAjaxTargets() {
		List<AjaxTarget> targets = new ArrayList<>();
		WTable table = getTable();
		for (int i = 0; i < table.getColumnCount(); i++) {
			WComponent renderer = table.getColumn(i).getRenderer();
			if (renderer instanceof AjaxTarget) {
				targets.add((AjaxTarget) renderer);
			}
		}
		return targets;
	}

	protected WTable getTable() {
		return WebUtilities.getAncestorOfClass(WTable.class, this);
	}

	protected boolean isAllowEdit() {
		return getTable().isEditable();
	}

}
