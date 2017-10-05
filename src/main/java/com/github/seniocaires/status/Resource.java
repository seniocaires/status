package com.github.seniocaires.status;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/status")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class Resource {

  @Inject
  private Service service;

  @GET
  @Path("/")
  public Response status() {
    return Response.ok(new Gson().toJson(service.buscarTodos())).build();
  }
}
