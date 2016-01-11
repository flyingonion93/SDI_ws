package camara;

import comm.*;
import corba.instantanea.*;
import corba.camara.*;
import corba.robot.*;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;


public class CamaraIntServerImpl extends corba.camara.CamaraIntPOA {

   private org.omg.PortableServer.POA poa_;
   private org.omg.CORBA.ORB orb_;

   private ConcurrentLinkedDeque<String> listaRobots = new ConcurrentLinkedDeque<String>();
   private ConcurrentLinkedDeque<EstadoRobotD> listaEstados = new ConcurrentLinkedDeque<EstadoRobotD>();
   InstantaneaD instantanea;
   private int nrobots;
   private IPYPortD ipyport;

    public

    CamaraIntServerImpl(org.omg.CORBA.ORB orb, org.omg.PortableServer.POA poa, IPYPortD iport) 
    {
        orb_ = orb;
        poa_ = poa;
        ipyport = new IPYPortD(iport.ip, iport.port);
        
        nrobots = 0;
    }


    public org.omg.PortableServer.POA
    _default_POA()
    {
        if(poa_ != null)
            return poa_;
        else
            return super._default_POA();
    }

    //
    // IDL:corba/Camara/CamaraInt/SuscribirRobot:1.0
    //
    public synchronized suscripcionD
    SuscribirRobot(String IORrob)
    {
       //EJERCICIO: Implementar la suscripcion al robot
    	listaRobots.add(IORrob);
    	nrobots++;
    	suscripcionD sus = new suscripcionD(nrobots, ipyport);
    	System.out.println("+ A�adido robot con IOR: " + IORrob);
		return sus;
    }
    
    public void start(){
        new CamaraDifusion(ipyport).start();
    }

    //------------------------------------------------------------------------------
    // La clase anidada CamaraDifusion
    //------------------------------------------------------------------------------
    class CamaraDifusion extends Thread{
     private Difusion difusion;
     
      //------------------------------------------------------------------------------
      public CamaraDifusion(IPYPortD iport){
         difusion = new Difusion(iport);
      }

      //------------------------------------------------------------------------------
      public void run(){
        corba.instantanea.EstadoRobotDHolder st = new EstadoRobotDHolder();
        String ior=null;
        LinkedList<String> listaFallos = new LinkedList<String>();
         while(true){
           listaEstados.clear();
           listaFallos.clear();
           for (Iterator<String> i = listaRobots.iterator(); i.hasNext();){
        	   try {
        		   
                //EJERCICIO: invocar via CORBA el metodo ObtenerEstado y anyadir
               //el estado del robot correspondiente a la lista de estados
        		   
            	 ior = (String) i.next(); //Obtener el identificador del siguiente robot
            	 org.omg.CORBA.Object obj = orb_.string_to_object(ior); //Obtener la referencia remota a partir de su identificador
            	 RobotSeguidorInt robot = corba.robot.RobotSeguidorIntHelper.narrow(obj); //Crear el objeto robot
            	 robot.ObtenerEstado(st); //Obtener el estado del robot
            	 listaEstados.add(st.value); //A�adir el estado el robot a la lista de estados
            	 
             } catch (/*EJERCICIO: Seleccionar excepcion */ org.omg.CORBA.COMM_FAILURE e){
                 System.out.println("- Detectado fallo Robot: " + ior );
               //EJERCICIO: anyadir el robot caido a la lista de fallos 
                 listaFallos.add(ior); //A�adir la referencia del robot ca�do porque no se puede obtener su estado
            }
           }
           
           listaRobots.removeAll(listaFallos);
           
           //EJERCICIO: crear una instantanea a partir de la lista de estados de los robots. 
           instantanea = new InstantaneaD(/*EJERCICIO*/(EstadoRobotD[]) listaEstados.toArray(new EstadoRobotD[0])); 
           
           //EJERCICIO: difundir la instantanea 
           difusion.sendObject(instantanea);
           
           try{
               Thread.sleep(400);
           }catch(InterruptedException e){
               e.printStackTrace();
           }
        }
      }
    }
}