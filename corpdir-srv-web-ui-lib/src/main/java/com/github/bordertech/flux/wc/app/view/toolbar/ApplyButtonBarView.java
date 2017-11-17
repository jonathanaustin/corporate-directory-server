package com.github.bordertech.flux.wc.app.view.toolbar;

import com.github.bordertech.flux.wc.app.common.AppAjaxControl;
import com.github.bordertech.flux.wc.app.view.ToolbarView;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WPanel;

/**
 * Apply button.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ApplyButtonBarView<T> extends AbstractToolbarView<T> implements ToolbarView<T> {

	private final WPanel panel = new WPanel(WPanel.Type.FEATURE);

	private final WButton btnApply = new WButton("Apply");
	private final WAjaxControl ajax = new AppAjaxControl(btnApply);

	public ApplyButtonBarView(final String viewId) {
		super(viewId);
		WContainer content = getContent();
		content.add(panel);
		panel.add(btnApply);
		panel.add(ajax);
		btnApply.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				dispatchViewEvent(ToolbarBaseViewEvent.APPLY, event.getActionObject());
			}
		});
	}

	public void addTarget(final AjaxTarget target) {
		ajax.addTarget(target);
	}

	public WButton getButton() {
		return btnApply;
	}

}
