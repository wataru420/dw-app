package com.wataru420.helloworld.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@IdClass(PlayListDetailPk.class)
@Table(name = "playlist_detail")
@NamedQueries({
	@NamedQuery(name = "com.wataru420.helloworld.core.PlayListDetail.deleteByPk", query = "delete PlayListDetail where playlist_name = :playlist_name and number = :number"),
	@NamedQuery(name = "com.wataru420.helloworld.core.PlayListDetail.findByName", query = "select d from PlayListDetail d where playlist_name = :name"),
	})
public class PlayListDetail {
	
	@Id
	private String playlist_name;
	@Id
	private Integer number;
	
	private Integer music_id;

	public String getPlaylist_name() {
		return playlist_name;
	}

	public void setPlaylist_name(String playlist_name) {
		this.playlist_name = playlist_name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getMusic_id() {
		return music_id;
	}

	public void setMusic_id(Integer music_id) {
		this.music_id = music_id;
	}


}
