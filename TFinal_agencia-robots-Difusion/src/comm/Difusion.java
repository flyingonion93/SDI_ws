package comm;

import java.io.*;
import java.net.*;

import corba.camara.IPYPortD;

public class Difusion
{

  MulticastSocket socket;
  IPYPortD ipyport;
  public InetAddress group;

  public  Difusion(IPYPortD ipyport)
  {
	  this.ipyport = ipyport;
	  try
	  {
		  socket = new MulticastSocket( this.ipyport.port );
	  } catch( IOException e )
	  {
		  e.printStackTrace();
	  }
	  try
	  {
		  group = InetAddress.getByName( this.ipyport.ip);		  
	  } catch( IOException e )
	  {
		  e.printStackTrace();
	  }
	  try
	  {
		  socket.joinGroup(group);
	  } catch (IOException e) 
	  {
		  	e.printStackTrace();
	  }
  }

//------------------------------------------------------------------------------
  public Object receiveObject()
  {
	  Object object = null;
	  ObjectInputStream ois = null;
	  byte[] buffer;
	  DatagramPacket packet;
	  ByteArrayInputStream bis;
	  buffer = new byte[20000];
	  packet = new DatagramPacket( buffer, buffer.length );	  
	  try 
	  {
		  socket.receive(packet);
	  }catch( IOException e )
	  {
		  e.printStackTrace();
	  }
	  buffer = packet.getData();
	  bis = new ByteArrayInputStream( buffer );
	  try
	  {
		  ois = new ObjectInputStream( bis );
	  } catch( IOException e )
	  {
		  e.printStackTrace();
	  }
	  try
	  {
		  object = (Object) ois.readObject();
	  } catch( IOException e )
	  {
		  e.printStackTrace();
	  } catch ( ClassNotFoundException e )
	  {
		  e.printStackTrace();
	  }
	  return object;
  }

//------------------------------------------------------------------------------
  public void sendObject(Object object)
  {
	  ByteArrayOutputStream bos = new ByteArrayOutputStream();
	  ObjectOutputStream oos = null;
	  byte[] buffer = null;
	  DatagramPacket packet = null;	  
	  try
	  {
		  oos = new ObjectOutputStream( bos );
	  } catch( IOException e )
	  {
		  e.printStackTrace();
	  }
	  try
	  {
		  oos.writeObject( object );
	  } catch( IOException e )
	  {
		  e.printStackTrace();
	  }
	  buffer = bos.toByteArray();
	  packet = new DatagramPacket( buffer, buffer.length, ipyport.port );
	  try
	  {
		  socket.send( packet );
	  } catch( IOException e )
	  {
		  e.printStackTrace();
	  }	  
  }
}