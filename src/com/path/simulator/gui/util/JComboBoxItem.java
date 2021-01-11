package com.path.simulator.gui.util;

public class JComboBoxItem {

	private String id;
	private String description;

	public JComboBoxItem(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}
}