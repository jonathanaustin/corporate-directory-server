package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.app.dataapi.modify.ModifyEntityApi;
import com.github.bordertech.flux.app.dataapi.modify.ModifyTreeApi;
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
		return createModifyCreatorInstance(type, null);
	}

	public static ModifyCreator getModifyCreatorInstance(final DataApiType type, final String qualifier) {
		return createModifyCreatorInstance(type, qualifier);
	}

	private static ModifyCreator createModifyCreatorInstance(final DataApiType type, final String qualifier) {
		String suffix = qualifier == null ? "" : qualifier;
		String key = type.getKey() + "-" + suffix;
		ModifyCreator creator = CREATORS.get(key);
		if (creator != null) {
			return creator;
		}
		DataApi api = DataApiFactory.getInstance(type);
		if (api instanceof ModifyTreeApi) {
			creator = new DefaultModifyTreeCreator((ModifyTreeApi) api, qualifier);
		} else if (api instanceof ModifyEntityApi) {
			creator = new DefaultModifyEntityCreator((ModifyEntityApi) api, qualifier);
		} else {
			throw new IllegalStateException("No action creator available for datatype [" + key + "]");
		}
		CREATORS.put(key, creator);
		return creator;
	}

}
