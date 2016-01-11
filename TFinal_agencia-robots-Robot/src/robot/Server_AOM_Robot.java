package robot;
import java.util.Properties;

import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.ThreadPolicyValue;

import corba.camara.CamaraInt;
import corba.camara.CamaraIntHelper;

public class Server_AOM_Robot 
{
	
	static CamaraInt camara;
	static int registrado = 0;
	static String nombre = "Robot";
	
	public static void main(String[] args) 
	{

		Properties props = System.getProperties();
		props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
		props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
		props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		props.put("org.omg.CORBA.ORBInitialPort", "1050");
		if( args.length > 0 )
			nombre = args[args.length - 1];
			
		try 
		{
			org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, props);
			org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
			POA poaRoot = POAHelper.narrow(obj);
			org.omg.CORBA.Policy[] policies = {
					poaRoot.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
					poaRoot.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL) 
			};
			POA poa = poaRoot.create_POA("RobotSeguidorIntServerImpl_poa",	poaRoot.the_POAManager(), policies);
			RobotSeguidorIntServerImpl servant = new RobotSeguidorIntServerImpl( poa );
			byte[] objectId = "AnyObjectID".getBytes();
			poa.activate_object_with_id(objectId, servant);
			poaRoot.the_POAManager().activate();
			obj = poa.servant_to_reference(servant);
			do
			{
				
				try
				{
					org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
					
					NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
					
					NameComponent namec = new NameComponent( "Robot", "" );
					
					NameComponent[] path = {namec};
					
					camara = CamaraIntHelper.narrow( nc.resolve( path ) );
					
					System.out.println( "Identificador: " + servant );
					servant.miIOR = orb.object_to_string( obj );
					servant.orb_ = orb;
					servant.camara = camara;
					servant.minombre = nombre;
					registrado = 1;
				} catch( Exception e )
				{
					System.out.println( "Robot no registrado. Reintentando." );
					try
					{
						Thread.sleep( 500 );
					} catch( InterruptedException e2 ){}
				}
			}while( registrado == 0 );
			servant.start();
			System.out.println("Robot Server ready...");
			orb.run();
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
}