package com.wataru420.helloworld.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import javax.ws.rs.core.Response;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.wataru420.helloworld.core.PlayList;

public class PlayListDAO extends AbstractDAO<PlayList> {
    public PlayListDAO(SessionFactory factory) {
        super(factory);
    }


    public List<PlayList> findByName(String name) {
    	Query query = namedQuery("com.wataru420.helloworld.core.PlayList.findByName");
    	query.setString("name", name);
    	
        return list(query);
    }
    
    public PlayList create(PlayList music) {
        return persist(music);
    }

}
