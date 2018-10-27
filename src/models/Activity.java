package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Objects;

public class Activity implements Serializable
{ 

  
  public String   id;
  public String type;
  public String location;
  public double distance;
  
  public List<Location> route = new ArrayList<>();
 
  public Activity()
  {
  }
  
  public Activity(String type, String location, double distance)
  {
	this.id = UUID.randomUUID().toString();
    this.type      = type;
    this.location  = location;
    this.distance  = distance;
  }
  
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Activity)
    {
      final Activity other = (Activity) obj;
      return Objects.equal(type, other.type) 
          && Objects.equal(location,  other.location)
          && Objects.equal(distance,  other.distance)
          && Objects.equal(route,     other.route);    
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
                               .addValue(type)
                               .addValue(location)
                               .addValue(distance)
                               .addValue(route)
                               .toString();
  }
  
  public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public double getDistance() {
	return distance;
}

public void setDistance(double distance) {
	this.distance = distance;
}

public List<Location> getRoute() {
	return route;
}

public void setRoute(List<Location> route) {
	this.route = route;
}

@Override  
  public int hashCode()  
  {  
     return Objects.hashCode(this.id, this.type, this.location, this.distance);  
  } 

}