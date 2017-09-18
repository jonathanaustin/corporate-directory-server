package com.github.bordertech.wcomponents.lib.app.view.input;

import com.github.bordertech.wcomponents.AbstractWSelectList;
import com.github.bordertech.wcomponents.lib.app.view.form.FormUpdateable;
import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.List;

/**
 * Form input from options view. The view bean is the input value.
 *
 * @param <T> the option type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface InputOptionsView<T> extends View<T>, FormUpdateable {

	void setOptions(final List<T> options);

	List<T> getOptions();

	void addOption(final T option);

	void removeOption(final T option);

	void updateOption(final T option);

	int getSize();

	AbstractWSelectList getSelectInput();

	void showView(final boolean show);

}
