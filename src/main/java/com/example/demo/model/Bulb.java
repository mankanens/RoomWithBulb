package com.example.demo.model;

import java.io.Serializable;

public class Bulb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5280030792886530901L;
	
	Boolean isOn = false;

	public Boolean getIsOn() {
		return isOn;
	}

	public void setIsOn(Boolean isOn) {
		this.isOn = isOn;
	}

	public void toogle() {
		if (this.isOn) {
			this.setIsOn(false);
		} else {
			this.setIsOn(true);
		}
	}

}
