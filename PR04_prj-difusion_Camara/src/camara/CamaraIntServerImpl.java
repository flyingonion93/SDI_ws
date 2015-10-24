package camara;

import comm.*;
import corba.instantanea.*;
import corba.camara.*;
import corba.camara.suscripcionD;
import corba.camara.IPYPortD;
import corba.robot.*;
import java.util.LinkedList;
import java.util.Iterator;

public class CamaraIntServerImpl extends corba.camara.CamaraIntPOA 
{

   private org.omg.PortableServer.POA poa_;
   private org.omg.CORBA.ORB orb_;

   private LinkedList listaRobots = new LinkedList();
   private LinkedList listaEstados = new LinkedList();
   InstantaneaD instantanea;
   private int nrobots;
   private IPYPortD ipyport;

    public CamaraIntServerImpl(org.omg.CORBA.ORB orb, org.omg.PortableServer.POA poa, IPYPortD iport) 
    {
        orb_ = orb;
        poa_ = poa;
        ipyport = new IPYPortD(iport.ip, iport.port);
        
        nrobots = 0;
    }


    public org.omg.PortableServer.POA _default_POA()
    {
        if(poa_ != null)
            return poa_;
        else
            return super._default_POA();
    }

    //
    // IDL:corba/Camara/CamaraInt/SuscribirRobot:1.0
    //
    public suscripcionD SuscribirRobot(String IORrob)
    {    	
    	suscripcionD sub = null;
    	sub = new suscripcionD( nrobots, ipyport );
		listaRobots.add(IORrob);
		nrobots++;
    	return sub;
    }
    
    public void start()
    {
        new CamaraDifusion(ipyport).start();
    }

    //------------------------------------------------------------------------------
    // La clase anidada CamaraDifusion
    //------------------------------------------------------------------------------
    class CamaraDifusion extends Thread
    {
    	private Difusion difusion;
     
    	//------------------------------------------------------------------------------
    	public CamaraDifusion(IPYPortD iport)
    	{
    		difusion = new Difusion(iport);
    	}

    	//------------------------------------------------------------------------------
    	public void run()
    	{
    		corba.instantanea.EstadoRobotDHolder st = new EstadoRobotDHolder();
    		String ior=null;
    		LinkedList listaFallos = new LinkedList();

    		while(true)
    		{
    			listaEstados.clear();
    			listaFallos.clear();
    			for (Iterator i = listaRobots.iterator(); i.hasNext(); )
    			{
    				try 
    				{
    					ior = (String)i.next();
    					org.omg.CORBA.Object obj = orb_.string_to_object(ior);
    					RobotSeguidorInt target = corba.robot.RobotSeguidorIntHelper.narrow(obj);
    					target.ObtenerEstado(st);
    					listaEstados.add(st.value);     
    				} catch ( Exception e )
    				{
    					System.out.println("Detectado fallo Robot: " + ior );
    					listaFallos.add( ior );
    				} 
    			}
    			
    			instantanea = new InstantaneaD((EstadoRobotD[])listaEstados.toArray(new EstadoRobotD[0]));
    			difusion.sendObject(instantanea); 

    			try
    			{
    				Thread.sleep(400);
    			}catch(InterruptedException e)
    			{
    				e.printStackTrace();
    			}
    		}
    	}
    }
}