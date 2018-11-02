package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.Objects;

public class User implements Serializable
{
  public static Long counter = 0l;
  
  public String   id;
  public String firstName;
  public String lastName;
  public String email;
  public String password;
 
  public Map<String, Activity> activities = new HashMap<>();

  public User()
  {
  }
  
  public User(String firstName, String lastName, String email, String password)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.id = UUID.randomUUID().toString();
  }
  
  
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof User)
    {
      final User other = (User) obj;
      return Objects.equal(firstName, other.firstName) 
          && Objects.equal(lastName,  other.lastName)
          && Objects.equal(email,     other.email)
          && Objects.equal(password,  other.password);
    }
    else
    {
      return false;
    }
  }
  

  
  public static Long getCounter() {
	return counter;
}

public static void setCounter(Long counter) {
	User.counter = counter;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmail() {
	return email;
}

public String getFirstname() 
{
  return firstName;
}

public String getLastname() 
{
  return lastName;
}



public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public Map<String, Activity> getActivities() {
	return activities;
}

public int getActivitiessize() {
    return activities.size();
}

public void setActivities(Map<String, Activity> activities) {
	this.activities = activities;
}



  @Override  
  public int hashCode()  
  {  
     return Objects.hashCode(this.lastName, this.firstName, this.email, this.password,this.id);  
  }
  
}