package com.wataru420.helloworld.resources;

import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.common.base.Charsets;

@Path("/views")
public class ViewResource {
    @GET
    @Produces("text/html;charset=UTF-8")
    @Path("/utf8.ftl")
    public View freemarkerUTF8() {
        return new View("/views/ftl/utf8.ftl", Charsets.UTF_8) {
        };
    }

    @GET
    @Produces("text/html;charset=UTF-8")
    @Path("/utf8.mustache")
    public View mustacheUTF8() {
        return new View("/views/mustache/utf8.mustache", Charsets.UTF_8) {
        };
    }

}