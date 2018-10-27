package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.impl.CollectionASCIITableAware;
import com.bethecoder.ascii_table.spec.IASCIITableAware;
import com.google.common.base.Optional;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.Activity;
import models.User;
import utils.BinarySerializer;
import utils.JSONSerializer;
import utils.Serializer;
import utils.XMLSerializer;


public class Main {

	PacemakerAPI paceApi;


	  

	public Main() throws Exception
	{
		
		File  datastore = new File("datastore.json");
		Serializer serializer = new JSONSerializer(datastore);
		System.out.println("this is a test");

		paceApi = new PacemakerAPI(serializer);
		if (datastore.isFile())
		{
			paceApi.load();
		}
	}

	@Command(description = "Create a new User")
	public void createUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
			@Param(name = "email") String email, @Param(name = "password") String password) {
		paceApi.createUser(firstName, lastName, email, password);
	}
	
	

	@Command(description = "Get a Users details by Email")
	public void listUserEmail(@Param(name = "email") String email) {
		
		User user = paceApi.getUserByEmail(email);
		System.out.println(user);
	}
	
	@Command(description = "Get an Activity by ID")
	public void listActivity(@Param(name = "id") String id) {
		Activity activity = paceApi.getActivityById(id);
		System.out.println(activity);
	}
	
	
	@Command(description = "Get a Users details by ID")
	public void listUserId(@Param(name = "id") String id) {
		User user = paceApi.getUserById(id);
		System.out.println(user);
	}

	  @Command(description="Get all users details")
	  public void getUsers ()
	  {
	    List<User> userList = new ArrayList<User> (paceApi.getUsers());
	    IASCIITableAware asciiTableAware = new CollectionASCIITableAware<User>(userList, "firstname", "lastname", "email","id"); 
	    ASCIITable.getInstance().printTable(asciiTableAware);
	  }

	@Command(description = "Delete a User")
	public void deleteUser(@Param(name = "email") String email) {
		Optional<User> user = Optional.fromNullable(paceApi.getUserByEmail(email));
		if (user.isPresent()) {
			paceApi.deleteUser(user.get().id);
		}
	}

	@Command(description = "Add an activity")
	public void addActivity(@Param(name = "user-id") String id, @Param(name = "type") String type,
			@Param(name = "location") String location, @Param(name = "distance") double distance) {
		Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
		if (user.isPresent()) {
			paceApi.createActivity(id, type, location, distance);
		}
		
	}

	@Command(description = "Add Location to an activity")
	public void addLocation(@Param(name = "activity-id") String id, @Param(name = "latitude") float latitude,
			@Param(name = "longitude") float longitude) {
		Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(id));
		if (activity.isPresent()) {
			paceApi.addLocation(activity.get().id, latitude, longitude);
		}
	}

	public static void main(String[] args) throws Exception {
		Main main = new Main();

		Shell shell = ShellFactory.createConsoleShell("Input:","Welcome to pacemaker-console - ?help for instructions", main);
		shell.commandLoop();

		main.paceApi.store();

	}
}
