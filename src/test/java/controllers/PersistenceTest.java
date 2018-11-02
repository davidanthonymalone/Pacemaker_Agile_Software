package controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import static models.Fixtures.users;

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

class PersistenceTest {

    PacemakerAPI pacemaker;
    void populate (PacemakerAPI pacemaker)
    {  
      for (var user : users)
      {
        pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
      }
      var user1 = pacemaker.getUserByEmail(users[0].email);
      var activity = pacemaker.createActivity(user1.id, activities[0].type, activities[0].location, activities[0].distance);
      pacemaker.createActivity(user1.id, activities[1].type, activities[1].location, activities[1].distance);
      var user2 = pacemaker.getUserByEmail(users[1].email);
      pacemaker.createActivity(user2.id, activities[2].type, activities[2].location, activities[2].distance);
      pacemaker.createActivity(user2.id, activities[3].type, activities[3].location, activities[3].distance);

      for (Location location : locations)
      {
        pacemaker.addLocation(activity.id, location.latitude, location.longitude);
      }
    }
    
    @Test
    public void testXMLSerializer() throws Exception
    { 
      var datastoreFile = "testdatastore.xml";
      deleteFile (datastoreFile);

      var serializer = new XMLSerializer(new File (datastoreFile));

      pacemaker = new PacemakerAPI(serializer); 
      populate(pacemaker);
      pacemaker.store();

      var pacemaker2 =  new PacemakerAPI(serializer);
      pacemaker2.load();

      assertEquals (pacemaker.getUsers().size(), pacemaker2.getUsers().size());
      for (var user : pacemaker.getUsers())
      {
        assertTrue (pacemaker2.getUsers().contains(user));
      }
      deleteFile ("testdatastore.xml");
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
      String activityID = pacemaker.getUserByEmail(users[0].email).activities.keySet().iterator().next();
      
    }

    
    void deleteFile(String fileName)
    {
      var datastore = new File ("testdatastore.xml");
      if (datastore.exists())
      {
        datastore.delete();
      }
    }

    
   
    
    

    
    
    
    
    

}