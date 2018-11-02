package models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static models.Fixtures.users;

class UserTest {

  User homer = new User ("homer", "simpson", "homer@simpson.com",  "secret");

  @Test
  public void testCreate()
  {
    assertEquals ("homer",               homer.firstName);
    assertEquals ("simpson",             homer.lastName);
    assertEquals ("homer@simpson.com",   homer.email);   
    assertEquals ("secret",              homer.password);   
  }
  
  
  

  @Test
  public void testIds()
  {
    var ids = new HashSet<>();
    for (var user : users)
    {
      ids.add(user.id);
    }
    assertEquals (users.length, ids.size());
  }



}