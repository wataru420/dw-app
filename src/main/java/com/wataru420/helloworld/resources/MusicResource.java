package com.wataru420.helloworld.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import redis.clients.jedis.JedisPool;

import com.google.common.base.Optional;
import com.wataru420.helloworld.core.Artist;
import com.wataru420.helloworld.core.Music;
import com.wataru420.helloworld.core.PlayHistory;
import com.wataru420.helloworld.db.ArtistDAO;
import com.wataru420.helloworld.db.MusicDAO;
import com.wataru420.helloworld.db.PlayHistoryDAO;

@Path("/api/musics")
@Produces(MediaType.APPLICATION_JSON)
public class MusicResource {

	private final MusicDAO musicDAO;
	private final PlayHistoryDAO playHistoryDAO;
	private final ArtistDAO artistDAO;

	public MusicResource(MusicDAO musicDAO, PlayHistoryDAO playHistoryDAO,ArtistDAO artistDAO,JedisPool jedisPool) {
		this.musicDAO = musicDAO;
		this.playHistoryDAO = playHistoryDAO;
		this.artistDAO = artistDAO;
	}

	@POST
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createPerson(@FormParam("artist_id") IntParam artistId,@FormParam("title") String title,@FormParam("outline") String outline) throws URISyntaxException {
		if (artistId.get() == null || title == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		
		Optional<Artist> artist = artistDAO.findById(artistId.get());
		if (artist.get() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		Music music = new Music();
		music.setArtistId(artistId.get());
		music.setTitle(title);
		music.setOutline(outline);
		music = musicDAO.create(music);
		URI uri = new URI("/api/musics/" + music.getId());
		return Response.status(Response.Status.CREATED).location(uri).build();
	}
	
	@PUT
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{id}")
	public Response editPerson(@PathParam("id") IntParam id,@FormParam("artist_id") IntParam artistId,@FormParam("title") String title,@FormParam("outline") String outline) throws URISyntaxException {
		if (artistId.get() == null || title == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		Optional<Artist> artist = artistDAO.findById(artistId.get());
		if (artist.get() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		Optional<Music> musicOptional = musicDAO.findById(id.get());
		if (musicOptional.get() == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		Music music = musicOptional.get();
		music.setArtistId(artistId.get());
		music.setTitle(title);
		music.setOutline(outline);
		music = musicDAO.create(music);
		URI uri = new URI("/api/musics/" + music.getId());
		return Response.status(Response.Status.NO_CONTENT).location(uri).build();
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
	public Music getMusic(@PathParam("id") IntParam id) {
		final Optional<Music> music = musicDAO.findById(id.get());
		return music.get();
	}
	
	@DELETE
	@UnitOfWork
	@Path("/{id}")
	public Response deleteMusic(@PathParam("id") IntParam id) {
		return musicDAO.deleteById(id.get());
	}
	
	@POST
	@UnitOfWork
	@Path("/{id}/play")
	public Response playMusic(@PathParam("id") IntParam id) {
		final Optional<Music> musicOptional = musicDAO.findById(id.get());
		Music music = musicOptional.get();
		if (music == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		//TODO あとで
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	
	@GET
	@UnitOfWork
	@Path("/times")
	public List<PlayHistory> countHistory(@QueryParam("id") IntParam id) {
		List<PlayHistory> res = playHistoryDAO.countPlayTyme((id != null)?id.get():null);
		for (PlayHistory h : res ) {
			
		}
		return res;
	}
	
	@GET
	@UnitOfWork
	@Path("/times")
	public Response countHistory(@QueryParam("id") IntParam id) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@GET
	@UnitOfWork
	@Path("/recent")
	public List<PlayHistory> recent(@QueryParam("limit") IntParam limit,
			@QueryParam("start") IntParam start) {
		return playHistoryDAO.findAll((limit != null) ? limit.get() : null,
				(start != null) ? start.get() : null);
	}
}
