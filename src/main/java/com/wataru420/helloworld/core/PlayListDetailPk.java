package com.wataru420.helloworld.core;

import java.io.Serializable;

public class PlayListDetailPk implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayListDetailPk() {

	}
	
	public PlayListDetailPk(String playlist_name, Integer number) {
		this.playlist_name = playlist_name;
		this.number = number;
	}
	
	protected String playlist_name;

	protected Integer number;

}
