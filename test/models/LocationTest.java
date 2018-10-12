package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationTest {

    private Location one;
    private Location two;

    @BeforeEach
    public void setup()
    {
      one = new Location(23.3f, 33.3f);
      two = new Location(34.4f, 22.2f);
    }

    @AfterEach
    public void tearDown()
    {
      one = two = null;
    }

    @Test
    public void testCreate()
    {
      assertEquals (23.3, one.latitude, 0.01);
      assertEquals (33.3, one.longitude, 0.01);
    }

    @Test
    public void testIds()
    {
      assertNotEquals(one.id, two.id);
    }

    @Test
    public void testToString()
    {
      assertEquals ("Location{2, 23.3, 33.3}", one.toString());
    }

}