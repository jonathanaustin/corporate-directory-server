package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingDropdownOptionsView;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingMultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.List;
import jersey.repackaged.com.google.common.base.Objects;

/**
 * Location detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class LocationPanel extends BasicApiKeyPanel<Location> {

	private final PollingDropdownOptionsView<String, Location> drpParent = new PollingDropdownOptionsView<>("PAR");
	private final PollingMultiSelectPairOptionsView<String, Location> multiSub = new PollingMultiSelectPairOptionsView<>("SUB");

	/**
	 * Construct basic detail panel. \
	 */
	public LocationPanel() {
		getFormPanel().add(new WHeading(HeadingLevel.H2, "Address"));
		AddressPanel addressPanel = new AddressPanel();
		addressPanel.setBeanProperty("address");
		getFormPanel().add(addressPanel);

		// Parent
		WLabel lbl = new WLabel("Parent Location", drpParent.getSelectInput());
		getFormLayout().addField(lbl, drpParent);
		drpParent.setIncludeNullOption(true);
		drpParent.setCodeProperty("id");
		drpParent.getOptionsView().setBeanProperty("parentId");
		drpParent.setRetrieveCollectionModelKey("location.search");

		// Sub Locations
		lbl = new WLabel("Sub Locations", multiSub.getSelectInput());
		getFormLayout().addField(lbl, multiSub);
		multiSub.setCodeProperty("id");
		multiSub.getOptionsView().setBeanProperty("subIds");
		multiSub.setRetrieveCollectionModelKey("location.search");

		// FIXME: Temporary delays as firing extra AJX Trigger
		drpParent.getPollingView().setPollingInterval(50);
		multiSub.getPollingView().setPollingInterval(75);

	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		drpParent.startLoad("");
		multiSub.startLoad("");
	}

	@Override
	protected void validateComponent(final List<Diagnostic> diags) {
		super.validateComponent(diags);
		Location current = getViewBean();

		// Check parent
		Location parent = drpParent.getSelectedOption();
		if (Objects.equal(current, parent)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot make the location its own parent."));
		}

		// Check Sub
		List<Location> subs = multiSub.getSelectedOptions();
		if (subs != null && subs.contains(current)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot add the location to itself."));
		}
		if (subs != null && parent != null && subs.contains(parent)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot have the same location as a child and parent."));
		}
	}

}
