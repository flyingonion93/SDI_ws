package camara;

import corba.camara.IPYPortD;
import corba.camara.ListaSuscripcionD;
import corba.camara.suscripcionD;
import corba.instantanea.EstadoRobotD;
import corba.instantanea.InstantaneaD;
import corba.khepera.escenario.EscenarioD;
import khepera.escenario.Escenario;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.omg.PortableServer.POA;

/**
 * This class is the implemetation object for your IDL interface.
 *
 * Let the Eclipse complete operations code by choosing 'Add unimplemented methods'.
 */
public class CamaraIntServerImpl extends corba.camara.CamaraIntPOA {
	
	   private org.omg.PortableServer.POA poa_;
	   private org.omg.CORBA.ORB orb_;	   
	   private ConcurrentLinkedDeque<String> listaRobots = new ConcurrentLinkedDeque<String>();
	   private ConcurrentLinkedDeque<EstadoRobotD> listaEstados = new ConcurrentLinkedDeque<EstadoRobotD>();
	   private ConcurrentLinkedDeque<String> listaConsolas = new ConcurrentLinkedDeque<String>();
	   private Escenario escenario = new Escenario();
	   InstantaneaD instantanea;
	   private int nrobots;
	   private int nconsolas;
	   private IPYPortD ipyport;
	   private boolean modificarEscenario = false;
	   
	   /**
	    * Constructor for CamaraIntServerImpl
	    */
	   public CamaraIntServerImpl(org.omg.CORBA.ORB orb, org.omg.PortableServer.POA poa, IPYPortD iport) 
	   {
		   orb_ = orb;
		   poa_ = poa;
		   ipyport = new IPYPortD( iport.ip, iport.port );
		   nrobots = 0;
		   nconsolas = 0;
		   
	   }
	   
	   public POA _default_POA()
	   {
	     if (poa_ != null) {
	       return poa_;
	     }
	     return super._default_POA();
	   }
	   
	   @Override
	   public synchronized suscripcionD SuscribirRobot(String IORrob) {
		   listaRobots.add(IORrob);
		   nrobots++;
		   suscripcionD sus = new suscripcionD( nconsolas, ipyport, escenario.toEscenarioD());
		   System.out.println( "Añadido robot con IOR: " + IORrob );
		   return sus;
	   }
	   
	   @Override
	   public synchronized suscripcionD SuscribirConsola(String IORcons) {
		   listaRobots.add(IORcons);
		   nconsolas++;
		   suscripcionD sus = new suscripcionD(nconsolas, ipyport, escenario.toEscenarioD());
		   System.out.println( "Añadida consola con IOR: " + IORcons );
		   return sus;
	   }

	   @Override
	   public synchronized void BajaRobot(String IORrob) {
		   if( listaRobots.contains(IORrob) )
		   {
			   listaRobots.remove( IORrob );
			   System.out.println( "Eliminado robot con IOR: " + IORrob );
			   nrobots--;
		   }
	   }
	   
	   @Override
	   public synchronized void BajaConsola(String IORcons) {
		   if( listaRobots.contains(IORcons) )
		   {
			   listaRobots.remove( IORcons );
			   System.out.println( "Eliminado robot con IOR: " + IORcons );
			   nrobots--;
		   }
	   }

	   @Override
	   public synchronized ListaSuscripcionD ObtenerLista() {
		   String[] IORrobots = (String[]) listaRobots.toArray(new String[0]);		   
		   String[] IORconsolas = (String[]) listaConsolas.toArray(new String[0]);
		   ListaSuscripcionD listaSus = new ListaSuscripcionD( IORrobots, IORconsolas );
		   return listaSus;
	   }
	   
	   @Override
	   public IPYPortD ObtenerIPYPortDifusion() {
		   return ipyport;
	   }

	   @Override
	   public synchronized InstantaneaD ObtenerInstantanea() {
		   InstantaneaD instantanea = new InstantaneaD((EstadoRobotD[])listaEstados.toArray( new EstadoRobotD[0]));
		   return instantanea;
	   }

	   @Override
	   public void ModificarEscenario(EscenarioD esc) {
		   escenario = new Escenario(esc);
		   modificarEscenario = true;
	   }
	   
	   private void modesc()
	   {
		//TODO   
	   }

	   @Override
	   public EscenarioD ObtenerEscenario() {
		   return escenario.toEscenarioD();
	   }
	   
	   public void start()
	   {
		   
	   }
	   
	   class CamaraDifusion
	   {
		   
	   }
}