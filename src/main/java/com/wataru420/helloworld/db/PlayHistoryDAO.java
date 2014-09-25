package com.wataru420.helloworld.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.wataru420.helloworld.core.PlayHistory;

public class PlayHistoryDAO extends AbstractDAO<PlayHistory> {
    public PlayHistoryDAO(SessionFactory factory) {
        super(factory);
    }
    
    public List<PlayHistory> countPlayTyme(Integer id) {
    	Query query = namedQuery("com.wataru420.helloworld.core.PlayHistory.countPlayTyme");
    	if (id != null) {
    		query = namedQuery("com.wataru420.helloworld.core.PlayHistory.countPlayTymeById");
    		query.setInteger("id", id);
    	}
    	query.setMaxResults(100);
    	return list(query);
    }

    
    public List<PlayHistory> findAll(Integer limit, Integer start) {

    	Query query = namedQuery("com.wataru420.helloworld.core.PlayHistory.findAll");

    	
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
