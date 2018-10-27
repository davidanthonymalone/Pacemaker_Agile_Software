package controllers;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

import models.Activity;
import models.Location;
import models.User;
import utils.JSONSerializer;
import utils.Serializer;
import utils.XMLSerializer;

public class PacemakerAPI
{
	private Map<String, User>     userIndex  = new HashMap<>();
	private Map<String, User>   emailIndex      = new HashMap<>();
	private Map<String, Activity> activitiesIndex = new HashMap<>();

	private Serializer serializer;

	public PacemakerAPI(Serializer serializer)
	{
		this.serializer = serializer;
	}

	public Collection<Activity> getActivities ()
	{
		return activitiesIndex.values();
	}
	
	public Collection<User> getUsers ()
	{
		return userIndex.values();
	}

	
	
	public  void deleteUsers() 
	{
		userIndex.clear();
		emailIndex.clear();
	}

	public User createUser(String firstName, String lastName, String email, String password) 
	{
		User user = new User (firstName, lastName, email, password);
		userIndex.put(user.id, user);
		emailIndex.put(email, user);
		return user;
	}

	public User getUserByEmail(String email) 
	{
		return emailIndex.get(email);
	}
	
	public User getUserById(String id) 
	{
		return userIndex.get(id);
	}
	
	public Activity getActivityById(String id) 
	{
		return activitiesIndex.get(id);
	}
	
	public Activity getActivityByUserId(String id) 
	{
		return activitiesIndex.get(id);
	}


	  public User getUser(String id) 
	  {
	     return userIndex.get(id);
	  }

	  public void deleteUser(String id) 
	  {
	   User user = userIndex.remove(id);
	     emailIndex.remove(user.email);
	  }
	  public Activity createActivity(String id, String type, String location, double distance) 
	  {
	    Activity activity = null;
	    Optional<User> user = Optional.fromNullable(userIndex.get(id));
	    if (user.isPresent())
	    {
	      activity = new Activity (type, location, distance);
	      user.get().activities.put(activity.id, activity);
	      activitiesIndex.put(activity.id, activity);
	    }
	    return activity;
	  }
	public Activity getActivity (String id)
	{
		return activitiesIndex.get(id);
	}

	public void addLocation (String id, float latitude, float longitude)
	{
		Optional<Activity> activity = Optional.fromNullable(activitiesIndex.get(id));
		if (activity.isPresent())
		{
			activity.get().route.add(new Location(latitude, longitude));
		}
	}

	  @SuppressWarnings("unchecked")
	  public void load() throws Exception
	  {
	    serializer.read();
	    activitiesIndex = (Map<String, Activity>) serializer.pop();
	    emailIndex      = (Map<String, User>)   serializer.pop();
	    userIndex       = (Map<String, User>)   serializer.pop();
	  }

	  public void store() throws Exception
	  {
	    serializer.push(userIndex);
	    serializer.push(emailIndex);
	    serializer.push(activitiesIndex);
	    serializer.write(); 
	  }
	  
	  

}
