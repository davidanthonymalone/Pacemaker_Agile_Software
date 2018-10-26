package controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.PacemakerAPI;
import models.Activity;
import models.Location;
import models.User;
import utils.Serializer;
import utils.XMLSerializer;

import static models.Fixtures.users;
import static models.Fixtures.activities;
import static models.Fixtures.locations;

public class PacemakerAPITest
{
  private PacemakerAPI pacemaker;
  void populate (PacemakerAPI pacemaker)
  {  
    for (User user : users)
    {
      pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
    }
    User user1 = pacemaker.getUserByEmail(users[0].email);
    Activity activity = pacemaker.createActivity(user1.id, activities[0].type, activities[0].location, activities[0].distance);
    pacemaker.createActivity(user1.id, activities[1].type, activities[1].location, activities[1].distance);
    User user2 = pacemaker.getUserByEmail(users[1].email);
    pacemaker.createActivity(user2.id, activities[2].type, activities[2].location, activities[2].distance);
    pacemaker.createActivity(user2.id, activities[3].type, activities[3].location, activities[3].distance);

    for (Location location : locations)
    {
      pacemaker.addLocation(activity.id, location.latitude, location.longitude);
    }
  }
  
  @Test
  public void testAddActivityWithMultipleLocation()
  {
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    Long activityId = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance).id;

    for (Location location : locations)
    {
      pacemaker.addLocation(activityId, location.latitude, location.longitude);      
    }

    Activity activity = pacemaker.getActivity(activityId);
    assertEquals (locations.length, activity.route.size());
    int i = 0;
    for (Location location : activity.route)
    {
      assertEquals(location, locations[i]);
      i++;
    }
  }
  
  @Test
  public void testAddActivityWithSingleLocation()
  {
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    Long activityId = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance).id;

    pacemaker.addLocation(activityId, locations[0].latitude, locations[0].longitude);

    Activity activity = pacemaker.getActivity(activityId);
    assertEquals (1, activity.route.size());
    assertEquals(0.0001, locations[0].latitude,  activity.route.get(0).latitude);
    assertEquals(0.0001, locations[0].longitude, activity.route.get(0).longitude);   
  }

  @BeforeEach
  public void setup()
  {
    pacemaker = new PacemakerAPI(null);
    for (User user : users)
    {
      pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
    }
  }

  @AfterEach
  public void tearDown()
  {
    pacemaker = null;
  }

  @Test
  public void testUser()
  {
    assertEquals (users.length, pacemaker.getUsers().size());
    pacemaker.createUser("homer", "simpson", "homer@simpson.com", "secret");
    assertEquals (users.length+1, pacemaker.getUsers().size());
    assertEquals (users[0], pacemaker.getUserByEmail(users[0].email));
  }  
  
  @Test
  public void testAddActivity()
  {
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    Activity activity = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance);
    Activity returnedActivity = pacemaker.getActivity(activity.id);
    assertEquals(activities[0],  returnedActivity);
    assertNotSame(activities[0], returnedActivity);
  }

  @Test
  public void testUsers()
  {
    assertEquals (users.length, pacemaker.getUsers().size());
    for (User user: users)
    {
      User eachUser = pacemaker.getUserByEmail(user.email);
      assertEquals (user, eachUser);
      assertNotSame(user, eachUser);
    }
  }

  @Test
  public void testEquals()
  {
    User homer = new User ("homer", "simpson", "homer@simpson.com",  "secret");
    User homer2 = new User ("homer", "simpson", "homer@simpson.com",  "secret"); 
    User bart   = new User ("bart", "simpson", "bartr@simpson.com",  "secret"); 

    assertEquals(homer, homer);
    assertEquals(homer, homer2);
    assertNotEquals(homer, bart);    
  }    
  
  

  @Test
  public void testPopulate()
  { 
    pacemaker = new PacemakerAPI(null);

    assertEquals(0, pacemaker.getUsers().size());
    populate (pacemaker);

    assertEquals(users.length, pacemaker.getUsers().size());
    assertEquals(2, pacemaker.getUserByEmail(users[0].email).activities.size());
    assertEquals(2, pacemaker.getUserByEmail(users[1].email).activities.size());   
    Long activityID = pacemaker.getUserByEmail(users[0].email).activities.keySet().iterator().next();
    assertEquals(locations.length, pacemaker.getActivity(activityID).route.size());   
  }
  
  
  void deleteFile(String fileName)
  {
    File datastore = new File ("testdatastore.xml");
    if (datastore.exists())
    {
      datastore.delete();
    }
  }
  
  @Test
  public void testXMLSerializer() throws Exception
  { 
    String datastoreFile = "testdatastore.xml";
    deleteFile (datastoreFile);

    Serializer serializer = new XMLSerializer(new File (datastoreFile));

    pacemaker = new PacemakerAPI(serializer); 
    populate(pacemaker);
    pacemaker.store();

    PacemakerAPI pacemaker2 =  new PacemakerAPI(serializer);
    pacemaker2.load();

    assertEquals (pacemaker.getUsers().size(), pacemaker2.getUsers().size());
    for (User user : pacemaker.getUsers())
    {
      assertTrue (pacemaker2.getUsers().contains(user));
    }
    deleteFile ("testdatastore.xml");
  }
  
  
  @Test
  public void testDeleteUsers()
  {
    assertEquals (users.length, pacemaker.getUsers().size());
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    pacemaker.deleteUser(marge.id);
    assertEquals (users.length-1, pacemaker.getUsers().size());    
  }

}