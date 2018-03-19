package com.github.bordertech.flux.wc.common.table;

import com.github.bordertech.flux.wc.view.FluxDumbView;
import com.github.bordertech.flux.wc.view.event.base.SelectBaseEventType;
import com.github.bordertech.flux.wc.view.util.ViewUtil;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.addons.common.IconConstants;
import com.github.bordertech.wcomponents.addons.common.WDiv;
import com.github.bordertech.wcomponents.addons.common.relative.WLibButton;

/**
 * Panel with a Button that can be used in a table column.
 *
 * @author jonathan
 */
public class ViewButtonPanel extends WDiv {

	private final WLibButton button = new WLibButton("View");

	public ViewButtonPanel() {
		add(button);
		button.setImageUrl(IconConstants.VIEW_IMAGE, true);
		button.setRenderAsLink(false);
		button.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				doDefaultAction();
			}
		});
		setBeanProperty(".");
	}

	public WButton getButton() {
		return button;
	}

	protected void doDefaultAction() {
		FluxDumbView view = ViewUtil.findParentView(this);
		if (view != null) {
			view.dispatchViewEvent(SelectBaseEventType.SELECT, getBean());
		}
	}

}
