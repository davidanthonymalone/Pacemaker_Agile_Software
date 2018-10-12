package utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

public class XMLSerializer implements Serializer
{

	private Stack stack = new Stack();
	private File file;

	public XMLSerializer(File file)
	{
		this.file = file;
	}

	public void push(Object o)
	{
		stack.push(o);
	}

	public Object pop()
	{
		return stack.pop(); 
	}

	@SuppressWarnings("unchecked")
	public void read() throws Exception
	{
		ObjectInputStream is = null;
	      Object obj = is.readObject();
	      while (obj != null)
	      {
	        stack.push(obj);
	        obj = is.readObject();
	      }

		try
		{
			//set up an array of classes that can be loaded.
			Class<?>[] classes = new Class[] { models.User.class, models.Activity.class, models.Location.class };
			
			XStream xstream = new XStream(new DomDriver());
			
			//set up permissions for loading via xstream
			XStream.setupDefaultSecurity(xstream);
			xstream.allowTypes(classes);	  
			
			is = xstream.createObjectInputStream(new FileReader(file));
			stack = (Stack) is.readObject();
		}
		finally
		{
			if (is != null)
			{
				is.close();
			}
		}
	}

	public void write() throws Exception
	{
		ObjectOutputStream os = null;
	      while (!stack.empty())
	      {
	        os.writeObject(stack.pop());  
	      }

		try
		{
			XStream xstream = new XStream(new DomDriver());
			os = xstream.createObjectOutputStream(new FileWriter(file));
			os.writeObject(stack);
		}
		finally
		{
			if (os != null)
			{
				os.close();
			}
		}
	}
}