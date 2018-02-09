package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * Views are responsible for rendering the UI.
 *
 * @see com.github.bordertech.flux.view.SmartView
 * @see com.github.bordertech.flux.view.DumbView
 *
 * @param <T> the view bean
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface View<T> extends Serializable {

}
