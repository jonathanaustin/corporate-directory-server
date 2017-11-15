package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.dataapi.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Model provider factory.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ModifyCreatorFactory {

	private static final Map<String, ModifyCreator> CREATORS = new HashMap<>();

	private ModifyCreatorFactory() {
	}

	public static ModifyCreator getModifyCreatorInstance(final DataApiType type) {
		return createModifyCreatorInstance(false, type, null);
	}

	public static ModifyCreator getModifyCreatorInstance(final DataApiType type, final String qualifier) {
		return createModifyCreatorInstance(false, type, qualifier);
	}

	public static ModifyTreeCreator getModifyTreeCreatorInstance(final DataApiType type) {
		return (ModifyTreeCreator) createModifyCreatorInstance(true, type, null);
	}

	public static ModifyTreeCreator getModifyTreeCreatorInstance(final DataApiType type, final String qualifier) {
		return (ModifyTreeCreator) createModifyCreatorInstance(true, type, qualifier);
	}

	private static ModifyCreator createModifyCreatorInstance(final boolean tree, final DataApiType type, final String qualifier) {
		String prefix = tree ? "tree-" : "def-";
		String suffix = qualifier == null ? "" : qualifier;
		String key = prefix + type.getKey() + "-" + suffix;
		ModifyCreator creator = CREATORS.get(key);
		if (creator != null) {
			return creator;
		}
		if (tree) {
			creator = new DefaultModifyCreator(type, qualifier);
		} else {
			creator = new DefaultModifyTreeCreator(type, qualifier);
		}
		CREATORS.put(key, creator);
		return creator;
	}

}
