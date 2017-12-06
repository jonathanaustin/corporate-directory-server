package com.github.bordertech.wcomponents.lib.security;

import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 */
public class DefaultAppPath implements AppPath {

	private final String path;

	public DefaultAppPath(final String path) {
		this.path = path;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 53 * hash + Objects.hashCode(this.path);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DefaultAppPath other = (DefaultAppPath) obj;
		if (!Objects.equals(this.path, other.path)) {
			return false;
		}
		return true;
	}

}
