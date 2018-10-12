package models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class UserTest {

  private User[] users =
    {
        new User ("marge", "simpson", "marge@simpson.com",  "secret"),
        new User ("lisa",  "simpson", "lisa@simpson.com",   "secret"),
        new User ("bart",  "simpson", "bart@simpson.com",   "secret"),
        new User ("maggie","simpson", "maggie@simpson.com", "secret")
    };
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
    Set<Long> ids = new HashSet<>();
    for (User user : users)
    {
      ids.add(user.id);
    }
    assertEquals (users.length, ids.size());
  }

  @Test
  public void testToString()
  {
    assertEquals ("User{homer, simpson, homer@simpson.com, secret, {}}", homer.toString());
  }

}