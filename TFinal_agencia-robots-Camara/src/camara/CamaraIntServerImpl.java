package camara;

import corba.camara.IPYPortD;
import corba.camara.ListaSuscripcionD;
import corba.camara.suscripcionD;
import corba.consola.ConsolaIntHelper;
import corba.instantanea.EstadoRobotD;
import corba.instantanea.EstadoRobotDHolder;
import corba.instantanea.InstantaneaD;
import corba.khepera.escenario.EscenarioD;
import corba.robot.RobotSeguidorInt;
import corba.robot.RobotSeguidorIntHelper;
import khepera.escenario.Escenario;

import java.util.Iterator;
import java.util.LinkedList;

import org.omg.PortableServer.POA;

import comm.Difusion;

/**
 * This class is the implemetation object for your IDL interface.
 *
 * Let the Eclipse complete operations code by choosing 'Add unimplemented methods'.
 */
public class CamaraIntServerImpl extends corba.camara.CamaraIntPOA {
	
	   private org.omg.PortableServer.POA poa_;
	   private org.omg.CORBA.ORB orb_;	   
	   private LinkedList<String> listaRobots = new LinkedList<String>();
	   private LinkedList<EstadoRobotD> listaEstados = new LinkedList<EstadoRobotD>();
	   private LinkedList<String> listaConsolas = new LinkedList<String>();
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
	   public synchronized suscripcionD SuscribirRobot(String IORrob) 
	   { 
		   if( !listaRobots.contains( IORrob) )
		   {
			   listaRobots.add(IORrob);
			   nrobots++;
		   }
		   suscripcionD sus = new suscripcionD( nrobots, ipyport, escenario.toEscenarioD());
		   System.out.println( "A�adido robot con IOR: " + IORrob );
		   return sus;
	   }
	   
	   @Override
	   public synchronized suscripcionD SuscribirConsola(String IORcons) 
	   {
		   if( !listaRobots.contains( IORcons ) )
		   {
			   listaRobots.add(IORcons);
			   nconsolas++;
		   }
		   suscripcionD sus = new suscripcionD(nconsolas, ipyport, escenario.toEscenarioD());
		   System.out.println( "A�adida consola con IOR: " + IORcons );
		   return sus;
	   }

	   @Override
	   public synchronized void BajaRobot(String IORrob) 
	   {
			   listaRobots.remove( IORrob );
			   System.out.println( "Eliminado robot con IOR: " + IORrob );
			   nrobots--;
	   }
	   
	   @Override
	   public synchronized void BajaConsola(String IORcons) 
	   {
			   listaRobots.remove( IORcons );
			   System.out.println( "Eliminada consola con IOR: " + IORcons );
			   nrobots--;
	   }

	   @Override
	   public synchronized ListaSuscripcionD ObtenerLista() 
	   {
		   String[] IORrobots = (String[]) listaRobots.toArray(new String[0]);		   
		   String[] IORconsolas = (String[]) listaConsolas.toArray(new String[0]);
		   ListaSuscripcionD listaSus = new ListaSuscripcionD( IORrobots, IORconsolas );
		   return listaSus;
	   }
	   
	   @Override
	   public IPYPortD ObtenerIPYPortDifusion() 
	   {
		   return ipyport;
	   }

	   @Override
	   public synchronized InstantaneaD ObtenerInstantanea() 
	   {
		   InstantaneaD instantanea = new InstantaneaD( (EstadoRobotD[]) listaEstados.toArray( new EstadoRobotD[0] ) );
		   return instantanea;
	   }

	   @Override
	   public void ModificarEscenario(EscenarioD esc)
	   {
		   escenario = new Escenario(esc);
		   modificarEscenario = true;
	   }

	   @Override
	   public EscenarioD ObtenerEscenario() 
	   {
		   return escenario.toEscenarioD();
	   }
	   	   
	   public void start()
	   {
		   new CamaraDifusion( ipyport );
	   }
	   
	   private void CambiarEscenario()
	   {
		   LinkedList<String> listaFallosRobots = new LinkedList<String>();
		   LinkedList<String> listaFallosConsola = new LinkedList<String>();
		   LinkedList<String> listaRobs = (LinkedList<String>) listaRobots.clone();
		   LinkedList<String> listaCons = (LinkedList<String>) listaConsolas.clone();
		   // Iteramos por la lista de robots disponibles para notificarles del escenario nuevo
		   for( Iterator<String> i = listaRobs.iterator(); i.hasNext(); )
		   {
			   String ior = i.next();
			   try
			   {
				   RobotSeguidorIntHelper.narrow( orb_.string_to_object( ior ) ).ModificarEscenario( escenario.toEscenarioD() );
			   } catch( Exception e )
			   {
				   System.out.println( "- Detectado fallo Robot: " + ior );
				   listaFallosRobots.add( ior );
			   }
		   }
		   // Iteramos por la lista de robots ca�dos para eliminarlos
		   for( Iterator<String> i = listaFallosRobots.iterator(); i.hasNext(); )
		   {
			   String ior = i.next();
			   BajaRobot( ior );
		   }
		   
		   // Iteramos por la lista de consolas disponibles para notificarles del escenario nuevo
		   for( Iterator<String> i = listaCons.iterator(); i.hasNext(); )
		   {
			   String ior = i.next();
			   try
			   {
				   ConsolaIntHelper.narrow( orb_.string_to_object( ior ) ).ModificarEscenario( escenario.toEscenarioD() );
			   } catch( Exception e )
			   {
				   System.out.println( "- Detectado fallo Consola: " + ior );
				   listaFallosConsola.add( ior );
			   }
		   }
		   // Iteramos por la lista de consolas ca�das para eliminarlas
		   for( Iterator<String> i = listaFallosConsola.iterator(); i.hasNext(); )
		   {
			   String ior = i.next();
			   BajaConsola( ior );
		   }
		   
		   modificarEscenario = false;
	   }
	   
	   class CamaraDifusion extends Thread
	   {
		   private Difusion difusion;
		   private String ior;
		   private LinkedList<String> listaFallos = new LinkedList<String>();
		   EstadoRobotDHolder st = new EstadoRobotDHolder();
		   
		   public CamaraDifusion( IPYPortD iport )
		   {
			   difusion = new Difusion(iport);
		   }
		   
		   public void run()
		   {
			   if( modificarEscenario )
				   CambiarEscenario();
			   
			   listaEstados.clear();
			   listaFallos.clear();
			   LinkedList<String> listaRobs = (LinkedList<String>)listaRobots.clone();
			   for( Iterator<String> i = listaRobs.iterator(); i.hasNext(); )
			   {
				   try
				   {
					   ior = i.next();
					   org.omg.CORBA.Object obj = orb_.string_to_object(ior);
					   RobotSeguidorInt robot = corba.robot.RobotSeguidorIntHelper.narrow(obj);
					   robot.ObtenerEstado(st);
					   listaEstados.addFirst(st.value);
				   } catch( org.omg.CORBA.COMM_FAILURE e )
				   {
					   System.out.println( "- Detectado fallo Robot: " + ior );
					   listaFallos.add(ior);
				   }
			   }
			   // Iteramos por la lista de robots ca�dos para eliminarlos
			   for( Iterator<String> i = listaFallos.iterator(); i.hasNext(); )
			   {
				   ior = (String) i.next();
				   BajaRobot(ior);
			   }				   
			   instantanea = new InstantaneaD( (EstadoRobotD[]) listaEstados.toArray( new EstadoRobotD[0] ) );
			   difusion.sendObject( instantanea );
		   }
	   }
}