package com.github.bordertech.wcomponents.lib.app.view.table;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.app.event.SelectEventType;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.lib.icons.IconConstants;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.util.ViewUtil;

/**
 * Panel with a Button that can be used in a table column.
 *
 * @author jonathan
 */
public class ViewButtonPanel extends WDiv {

	private final WButton button = new WButton("View");

	public ViewButtonPanel() {
		add(button);
		button.setImageUrl(IconConstants.VIEW_IMAGE);
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
		View view = ViewUtil.findParentView(this);
		if (view != null) {
			view.dispatchEvent(SelectEventType.SELECT, getBean());
		}
	}

}
