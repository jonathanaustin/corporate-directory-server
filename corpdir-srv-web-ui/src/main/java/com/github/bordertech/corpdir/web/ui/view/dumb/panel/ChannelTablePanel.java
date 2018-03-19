package com.github.bordertech.corpdir.web.ui.view.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.api.v1.model.ChannelTypeEnum;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.smart.table.TableInlineEditingSmartView;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WEmailField;
import com.github.bordertech.wcomponents.WPhoneNumberField;
import com.github.bordertech.wcomponents.addons.common.WDiv;
import com.github.bordertech.wcomponents.addons.table.AbstractTableEditableColumn;
import com.github.bordertech.wcomponents.addons.table.TableColumn;
import com.github.bordertech.wcomponents.addons.table.edit.RowMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Channel table panel.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ChannelTablePanel extends DefaultDumbView<List<Channel>> {

	private final TableInlineEditingSmartView<Channel> tableView;

	/**
	 * Construct basic channel panel.
	 *
	 * @param viewId the viewId
	 */
	public ChannelTablePanel(final String viewId) {
		super(viewId);

		// Type
		final WDropdown type = new WDropdown(ChannelTypeEnum.values()) {
			@Override
			public boolean isReadOnly() {
				return tableView.getCurrentRowMode() == RowMode.READ;
			}
		};
		type.setMandatory(true);
		type.setToolTip("channel type");

		// Phone
		final WPhoneNumberField phone = new WPhoneNumberField() {
			@Override
			public boolean isVisible() {
				return type.getSelected() != ChannelTypeEnum.EMAIL;
			}

			@Override
			public void updateBeanValue() {
				if (isVisible()) {
					super.updateBeanValue();
				}
			}

			@Override
			public boolean isReadOnly() {
				return tableView.getCurrentRowMode() == RowMode.READ;
			}
		};

		// Email
		WEmailField email = new WEmailField() {
			@Override
			public boolean isVisible() {
				return type.getSelected() == ChannelTypeEnum.EMAIL;
			}

			@Override
			public void updateBeanValue() {
				if (isVisible()) {
					super.updateBeanValue();
				}
			}

			@Override
			public boolean isReadOnly() {
				return tableView.getCurrentRowMode() == RowMode.READ;
			}
		};

		// Detail Panel
		WDiv valuePanel = new WDiv();
		valuePanel.add(phone);
		valuePanel.add(email);
		// Ajax (Could use subordinate)
		valuePanel.add(new WAjaxControl(type, valuePanel));

		// Defaults
		phone.setToolTip("Phone number");
		phone.setPlaceholder("Phone number");
		phone.setMandatory(true);
		email.setToolTip("Email address");
		email.setPlaceholder("Email address");
		email.setMandatory(true);

		// Setup table columns
		List<TableColumn<?, Channel>> cols = new ArrayList<>();
		// Type
		cols.add(new AbstractTableEditableColumn<ChannelTypeEnum, Channel>("Type", type) {
			@Override
			public ChannelTypeEnum getValue(final Channel bean) {
				return bean.getType();
			}

			@Override
			public void setValue(final Channel bean, final ChannelTypeEnum value) {
				bean.setType(value);
			}
		});
		// Value
		// FIXME WComponents should be able to use SetValueAt for Containers. Becasue the column is a DIV it needs the whole bean passed to it and set the bean propertis on the phone and email fields.
		cols.add(new AbstractTableEditableColumn<Channel, Channel>("Detail", valuePanel) {
			@Override
			public Channel getValue(final Channel bean) {
				return bean;
			}
		});
		// FIXME Have to set the bean property as WTable does not call setValueAt when the column is a container
		phone.setBeanProperty("value");
		email.setBeanProperty("value");
		valuePanel.setBeanProperty(".");

		// Add Table
		tableView = new TableInlineEditingSmartView<>("CHNLT", cols);
		tableView.setBeanProperty(".");
		tableView.setSearchAncestors(true);
		tableView.setActionCreatorKey(CorpEntityType.CHANNEL.getActionCreatorKey());
		tableView.getTableView().getAddButton().setText("Add channel");
		getContent().add(tableView);
	}

}
