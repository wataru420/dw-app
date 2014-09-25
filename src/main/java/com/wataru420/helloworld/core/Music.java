package com.wataru420.helloworld.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "music")
@NamedQueries({
		@NamedQuery(name = "com.wataru420.helloworld.core.Music.deleteById", query = "delete Music where id = :id"),
		@NamedQuery(name = "com.wataru420.helloworld.core.Music.findAll", query = "SELECT m FROM Music m"),
		@NamedQuery(name = "com.wataru420.helloworld.core.Music.findByArtistId", query = "SELECT m FROM Music m where artist_id= :artistId"),
		@NamedQuery(name = "com.wataru420.helloworld.core.Music.findByTitle", query = "SELECT m FROM Music m where title like :title"),
		@NamedQuery(name = "com.wataru420.helloworld.core.Music.findByArtistIdAndTitle", query = "SELECT m FROM Music m where artist_id= :artistId and  title like :title")})
public class Music {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "artist_id", nullable = false)
	private Integer artistId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "outline", nullable = true)
	private String outline;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("artist_id")
	public Integer getArtistId() {
		return artistId;
	}

	public void setArtistId(Integer artistId) {
		this.artistId = artistId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

}
