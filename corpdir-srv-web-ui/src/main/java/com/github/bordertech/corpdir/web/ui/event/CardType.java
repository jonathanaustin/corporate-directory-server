package com.github.bordertech.corpdir.web.ui.event;

import com.github.bordertech.corpdir.web.ui.view.PositionTypeCrudView;
import com.github.bordertech.corpdir.web.ui.view.UnitTypeCrudView;
import com.github.bordertech.wcomponents.lib.mvc.View;

/**
 * Cards.
 *
 * @author jonathan
 */
public enum CardType {
	POSITION_TYPE_CARD("Position Type", true, PositionTypeCrudView.class),
	UNIT_TYPE_CARD("Unit Type", true, UnitTypeCrudView.class);

	CardType(final String desc, final boolean system, final Class<? extends View> clazz) {
		this.desc = desc;
		this.system = system;
		this.clazz = clazz;
	}

	final String desc;
	final boolean system;
	final Class<? extends View> clazz;

	public String getDesc() {
		return desc;
	}

	public boolean isSystem() {
		return system;
	}

	public Class<? extends View> getClazz() {
		return clazz;
	}

	public View createCardViewInstance(final String qualifier) {
		try {
			return (View) getClazz().getConstructor(String.class).newInstance(qualifier);
		} catch (Exception e) {
			throw new IllegalStateException("Could not create view class. " + e.getMessage(), e);
		}
	}
}
