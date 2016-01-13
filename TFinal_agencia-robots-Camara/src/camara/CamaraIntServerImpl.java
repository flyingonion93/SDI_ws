package camara;

import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.Iterator;

import comm.Difusion;
import corba.camara.IPYPortD;
import corba.camara.ListaSuscripcionD;
import corba.camara.suscripcionD;
import corba.instantanea.EstadoRobotD;
import corba.instantanea.EstadoRobotDHolder;
import corba.instantanea.InstantaneaD;
import corba.khepera.escenario.EscenarioD;
import corba.robot.RobotSeguidorInt;
import corba.robot.RobotSeguidorIntHelper;
import khepera.escenario.Escenario;

/**
 * This class is the implemetation object for your IDL interface.
 *
 * Let the Eclipse complete operations code by choosing 'Add unimplemented methods'.
 */
public class CamaraIntServerImpl extends corba.camara.CamaraIntPOA {
	/**
	 * Constructor for CamaraIntServerImpl 
	 */

	   private org.omg.PortableServer.POA poa_;
	   private org.omg.CORBA.ORB orb_;

	   private ConcurrentLinkedQueue<String> listaRobots = new ConcurrentLinkedQueue<String>();
	   private ConcurrentLinkedQueue<EstadoRobotD> listaEstados = new ConcurrentLinkedQueue<EstadoRobotD>();
	   private ConcurrentLinkedQueue<String> listaConsolas = new ConcurrentLinkedQueue<String>();
	   InstantaneaD instantanea;
	   private int nrobots;
	   private int nconsolas;
	   private IPYPortD ipyport;
	   private Escenario escenario=new Escenario();
	   private boolean escMod = false;
	public CamaraIntServerImpl(org.omg.CORBA.ORB orb, org.omg.PortableServer.POA poa, IPYPortD iport) {

        orb_ = orb;
        poa_ = poa;
        ipyport = new IPYPortD(iport.ip, iport.port);
        
        nrobots = 0;
        nconsolas = 0;
	}
	
	public org.omg.PortableServer.POA _default_POA()
	  {
	    if (poa_ != null) {
	      return poa_;
	    }
	    return super._default_POA();
	  }
	  

	@Override
	public synchronized suscripcionD SuscribirRobot(String IORrob) {
		// TODO Auto-generated method stub
		if (!listaRobots.contains(IORrob))
	    {
	      this.listaRobots.add(IORrob);
	      this.nrobots += 1;
	    }
	    System.out.println("Suscrito Robot: " + IORrob);
	    return new suscripcionD(nrobots - 1, ipyport, escenario.toEscenarioD());
	}

	@Override
	public synchronized suscripcionD SuscribirConsola(String IORcons) {
		// TODO Auto-generated method stub
		
			listaConsolas.add(IORcons);
			nconsolas += 1;
			System.out.println("Consola Suscrita " + IORcons);
		
			return new suscripcionD(nconsolas,ipyport,escenario.toEscenarioD());
	}

	@Override
	public synchronized void BajaRobot(String IORrob) {
		// TODO Auto-generated method stub
		if(listaRobots.contains(IORrob)){
		listaRobots.remove(IORrob);
		System.out.println("Baja Robot " +IORrob);
		}else
			System.out.println("Error al dar de baja el Robot "+IORrob);
	}

	@Override
	public synchronized void BajaConsola(String IORcons) {
		// TODO Auto-generated method stub
	listaConsolas.remove(IORcons);
	System.out.println("Baja Consola "+IORcons);
	}

	@Override
	public synchronized ListaSuscripcionD ObtenerLista() {
		// TODO Auto-generated method stub
		String[] IORrobots =(String[])listaRobots.toArray(new String[0]);
		String[] IORconsolas =(String[])listaConsolas.toArray(new String[0]);
		ListaSuscripcionD listaSubs = new ListaSuscripcionD(IORrobots,IORconsolas);
		return listaSubs;
	}

	@Override
	public synchronized IPYPortD ObtenerIPYPortDifusion() {
		// TODO Auto-generated method stub
		return ipyport;
	}

	@Override
	public synchronized InstantaneaD ObtenerInstantanea() {
		// TODO Auto-generated method stub
		InstantaneaD instantanea = new InstantaneaD((EstadoRobotD[])listaEstados.toArray(new EstadoRobotD[0]));
		
		return instantanea;
	}

	@Override
	public synchronized void ModificarEscenario(EscenarioD esc) {
		// TODO Auto-generated method stub
		escenario = new Escenario(esc);
				
		for(Iterator<String> i= listaRobots.iterator();i.hasNext();){
			RobotSeguidorIntHelper.narrow(orb_.string_to_object((String)i.next())).ModificarEscenario(esc);
		}
	}

	@Override
	public synchronized EscenarioD ObtenerEscenario() {
		// TODO Auto-generated method stub
		return escenario.toEscenarioD();
	}
	public void start(){
        new CamaraDifusion(ipyport).start();
    }

    //------------------------------------------------------------------------------
    // CamaraDifusion
    //------------------------------------------------------------------------------
    
	class CamaraDifusion extends Thread{
     private Difusion difusion;
     

      public CamaraDifusion(IPYPortD iport){
         difusion = new Difusion(iport);
      }


      public void run(){
        corba.instantanea.EstadoRobotDHolder st = new EstadoRobotDHolder();
        String ior=null;
        ConcurrentLinkedQueue<String> listaFallos = new ConcurrentLinkedQueue<String>();

         while(true){
           listaEstados.clear();
           listaFallos.clear();
           for (Iterator<String> i = listaRobots.iterator(); i.hasNext();
        		   ){
             try {
            	ior=(String)i.next();
	        	org.omg.CORBA.Object obj = orb_.string_to_object(ior);
	        	RobotSeguidorInt robot = corba.robot.RobotSeguidorIntHelper.narrow(obj);
	        	robot.ObtenerEstado(st);
	            listaEstados.add(st.value);
	            //ior=(String)i.next();
             } catch (org.omg.CORBA.COMM_FAILURE  e){
                 System.out.println("Detectado fallo Robot: " + ior );
          
                 listaFallos.add(ior);
                 //listaRobots.remove(ior);
                 
            } 
          }
           listaRobots.removeAll(listaFallos);
           
           instantanea = new InstantaneaD((EstadoRobotD[])listaEstados.toArray(new EstadoRobotD[0])); 
           
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