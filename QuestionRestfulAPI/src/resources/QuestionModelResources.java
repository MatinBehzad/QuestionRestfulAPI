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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.Service;
import entity.QuestionModel;

/**
 * @author matineh
 *
 */
@Path("question")
@Consumes( MediaType.APPLICATION_JSON)
@Produces( MediaType.APPLICATION_JSON)
public class QuestionModelResources {
	

	@EJB
	protected Service service;

	@Inject
	protected SecurityContext sc;
	
	@GET
	@RolesAllowed( { "ADMIN_ROLE", "USER_ROLE" })
	public Response getPersons() {
		List< QuestionModel> questions = service.getAllQuestion();
		Response response = Response.ok( questions).build();
		return response;
	}
	
	@POST
	@RolesAllowed({"ADMIN_ROLE" , "USER_ROLE"})
	public Response addQuestionRecord(QuestionModel newRecord) {
		Response response = null;
		QuestionModel newr = service.persistQuestionModel(newRecord);  
		response = Response.ok(newr).build();
		return response;
	}
	
	@GET
	@RolesAllowed({"USER_ROLE", "ADMIN_ROLE"})
	@Path("/{" + "id" + "}")
	public Response getQuestionRecordById(@PathParam("id") int id) {
		Response response = null;
		QuestionModel record = null;

		if (sc.isCallerInRole("ADMIN_ROLE")) {
			record = service.getQuestionModelById(id);
			response = Response.status(record == null ? Status.NOT_FOUND : Status.OK).entity(record).build();
		} else {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		return response;
	}

	
	@DELETE
	@RolesAllowed({"ADMIN_ROLE"})
	public Response deleteQuestionRecordById(int id) {
		Response response = null;
		QuestionModel record = service.getQuestionModelById(id);
		service.deleteQuestionModelById(id);
		
		record = service.getQuestionModelById(id);
		response = Response.status(record == null ? Status.OK : Status.NOT_MODIFIED).entity(record).build();
		return response;
	}


}
