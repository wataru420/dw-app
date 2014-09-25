package com.wataru420.helloworld.resources;

import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import com.wataru420.helloworld.core.Person;
import com.wataru420.helloworld.db.PersonDAO;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {

    private final PersonDAO peopleDAO;
    
    private final JedisPool jedisPool;

    public PeopleResource(PersonDAO peopleDAO, JedisPool jedisPool) {
        this.peopleDAO = peopleDAO;
        this.jedisPool = jedisPool;
    }

    @POST
    @UnitOfWork
    public Person createPerson(Person person) {
    	Person res = peopleDAO.create(person);
    	Jedis jedis = jedisPool.getResource();
    	String key = "Person" +  String.valueOf(person.getId());
    	jedis.set(key, person.getFullName());
    	System.out.println(jedis.get(key));
        return res;
    }

    @GET
    @UnitOfWork
    public List<Person> listPeople() {
        return peopleDAO.findAll();
    }

    @GET
    @UnitOfWork
    @Path("/{personId}")
    public Person getPerson(@PathParam("personId") LongParam personId) {
        final Optional<Person> person = peopleDAO.findById(personId.get());
        Jedis jedis = jedisPool.getResource();
        String key = "Person" +  String.valueOf(personId);
        System.out.println(jedis.get(key));
        jedisPool.returnResource(jedis);
        if (!person.isPresent()) {
            throw new NotFoundException("{status:notfound}");
        }
        return person.get();
    }
}
