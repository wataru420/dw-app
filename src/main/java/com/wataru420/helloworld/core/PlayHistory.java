package com.wataru420.helloworld.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "play_history")
@NamedQueries({
		@NamedQuery(name = "com.wataru420.helloworld.core.PlayHistory.countPlayTyme", query = "select h from PlayHistory h group by music_id"),
		@NamedQuery(name = "com.wataru420.helloworld.core.PlayHistory.countPlayTymeById", query = "select h from PlayHistory h group by music_id where music_id= :id"),
		@NamedQuery(name = "com.wataru420.helloworld.core.PlayHistory.findAll", query = "select h from PlayHistory h ORDER BY created_at DESC"),
		})
public class PlayHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "music_id", nullable = false)
	private Integer musicId;
	
	private String created_at;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public Integer getMusicId() {
		return musicId;
	}

	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}

	@JsonProperty("last_played")
	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}



	
	

}
