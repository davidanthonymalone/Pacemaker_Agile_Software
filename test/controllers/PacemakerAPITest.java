package controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.PacemakerAPI;
import models.User;

import static models.Fixtures.users;
import static models.Fixtures.activities;
import static models.Fixtures.locations;

public class PacemakerAPITest
{
  private PacemakerAPI pacemaker;

  @BeforeEach
  public void setup()
  {
    pacemaker = new PacemakerAPI(null);
  }
  @Test
  public void testUser()
  {
    User homer = new User ("homer", "simpson", "homer@simpson.com",  "secret");

    assertEquals (0, pacemaker.getUsers().size());
    pacemaker.createUser("homer", "simpson", "homer@simpson.com", "secret");
    assertEquals (1, pacemaker.getUsers().size());

    assertEquals (homer, pacemaker.getUserByEmail("homer@simpson.com"));
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

  @AfterEach
  public void tearDown()
  {
    pacemaker = null;
  }

}