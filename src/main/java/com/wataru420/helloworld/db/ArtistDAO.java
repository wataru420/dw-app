package com.wataru420.helloworld.db;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.wataru420.helloworld.core.Artist;

public class ArtistDAO extends AbstractDAO<Artist> {
    public ArtistDAO(SessionFactory factory) {
        super(factory);
    }
    
    public Artist create(Artist artist) {
        return persist(artist);
    }

    public Optional<Artist> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

}
