package com.wataru420.helloworld.core;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "playlist")
@NamedQueries({
		@NamedQuery(name = "com.wataru420.helloworld.core.PlayList.findByName", query = "select l from PlayList l where name = :name"),
		})
public class PlayList {
	@Id
	private String name;
	
	private String outline;

	@OneToMany(mappedBy = "id")
	private Collection<Music> musics = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public Collection<Music> getMusics() {
		return musics;
	}

	public void setMusics(Collection<Music> musics) {
		this.musics = musics;
	}


	

}
