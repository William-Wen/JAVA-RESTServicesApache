package service;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
//import javax.ejb.Stateless;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import domain.Food;
import domain.FoodList;
import domain.FoodManager;

/**
 * The JAX-RS service, deployable to GlassFish, which tracks food.
 *
 * @author William Wen
 *
 */
@Path("/foods")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//@Stateless
public class FoodRestService {
	@Context
	private UriInfo oUriInfo;

	@POST
	public Response create(Food food) {
		if (food == null) {
			throw new BadRequestException();
		}
		FoodManager.add(food);
		URI oUri = oUriInfo.getAbsolutePathBuilder().path(food.getName()).build();
		return Response.created(oUri).build();
	}

	@PUT
	public Response update(Food food) {
		if (food == null) {
			throw new BadRequestException();
		}
		FoodManager.delete(food.getName());
		FoodManager.add(food);
		return Response.ok().build();
	}

	@GET
	@Path("{name}")
	public Response get(@PathParam("name") String name) {
		Food oFood = FoodManager.find(name);
		if (oFood == null) {
			throw new NotFoundException();
		}
		return Response.ok(oFood).build();
	}

	@GET
	public Response getAll() {
		FoodList oFoodList = FoodManager.getFoods();
		GenericEntity<List<Food>> oFoods = new GenericEntity<List<Food>>(oFoodList){};
		return Response.ok(oFoods).build();
	}

	@RolesAllowed({"admin",})
	@DELETE
	@Path("{name}")
	public Response delete(@PathParam("name") String name) {
		Food oFood = FoodManager.find(name);
		if (oFood == null) {
			throw new NotFoundException();
		}
		FoodManager.delete(name);
		return Response.noContent().build();
	}
}
