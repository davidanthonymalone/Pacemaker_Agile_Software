package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.io.Serializable;
import java.util.UUID;

import com.google.common.base.Objects;

public class Location implements Serializable
{
  public static Long   counter = 0l;
  
  public String  id;
  public float latitude;
  public float longitude;
  
  public Location()
  {
  }
  
  public Location (float latitude, float longitude)
  {
	this.id = UUID.randomUUID().toString();
    this.latitude  = latitude;
    this.longitude = longitude;
  }
  
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Location)
    {
      final Location other = (Location) obj;
      return Objects.equal(latitude, other.latitude) 
          && Objects.equal(longitude, other.longitude);
    }
    else
    {
      return false;
    }
  }
  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(id)
                               .addValue(latitude)
                               .addValue(longitude)                              
                               .toString();
  }
  
  public static Long getCounter() {
	return counter;
}

public static void setCounter(Long counter) {
	Location.counter = counter;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public float getLatitude() {
	return latitude;
}

public void setLatitude(float latitude) {
	this.latitude = latitude;
}

public float getLongitude() {
	return longitude;
}

public void setLongitude(float longitude) {
	this.longitude = longitude;
}

@Override  
  public int hashCode()  
  {  
     return Objects.hashCode(this.id, this.latitude, this.longitude);  
  } 
}