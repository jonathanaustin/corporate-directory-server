package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.api.v1.model.ChannelTypeEnum;
import com.github.bordertech.corpdir.web.ui.config.DataApiType;
import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.smart.table.TableInlineEditingSmartView;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.lib.table.AbstractTableEditableColumn;
import com.github.bordertech.wcomponents.lib.table.TableColumn;
import java.util.ArrayList;
import java.util.List;

/**
 * Channel table panel.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ChannelTablePanel extends DefaultDumbView<List<Channel>> {

	/**
	 * Construct basic channel panel.
	 *
	 * @param viewId the viewId
	 */
	public ChannelTablePanel(final String viewId) {
		super(viewId);

		// Setup table columns
		List<TableColumn<?, Channel>> cols = new ArrayList<>();
		// Business Key
		cols.add(new AbstractTableEditableColumn<String, Channel>("Key") {
			@Override
			public String getValue(final Channel bean) {
				return bean.getBusinessKey();
			}

			@Override
			public void setValue(final Channel bean, final String value) {
				bean.setBusinessKey(value);
			}
		});
		// Type
		cols.add(new AbstractTableEditableColumn<ChannelTypeEnum, Channel>("Type", new WDropdown(ChannelTypeEnum.values())) {
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
		cols.add(new AbstractTableEditableColumn<String, Channel>("Detail") {
			@Override
			public String getValue(final Channel bean) {
				return bean.getValue();
			}

			@Override
			public void setValue(final Channel bean, final String value) {
				bean.setValue(value);
			}
		});

		// Add Table
		TableInlineEditingSmartView<Channel> tableView = new TableInlineEditingSmartView<>("CHNLT", cols);
		tableView.setBeanProperty(".");
		tableView.setSearchAncestors(true);
		tableView.setEntityActionCreatorKey(DataApiType.CHANNEL.getActionCreatorKey());
		getContent().add(tableView);
	}

}
