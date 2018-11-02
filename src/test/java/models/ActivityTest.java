package models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static models.Fixtures.activities;

class ActivityTest {

  Activity test = new Activity ("walk",  "fridge", 0.001);
  

  @Test
  public void testCreate()
  {
    assertEquals ("walk",          test.type);
    assertEquals ("fridge",        test.location);
    assertEquals (0.0001, 0.001,   test.distance);    
  }

  @Test
  public void testIds()
  {
    var ids = new HashSet<>();
    for (var activity : activities)
    {
      ids.add(activity.id);
    }
    assertEquals (activities.length, ids.size());
  }

  
  
  @Test
  public void testToString()
  {
    assertEquals ("Activity{" + test.id + ", walk, fridge, 0.001, []}", test.toString());
  }

}