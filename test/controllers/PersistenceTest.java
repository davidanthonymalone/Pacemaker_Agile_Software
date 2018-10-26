package controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class PersistenceTest {

    PacemakerAPI pacemaker;
    
    void deleteFile(String fileName)
    {
      File datastore = new File ("testdatastore.xml");
      if (datastore.exists())
      {
        datastore.delete();
      }
    }
    
    
    
    
    

}