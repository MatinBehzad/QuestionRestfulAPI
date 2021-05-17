/**
 * 
 */
package resources;


import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.soteria.WrappingCallerPrincipal;

import controller.Service;
import entity.Person;
import entity.SecurityUser;


/**
 * @author matineh
 *
 */
@Path("person")
@Consumes( MediaType.APPLICATION_JSON)
@Produces( MediaType.APPLICATION_JSON)
public class PersonResources {

	public class PersonResource {

		@EJB
		protected Service service;

		@Inject
		protected SecurityContext sc;

		@GET
	    @RolesAllowed({"ADMIN_ROLE"})
		public Response getPersons() {
			List< Person> persons = service.getAllPeople();
			Response response = Response.ok( persons).build();
			return response;
		}

		@GET
		@RolesAllowed( { "ADMIN_ROLE", "USER_ROLE" })
		@Path( "/{" + "id" + "}")
		public Response getPersonById( @PathParam("id") int id) {
			Response response = null;
			Person person = null;

			if ( sc.isCallerInRole("ADMIN_ROLE")) {
				person = service.getPersonId( id);
				response = Response.status( person == null ? Status.NOT_FOUND : Status.OK).entity( person).build();
			} else if ( sc.isCallerInRole( "USER_ROLE")) {
				WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
				SecurityUser sUser = (SecurityUser) wCallerPrincipal.getWrapped();
				person = sUser.getPerson();
				if ( person != null && person.getId() == id) {
					response = Response.status( Status.OK).entity( person).build();
				} else {
					throw new ForbiddenException( "User trying to access resource it does not own (wrong userid)");
				}
			} else {
				response = Response.status( Status.BAD_REQUEST).build();
			}
			return response;
		}

		@POST
		@RolesAllowed( {" ADMIN_ROLE "})
		public Response addPerson( Person newPerson) {
			Response response = null;
			Person newPersonWithIdTimestamps = service.persistPerson( newPerson);
			// build a SecurityUser linked to the new person
			service.buildUserForNewPerson( newPersonWithIdTimestamps);
			response = Response.ok( newPersonWithIdTimestamps).build();
			return response;
		}

}
}
