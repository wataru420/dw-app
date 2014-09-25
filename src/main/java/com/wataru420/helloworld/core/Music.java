package com.wataru420.helloworld.core;

import javax.persistence.*;

@Entity
@Table(name = "music")
@NamedQueries({
    @NamedQuery(
        name = "com.wataru420.helloworld.core.Music.findAll",
        query = "SELECT m FROM Music m"
    )
})
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "artist_id", nullable = false)
    private Integer artistId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "outline", nullable = true)
    private String outline;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
