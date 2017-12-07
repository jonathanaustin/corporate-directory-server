package com.github.bordertech.flux.wc.common.table;

import com.github.bordertech.flux.wc.view.FluxDumbView;
import com.github.bordertech.flux.wc.view.ViewUtil;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.lib.common.WLibButton;
import com.github.bordertech.wcomponents.lib.icons.IconConstants;

/**
 * Panel with a Button that can be used in a table column.
 *
 * @author jonathan
 */
public class DeleteButtonPanel extends WDiv {

	private final WLibButton button = new WLibButton("Delete");

	public DeleteButtonPanel() {
		add(button);
		button.setImageUrl(IconConstants.REMOVE_IMAGE, true);
		button.setRenderAsLink(false);
		button.setToolTip("Delete");
		button.setMessage("Do you want to remove this item?");
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
//			view.dispatchViewEvent(CollectionEventType.REMOVE_ITEM, getBean());
		}
	}

}
