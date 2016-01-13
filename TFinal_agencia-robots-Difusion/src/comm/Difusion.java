package comm;

import java.io.*;
import java.net.*;
import corba.camara.IPYPortD;

public class Difusion{

  MulticastSocket socket;
  corba.camara.IPYPortD ipyport;
  public InetAddress group;

//------------------------------------------------------------------------------
  public  Difusion(IPYPortD ipyport){
	  this.ipyport = ipyport;
	  
	  
	  try {
		  	//EJERCICIO: 
		  	//Obtener la direccion del grupo 
			group = InetAddress.getByName(ipyport.ip);
			//EJERCICIO: 
			//Crear el socket multicast 
			socket = new MulticastSocket(ipyport.port);
			//EJERCICIO: 
			//Unirse al grupo
			socket.joinGroup(group);

	  } catch (UnknownHostException e2) {
			e2.printStackTrace();
			return;
	  } catch (IOException e1) {
			e1.printStackTrace();
			return;
	  }

  }

//------------------------------------------------------------------------------
  public Object receiveObject(){

    Object object = null;
    ObjectInputStream ois = null;
    byte[] buffer = new byte[80000];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    ByteArrayInputStream bis;


  //EJERCICIO: recibir el paquete y deserializarlo 
    //pot ser que faja falta un bucle?
    
    try {
    	socket.receive(packet);
    	buffer = packet.getData();
        bis = new ByteArrayInputStream(buffer);
		ois = new ObjectInputStream(bis);
		object = ois.readObject();
	}catch (IOException e) {
		e.printStackTrace();		
	}catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
    return object;
  }

//------------------------------------------------------------------------------
  public void sendObject(Object object){

    ByteArrayOutputStream bos;
    ObjectOutputStream oos = null;
    byte[] buffer = new byte[4096];
    DatagramPacket packet = null;

  //EJERCICIO: serializar el paquete y difundirlo
    //y asi altre bucle infinit?
    bos = new ByteArrayOutputStream(buffer.length);
    try {
    	//contruir el byteArray en el buffer, contruir el objectOutputStream y escribirle el object
    	bos = new ByteArrayOutputStream(buffer.length);
		oos = new ObjectOutputStream(bos);
		oos.writeObject(object);
		oos.flush();
		buffer = bos.toByteArray();
	    packet = new DatagramPacket(buffer, buffer.length, group,ipyport.port);
	    socket.send(packet);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}