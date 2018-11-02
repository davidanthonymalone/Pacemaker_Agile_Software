package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

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
import utils.CompareObjDistance;
import utils.CompareObjLocation;
import utils.CompareObjType;
import utils.JSONSerializer;
import utils.Serializer;
import utils.XMLSerializer;


@SuppressWarnings("fallthrough")
public class Main {

	PacemakerAPI paceApi;

	String format1="JSON";
	boolean whatever=false;
	  
	
	public Main() throws Exception
	{
	
		XMLFormat();
	
		//JSONFormat();
		
		
	}
	
    @Command(description="Get all users details")
    public void getUsers ()
    {
        var userList = new ArrayList<User> (paceApi.getUsers());
        var asciiTableAware = new CollectionASCIITableAware<User>(userList, "firstname", "lastname", "email"); 
        ASCIITable.getInstance().printTable(asciiTableAware);
    }

	  @Command(description = "Create a new User")
	    public void createUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
	            @Param(name = "email") String email, @Param(name = "password") String password) {
	        paceApi.createUser(firstName, lastName, email, password);
	    }
	  
	@Command(description = "Print Time")
	public void PrintTime() throws Exception {
		  DateTime dt = new DateTime();
		  System.out.println(dt.toString());
		

	}
	
	
	@Command(description = "Change File Format")
	public void ChangeFileFormat(@Param(name = "format") String format) throws Exception {
		format1=format;
		System.out.println(format1);
		

	}
	
	public void JSONFormat() throws Exception{

		
		var  datastore = new File("datastore.json");
		Serializer serializer = new JSONSerializer(datastore);
		paceApi = new PacemakerAPI(serializer);
		if (datastore.isFile())
		{
			paceApi.load();
		}
		
	}
	
	
	public void XMLFormat() throws Exception{
		var  datastore = new File("datastore.xml");
		Serializer serializer = new XMLSerializer(datastore);
		

		paceApi = new PacemakerAPI(serializer);
		if (datastore.isFile())
		{
			paceApi.load();
		}
		
	}
	

    @Command(description = "Get a Users details By email")
    public void getUser(@Param(name = "email") String email) {
        var user = paceApi.getUserByEmail(email);
        System.out.println(user);
    }
	
	@Command(description = "Get an Activity by ID")
	public void listActivity(@Param(name = "id") String id) {
		Activity activity = paceApi.getActivityById(id);
		System.out.println(activity);
	}
	
//	  @Command(description="list all activities of a user")
//	  public void listActivities ()
//	  {
//	    List<User> userList = new ArrayList<User> (paceApi.getUsers());
//	    IASCIITableAware asciiTableAware = new CollectionASCIITableAware<User>(userList, "firstname", "lastname", "email","id","password"); 
//	    ASCIITable.getInstance().printTable(asciiTableAware);
//	  }
	
	  @Command(description = "Get Activites by User ID")
		public void listActivities(@Param(name = "user id") String id) {
		  List<Activity> userList = new ArrayList<Activity> (paceApi.getActivities());
		    IASCIITableAware asciiTableAware = new CollectionASCIITableAware<Activity>(userList, "location","Type","distance","id","location"); 
		    ASCIITable.getInstance().printTable(asciiTableAware);
		}
	
	  @Command(description = "Sort Activities")
			public void listActivitiesSorted(@Param(name = "user id") String id,@Param(name = "sort by distance, type, location") String sortby) {
		  		List<Activity> userList = new ArrayList<Activity> (paceApi.getActivities());
		  		
		  		if(sortby.toLowerCase().equals("distance")) {
		  					Collections.sort(userList, new CompareObjDistance());
			  				IASCIITableAware asciiTableAware = new CollectionASCIITableAware<Activity>(userList, "location","Type","distance","id"); 
			  				ASCIITable.getInstance().printTable(asciiTableAware);
		  		}else if (sortby.toLowerCase().equals("type")) {	
		  					Collections.sort(userList, new CompareObjType());
		  					IASCIITableAware asciiTableAware = new CollectionASCIITableAware<Activity>(userList, "location","Type","distance","id"); 
		  					ASCIITable.getInstance().printTable(asciiTableAware);
		  			
		  		}else if (sortby.toLowerCase().equals("location")) {	
  							Collections.sort(userList, new CompareObjLocation());
  							IASCIITableAware asciiTableAware = new CollectionASCIITableAware<Activity>(userList, "location","Type","distance","id"); 
  							ASCIITable.getInstance().printTable(asciiTableAware);
  			
		  		}else {
		  			
		  					System.out.println("not a valid sorty by type");
		  		}
		  		
			    
			}
	  
	  
	@Command(description = "Get a Users details by ID")
	public void listUserId(@Param(name = "user id") String id) {
		User user = paceApi.getUserById(id);
		List<User> userList = new ArrayList<User>();
		userList.add(user);
	    IASCIITableAware asciiTableAware = new CollectionASCIITableAware<User>(userList, "firstname", "lastname", "email","id","password"); 
	    ASCIITable.getInstance().printTable(asciiTableAware);
		
		System.out.println(user);
	}

	

	  @Command(description = "Delete a User")
	    public void deleteUser(@Param(name = "email") String email) {
	        var user = Optional.fromNullable(paceApi.getUserByEmail(email));
	        if (user.isPresent()) {
	            paceApi.deleteUser(user.get().id);
	        }
	    }
	
	
	@Command(description = "Delete a User By ID")
	public void deleteUserById(@Param(name = "user id") String id) {
		Optional<User> user = Optional.fromNullable(paceApi.getUserById(id));
		if (user.isPresent()) {
			
			System.out.println("You have deleted user: " +user);
			paceApi.deleteUser(user.get().id);
		}
	}

	   @Command(description = "Add an activity")
	    public void addActivity(@Param(name = "user-id") String id, @Param(name = "type") String type,
	            @Param(name = "location") String location, @Param(name = "distance") double distance) {
	        var user = Optional.fromNullable(paceApi.getUser(id));
	        if (user.isPresent()) {
	            paceApi.createActivity(id, type, location, distance);
	        }
	    }
	
	

	


	@Command(description = "Add Location to an activity")
	public void addLocation(@Param(name = "activity-id") String id, @Param(name = "latitude") float latitude,
			@Param(name = "longitude") float longitude) {
		var activity = Optional.fromNullable(paceApi.getActivity(id));
		if (activity.isPresent()) {
			paceApi.addLocation(activity.get().id, latitude, longitude);
		}
	}

	  public static void main(String[] args) throws Exception {

	        var main = new Main();

	        var shell = ShellFactory.createConsoleShell("pm", "Welcome to pacemaker-console - ?help for instructions", main);
	        shell.commandLoop();

	        main.paceApi.store();
	    }
	
	
	@Command(description = "Report active users")
    public void reportActiveUsers() {

        var asciiTableAware = 
                //data for the table
                new CollectionASCIITableAware<User>(
                        paceApi.getUsers()
                        .stream()
                        .filter(p -> !p.getActivities().isEmpty())
                        .collect(Collectors.toList()), 
                        //properties to read (using getter methods)
                        List.of("firstname", "lastname", "email", "activitiessize"), 
                        //custom headers for the table
                        List.of("First Name", "Last Name", "Email", "Number of Activities")); 

        ASCIITable.getInstance().printTable(asciiTableAware);    
    }
	
	@Command(description = "Current Serialization Format")
    public void currentSerialisationFormat() {
        System.out.println(paceApi.getSerializer().serializerFormat());
    }
}
