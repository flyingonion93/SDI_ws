package comm;

import java.io.*;
import java.net.*;

import corba.camara.IPYPortD;

public class Difusion{

  MulticastSocket socket;
  corba.camara.IPYPortD ipyport;
  public InetAddress group;

  public  Difusion(IPYPortD ipyport){
    this.ipyport = ipyport; 

    try {
		socket = new MulticastSocket(ipyport.port);
		group = InetAddress.getByName("228.1.1.1");
		socket.joinGroup(group);
	} catch (IOException e) {
		e.printStackTrace();
	}
    
  }

//------------------------------------------------------------------------------
  public Object receiveObject(){

    Object object = null;
    ObjectInputStream ois = null;
    byte[] buffer;
    DatagramPacket packet;
    ByteArrayInputStream bis;
    try {
    	buffer = new byte[4096];
    	packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		buffer = packet.getData();
		bis = new ByteArrayInputStream(buffer);
	    ois = new ObjectInputStream(bis);
	    object = ois.readObject();
	} catch (Exception e) {
		e.printStackTrace();
	}
    
    return object;
  }

//------------------------------------------------------------------------------
  public void sendObject(Object object){
    ByteArrayOutputStream bos;
    ObjectOutputStream oos = null;
    byte[] buffer;
    DatagramPacket packet; 
    try {
    	bos = new ByteArrayOutputStream();
    	oos = new ObjectOutputStream(bos);
    	oos.writeObject(object);
    	buffer = bos.toByteArray();
    	packet = new DatagramPacket(buffer, buffer.length, group, 1110);
		socket.send(packet);
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}

// http://stackoverflow.com/questions/3736058/java-object-to-byte-and-byte-to-object-converter-for-tokyo-cabinet