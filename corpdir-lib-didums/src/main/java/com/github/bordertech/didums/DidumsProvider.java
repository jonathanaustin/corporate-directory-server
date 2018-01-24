package com.github.bordertech.didums;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * DI Provider.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DidumsProvider extends Serializable {

	<T> T getService(final Class<T> contract, final Annotation... qualifiers);

	<T> T createAndInject(final Class<T> createMe);

	<T, U extends T> void bind(final Class<T> contract, final Class<U> contractImpl, final boolean singleton, final Annotation... qualifiers);
}
