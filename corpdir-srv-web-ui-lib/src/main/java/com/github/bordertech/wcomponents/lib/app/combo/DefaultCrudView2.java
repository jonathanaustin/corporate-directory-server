package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Size;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.model.SearchModel;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 * @param <U> the entity model
 */
public class DefaultCrudView2<S, T, U extends ActionModel<T> & SearchModel<S, List<T>>> extends DefaultView {

	public DefaultCrudView2(final String title, final U model, final Container formPanel) {
		this(title, model, formPanel, null);
	}

	public DefaultCrudView2(final String title, final U model, final Container formPanel, final String qualifier) {
		super(qualifier);

		// Form View
		FormWithToolbarView<T> entView = new FormWithToolbarView<>(qualifier);
		entView.getFormView().getFormHolder().add(formPanel);
		// Select View
		SelectWithCriteriaTextView<T> select = new SelectWithCriteriaTextView<>(qualifier);
		// Form and Select View Together
		FormWithSelectView<T> view = new FormWithSelectView<>(entView, select, qualifier);

		String prefix = qualifier == null ? "" : qualifier;

		select.addDispatcherOverride(prefix + "-1", MsgEventType.values());
		select.addListenerOverride(prefix + "-1", MsgEventType.values());

		entView.addDispatcherOverride(prefix + "-2", MsgEventType.values());
		entView.addListenerOverride(prefix + "-2", MsgEventType.values());

		// Set Models
		view.addModel("search", model);
		view.addModel("action", model);
		view.setBlocking(true);

		WSection section = new WSection(title);
		section.getContent().add(view);
		section.setMargin(new Margin(Size.XL));

		WContainer content = getContent();
		content.add(section);
	}

}
