package com.github.bordertech.flux.crud.action;

import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.key.ActionKey;

/**
 * Retrieve Action holds the Call Type details.
 *
 * @author jonathan
 */
public class RetrieveAction extends DefaultAction {

	private final CallType callType;

	public RetrieveAction(final RetrieveActionType actionType, final String qualifier, final Object data, final CallType callType) {
		super(new ActionKey(actionType, qualifier), data);
		this.callType = callType;
	}

	public CallType getCallType() {
		return callType;
	}
}
