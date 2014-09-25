package com.wataru420.helloworld.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.wataru420.helloworld.core.Music;

public class MusicDAO extends AbstractDAO<Music> {
    public MusicDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Music> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

    public Music create(Music music) {
        return persist(music);
    }

    public List<Music> findAll(Integer limit, Integer start, Integer artistId, String title) {
    	if (title != null) {
    		title = "%"+title+"%";
    	}
    	Query query = namedQuery("com.wataru420.helloworld.core.Music.findAll");
    	if (artistId != null && title != null) {
    		query = namedQuery("com.wataru420.helloworld.core.Music.findByArtistIdAndTitle");
    		query.setInteger("artistId", artistId);
    		query.setString("title", title);
    	} else if (artistId != null) {
    		query = namedQuery("com.wataru420.helloworld.core.Music.findByArtistId");
    		query.setInteger("artistId", artistId);
    	} else if (title != null) {
    		query = namedQuery("com.wataru420.helloworld.core.Music.findByTitle");
    		query.setString("title", title);
    	}
    	
    	if (start != null) {
    		query.setFirstResult(start);
    	}
    	
    	if (limit != null) {
    		query.setMaxResults(limit);
    	} else {
    		query.setMaxResults(100);
    	}
    	
        return list(query);
    }
}
