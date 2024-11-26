package com.ox.banking.enums;

public enum Language {
	IT("Italiano"), EN("English"), ES("Español"), FR("Français");

	private final String description;

	Language(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}