package com.github.bordertech.flux.wc.view.dumb.toolbar;

import com.github.bordertech.flux.wc.common.FluxAjaxControl;
import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.dumb.form.FormUpdateable;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.addons.common.IconConstants;
import com.github.bordertech.wcomponents.addons.common.WDiv;
import com.github.bordertech.wcomponents.addons.common.relative.WLibButton;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddDeleteButtonBarView<T> extends DefaultDumbView<T> implements FormUpdateable {

	private final WLibButton btnAdd = new WLibButton("Add");
	private final WLibButton btnDelete = new WLibButton("Remove");

	private final FluxAjaxControl ajaxAdd = new FluxAjaxControl(btnAdd);
	private final FluxAjaxControl ajaxDelete = new FluxAjaxControl(btnDelete);

	private final WDiv ajaxPanel = new WDiv() {
		@Override
		public boolean isHidden() {
			return true;
		}

	};

	public AddDeleteButtonBarView(final String viewId) {
		super(viewId);

		WContainer content = getContent();
		content.add(btnAdd);
		content.add(btnDelete);

		// Add details
		btnAdd.setImageUrl(IconConstants.EDIT_IMAGE, true);
		btnAdd.setRenderAsLink(false);
		btnAdd.setToolTip("Add");

		// Delete details
		btnDelete.setImageUrl(IconConstants.REMOVE_IMAGE, true);
		btnDelete.setRenderAsLink(false);
		btnDelete.setMessage("Do you want to remove this item?");
		btnDelete.setToolTip("Delete");

		// Actions
		btnAdd.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatchViewEvent(ToolbarBaseEventType.ADD);
			}
		});
		btnDelete.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatchViewEvent(ToolbarBaseEventType.DELETE);
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

		registerEventAjaxControl(ToolbarBaseEventType.ADD, ajaxAdd);
		registerEventAjaxControl(ToolbarBaseEventType.DELETE, ajaxDelete);
	}

	public void showRemoveButton(final boolean show) {
		btnDelete.setVisible(show);
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		setContentVisible(!readonly);
	}

}
