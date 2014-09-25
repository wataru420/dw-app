package com.wataru420.helloworld.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.wataru420.helloworld.core.Music;

public class MusicDAO extends AbstractDAO<Music> {
    public MusicDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Music> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Music create(Music music) {
        return persist(music);
    }

    public List<Music> findAll(Integer limit) {
        return list(namedQuery("com.wataru420.helloworld.core.Music.findAll").setMaxResults(limit));
    }
}
