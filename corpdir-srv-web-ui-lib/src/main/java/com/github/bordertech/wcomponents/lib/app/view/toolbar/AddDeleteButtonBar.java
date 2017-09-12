package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.view.form.FormUpdateable;
import com.github.bordertech.wcomponents.lib.div.WDiv;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.icons.IconConstants;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddDeleteButtonBar<T> extends DefaultView<T> implements FormUpdateable {

	private final WButton btnAdd = new WButton("Add");
	private final WButton btnDelete = new WButton("Remove");

	private final WAjaxControl ajaxAdd = new AppAjaxControl(btnAdd);
	private final WAjaxControl ajaxDelete = new AppAjaxControl(btnDelete);

	private final WDiv ajaxPanel = new WDiv() {
		@Override
		public boolean isHidden() {
			return true;
		}

	};

	public AddDeleteButtonBar() {

		WContainer content = getContent();
		content.add(btnAdd);
		content.add(btnDelete);

		// Add details
		btnAdd.setImageUrl(IconConstants.EDIT_IMAGE);
		btnAdd.setRenderAsLink(false);
		btnAdd.setToolTip("Add");

		// Delete details
		btnDelete.setImageUrl(IconConstants.REMOVE_IMAGE);
		btnDelete.setRenderAsLink(false);
		btnDelete.setMessage("Do you want to remove this item?");
		btnDelete.setToolTip("Delete");

		// Actions
		btnAdd.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatchEvent(ToolbarEventType.ADD);
			}
		});
		btnDelete.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatchEvent(ToolbarEventType.DELETE);
			}
		});

		// AJAX
		content.add(ajaxPanel);
		ajaxPanel.add(ajaxAdd);
		ajaxPanel.add(ajaxDelete);
		ajaxAdd.addTarget(this);
		ajaxDelete.addTarget(this);

		// Defaults
		btnDelete.setVisible(false);
	}

	public void showRemoveButton(final boolean show) {
		btnDelete.setVisible(show);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventTarget(target, eventType);
		ajaxAdd.addTarget(target);
		ajaxDelete.addTarget(target);
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		setContentVisible(!readonly);
	}

}
