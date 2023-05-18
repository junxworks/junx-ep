package io.github.junxworks.ep.auth.simple;

import java.util.List;

import com.google.common.collect.Lists;

public class SimpleAccount {
	private String epRamKey;

	private String epRamSecret;

	private List<String> authorizes = Lists.newArrayList();

	public String getEpRamKey() {
		return epRamKey;
	}

	public void setEpRamKey(String epRamKey) {
		this.epRamKey = epRamKey;
	}

	public String getEpRamSecret() {
		return epRamSecret;
	}

	public void setEpRamSecret(String epRamSecret) {
		this.epRamSecret = epRamSecret;
	}

	public List<String> getAuthorizes() {
		return authorizes;
	}

	public void setAuthorizes(List<String> authorizes) {
		this.authorizes = authorizes;
	}

}
