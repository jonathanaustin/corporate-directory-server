package com.github.bordertech.flux.crud.action.retrieve;

import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.key.ActionKey;

/**
 * Retrieve Action holds the Call Type details.
 *
 * @author jonathan
 * @param <T> the retrieve payload type
 */
public class RetrieveAction<T> extends DefaultAction<T> {

	private final CallType callType;

	public RetrieveAction(final RetrieveActionType actionType, final String qualifier, final T data, final CallType callType) {
		super(new ActionKey(actionType, qualifier), data);
		this.callType = callType;
	}

	public CallType getCallType() {
		return callType;
	}
}
