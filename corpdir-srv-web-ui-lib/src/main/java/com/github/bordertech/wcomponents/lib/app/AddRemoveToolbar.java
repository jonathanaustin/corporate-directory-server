package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.view.FormUpdateable;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddRemoveToolbar<T> extends DefaultView<T> implements FormUpdateable {

	private final WButton btnAdd = new WButton("Add");
	private final WButton btnRemove = new WButton("Remove");

	private final WAjaxControl ajaxAdd = new WAjaxControl(btnAdd);
	private final WAjaxControl ajaxRemove = new WAjaxControl(btnRemove);

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}

	};

	public AddRemoveToolbar() {

		WContainer content = getContent();
		content.add(btnAdd);
		content.add(btnRemove);

		// Actions
		btnAdd.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatchEvent(ToolbarEventType.ADD);
			}
		});
		btnRemove.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatchEvent(ToolbarEventType.DELETE);
			}
		});

		// AJAX
		content.add(ajaxPanel);
		ajaxPanel.add(ajaxAdd);
		ajaxPanel.add(ajaxRemove);
		ajaxAdd.addTarget(this);
		ajaxRemove.addTarget(this);

		// Defaults
		btnRemove.setVisible(false);
	}

	public void showRemoveButton(final boolean show) {
		btnRemove.setVisible(show);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventTarget(target, eventType);
		ajaxAdd.addTarget(target);
		ajaxRemove.addTarget(target);
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		setContentVisible(!readonly);
	}

}
