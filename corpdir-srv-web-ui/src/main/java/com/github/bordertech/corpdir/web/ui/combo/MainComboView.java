package com.github.bordertech.corpdir.web.ui.combo;

import com.github.bordertech.corpdir.web.ui.ctrl.MainCardCtrl;
import com.github.bordertech.corpdir.web.ui.event.CardType;
import com.github.bordertech.corpdir.web.ui.view.MainCardView;
import com.github.bordertech.corpdir.web.ui.view.MainToolbarView;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;

/**
 *
 * @author jonathan
 */
public class MainComboView extends DefaultMessageComboView {

	private final MainCardView cardView = new MainCardView();

	public MainComboView() {
		super("wclib/hbs/layout/combo-app-main.hbs");

		MainToolbarView toolbar = new MainToolbarView();

		MainCardCtrl ctrl = new MainCardCtrl();
		ctrl.setCardView(cardView);
		cardView.addHtmlClass("wc-margin-all-lg");

		// Reset
		ResetViewCtrl resetCtrl = new ResetViewCtrl();

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-toolbar", toolbar);
		content.addTaggedComponent("vw-main", cardView);
	}

	public String getCardTitle() {
		CardType card = cardView.getCurrent();
		return card == null ? "" : card.getDesc();
	}

}
