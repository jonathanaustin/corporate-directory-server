package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.common.EntityLink;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.config.DataApiType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiIdPanel;
import com.github.bordertech.flux.wc.view.smart.input.PollingDropdownOptionsView;
import com.github.bordertech.wcomponents.WLabel;

/**
 * System Control detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SystemCtrlPanel extends BasicApiIdPanel<SystemCtrl> {

	/**
	 * Construct basic system control panel.
	 *
	 * @param viewId the viewId
	 */
	public SystemCtrlPanel(final String viewId) {
		super(viewId);

		// Current Version
		PollingDropdownOptionsView<String, VersionCtrl> drpVersion = new PollingDropdownOptionsView("VERS");
		drpVersion.setUseReadonlyContainer(true);
		drpVersion.getReadonlyContainer().add(new EntityLink(CardType.VERSION_CTRL));
		WLabel lbl = new WLabel("Current version", drpVersion.getSelectInput());
		getFormLayout().addField(lbl, drpVersion);
		drpVersion.setIncludeNullOption(true);
		drpVersion.setCodeProperty("id");
		drpVersion.getOptionsView().setBeanProperty("currentVersionId");
		drpVersion.setStoreKey(DataApiType.VERSION_CTRL.getSearchStoreKey());
	}

}
