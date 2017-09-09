package com.github.bordertech.wcomponents.lib.mvc;

import java.io.Serializable;

/**
 * Model provider.
 *
 * @author jonathan
 */
public interface ModelProvider extends Serializable {

	Model getModel(final String key);

}
