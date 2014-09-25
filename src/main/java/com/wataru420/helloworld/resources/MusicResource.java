package com.wataru420.helloworld.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.LongParam;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	public List<Music> listPeople(@QueryParam("limit") IntParam limit,
			@QueryParam("start") IntParam start,
			@QueryParam("artist_id") IntParam artistId,
			@QueryParam("title") String title) {
		return musicDAO.findAll((limit != null) ? limit.get() : null,
				(start != null) ? start.get() : null,
				(artistId != null) ? artistId.get() : null, title);
	}

	@GET
	@UnitOfWork
	@Path("/{id}")
	public Music getPerson(@PathParam("id") LongParam id) {

		return null;
	}
}
