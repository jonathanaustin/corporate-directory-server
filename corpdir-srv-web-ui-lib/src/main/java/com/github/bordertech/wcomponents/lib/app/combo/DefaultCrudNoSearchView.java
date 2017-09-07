package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.lib.app.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;

/**
 * Default CRUD view (No Search).
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <U> the entity model
 */
public class DefaultCrudNoSearchView<T, U extends ActionModel> extends DefaultView<T> {

	private final FormWithSelectView<T> view;

	public DefaultCrudNoSearchView(final String title, final U model, final Container formPanel) {
		this(title, model, formPanel, null);
	}

	public DefaultCrudNoSearchView(final String title, final U model, final Container formPanel, final String qualifier) {
		super(qualifier);

		// Form View
		FormWithToolbarView<T> entView = new FormWithToolbarView<>(qualifier);
		entView.getFormView().getFormHolder().add(formPanel);
		// Select View
		SelectView<T> select = new SelectMenuView<>(qualifier);

		// Form and Select View Together
		view = new FormWithSelectView<>(entView, select, qualifier);

		String prefix = qualifier == null ? "" : qualifier;

		entView.addDispatcherOverride(prefix + "-2", MsgEventType.values());
		entView.addListenerOverride(prefix + "-2", MsgEventType.values());

		// Set Models
		view.addModel("action", model);
		view.setBlocking(true);

		getContent().add(view);

//		WSection section = new WSection(title);
//		section.getContent().add(view);
//		section.setMargin(new Margin(Size.XL));
//		WContainer content = getContent();
//		content.add(section);
	}

	public View<T> getCrudView() {
		return view;
	}

}
