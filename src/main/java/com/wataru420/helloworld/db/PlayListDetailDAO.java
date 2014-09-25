package com.wataru420.helloworld.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import javax.ws.rs.core.Response;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.wataru420.helloworld.core.PlayListDetail;

public class PlayListDetailDAO extends AbstractDAO<PlayListDetail> {
    public PlayListDetailDAO(SessionFactory factory) {
        super(factory);
    }


    public List<PlayListDetail> findByName(String name) {
    	Query query = namedQuery("com.wataru420.helloworld.core.PlayListDetail.findByName");
    	query.setString("name", name);
    	
        return list(query);
    }
    

    public PlayListDetail create(PlayListDetail data) {
        return persist(data);
    }
    

    public Response deleteByPk(String playlistName,Integer number) {
    	Query query = namedQuery("com.wataru420.helloworld.core.PlayListDetail.deleteByPk");
    	query.setString("playlist_name", playlistName);
    	query.setInteger("number", number);
    	int res = query.executeUpdate();
    	if (res == 0) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
}
