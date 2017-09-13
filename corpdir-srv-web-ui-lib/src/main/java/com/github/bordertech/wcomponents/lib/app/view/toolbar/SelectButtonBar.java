package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.event.ModelEventType;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;

/**
 * Select button.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SelectButtonBar<T> extends AbstractToolbar<T> implements ToolbarView<T> {

	private final WPanel panel = new WPanel(WPanel.Type.FEATURE);

	private final WButton btnSelect = new WButton("Add");
	private final WAjaxControl ajax = new AppAjaxControl(btnSelect);

	public SelectButtonBar() {
		WContainer content = getContent();
		content.add(panel);
		panel.add(btnSelect);
		panel.add(ajax);
		btnSelect.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				dispatchEvent(ModelEventType.SELECTED, event.getActionObject());
			}
		});
	}

	public void addTarget(final AjaxTarget target) {
		ajax.addTarget(target);
	}

	public WButton getButton() {
		return btnSelect;
	}

}
