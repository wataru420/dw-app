package com.wataru420.helloworld.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import redis.clients.jedis.JedisPool;

import com.wataru420.helloworld.core.Music;
import com.wataru420.helloworld.core.Person;
import com.wataru420.helloworld.db.MusicDAO;

@Path("/music")
@Produces(MediaType.APPLICATION_JSON)
public class MusicResource {
    
    private final MusicDAO musicDAO;


    public MusicResource(MusicDAO musicDAO, JedisPool jedisPool) {
        this.musicDAO = musicDAO;
    }

    @POST
    @UnitOfWork
    public Person createPerson(Person person) {

        return null;
    }

    @GET
    @UnitOfWork
    public List<Music> listPeople() {
        return musicDAO.findAll(100);
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Music getPerson(@PathParam("id") LongParam id) {

        return null;
    }
}
