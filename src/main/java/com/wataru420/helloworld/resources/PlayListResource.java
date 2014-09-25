package com.wataru420.helloworld.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import redis.clients.jedis.JedisPool;

import com.google.common.base.Optional;
import com.wataru420.helloworld.core.Music;
import com.wataru420.helloworld.core.PlayList;
import com.wataru420.helloworld.core.PlayListDetail;
import com.wataru420.helloworld.db.MusicDAO;
import com.wataru420.helloworld.db.PlayListDAO;
import com.wataru420.helloworld.db.PlayListDetailDAO;

@Path("/api/playlists")
@Produces(MediaType.APPLICATION_JSON)
public class PlayListResource {

	private final PlayListDAO playListDAO;
	private final PlayListDetailDAO playListDetailDAO;
	private final MusicDAO musicDAO;

	public PlayListResource(PlayListDAO playListDAO,PlayListDetailDAO playListDetailDAO,MusicDAO musicDAO,JedisPool jedisPool) {
		this.playListDAO = playListDAO;
		this.playListDetailDAO = playListDetailDAO;
		this.musicDAO = musicDAO;
	}



	@GET
	@UnitOfWork
	@Path("/{name}")
	public Response getMusic(@PathParam("name") String name) {
		final List<PlayList> playLists = playListDAO.findByName(name);
		if (playLists.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
//		for (PlayList playList : playLists) {
//			List<PlayListDetail> list = playListDetailDAO.findByName(name);
//			playList.setPlayListDetails(list);
//		}
		List<PlayListDetail> list = playListDetailDAO.findByName(name);
		List<Music> musics = new ArrayList<>();
		for (PlayListDetail d : list) {
			if (d.getMusic_id() != null) {
				Optional<Music> m = musicDAO.findById(d.getMusic_id());
				musics.add(m.get());
			}
		}
		playLists.get(0).setMusics(musics);
		return Response.status(Response.Status.OK).entity(playLists).build();
	}
	
	@POST
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{name}")
	public Response createPerson(@PathParam("name") String name,@FormParam("outline") String outline,@FormParam("musics") String musics) throws URISyntaxException {
		List<PlayListDetail> list = playListDetailDAO.findByName(name);
		if (!list.isEmpty()) {
			Response.status(Response.Status.CONFLICT).build();
		}
		if (musics != null) {
			String[] musicIds = musics.split(",");
			int num = 1;
			for (String id : musicIds) {
				//TODO 存在チェック
				PlayListDetail d = new PlayListDetail();
				d.setMusic_id(Integer.getInteger(id));
				d.setPlaylist_name(name);
				d.setNumber(num);
				num++;
				playListDetailDAO.create(d);
			}
		}
		
		PlayList playList = new PlayList();
		playList.setName(name);
		playList.setOutline(outline);
		playListDAO.create(playList);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@POST
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{name}/add")
	public Response addMusic(@PathParam("name") String name,@FormParam("music_id") IntParam musicId,@FormParam("number") IntParam number) throws URISyntaxException {
		
		if (musicId == null || number == null || number.get() < 1) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		final List<PlayList> playLists = playListDAO.findByName(name);
		if (!playLists.isEmpty()) {
			Response.status(Response.Status.NOT_FOUND).build();
		}
		Optional<Music> m = musicDAO.findById(musicId.get());
		if (m.get() == null) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		List<PlayListDetail> list = playListDetailDAO.findByName(name);
		int num = 1;
		for (PlayListDetail d : list) {
			if (number.get().equals(num)) {
				PlayListDetail nd = new PlayListDetail();
				nd.setMusic_id(musicId.get());
				nd.setPlaylist_name(name);
				nd.setNumber(num);
				playListDetailDAO.create(nd);
				num++;
			}
			d.setNumber(num);
			playListDetailDAO.create(d);
			num++;
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@POST
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{name}/remove")
	public Response deleteMusic(@PathParam("name") String name,@FormParam("number") IntParam number) throws URISyntaxException {
		
		if (number == null || number.get() < 1) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		final List<PlayList> playLists = playListDAO.findByName(name);
		if (!playLists.isEmpty()) {
			Response.status(Response.Status.NOT_FOUND).build();
		}
		
		List<PlayListDetail> list = playListDetailDAO.findByName(name);
		int num = 1;
		for (PlayListDetail d : list) {
			if (number.get().equals(d.getNumber())) {
				playListDetailDAO.deleteByPk(d.getPlaylist_name(), d.getNumber());
			} else {
				d.setNumber(num);
				playListDetailDAO.create(d);
				num++;
			}
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}
