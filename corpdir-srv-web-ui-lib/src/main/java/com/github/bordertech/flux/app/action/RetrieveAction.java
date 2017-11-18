package com.github.bordertech.flux.app.action;

import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.key.ActionKey;

/**
 *
 * @author jonathan
 */
public class RetrieveAction extends DefaultAction {

	private final RetrieveCallType callType;

	public RetrieveAction(final RetrieveActionType actionType, final String qualifier, final Object data, final RetrieveCallType callType) {
		super(new ActionKey(actionType, qualifier), data);
		this.callType = callType;
	}

	public RetrieveCallType getCallType() {
		return callType;
	}
}
