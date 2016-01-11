package camara;
import java.util.Properties;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.ThreadPolicyValue;

import corba.camara.*;

public class Server_AOM_Camara {
	
	private static corba.camara.IPYPortD ipyport;
	
	public static void main(String[] args) 
	{
		Properties props = System.getProperties();
		props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
		props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
		props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		props.put("org.omg.CORBA.ORBInitialPort", "1050");
		
		if (args.length==4)
			ipyport = new IPYPortD( args[2], Integer.parseInt(args[3]) );
		else
			ipyport = new IPYPortD( "228.7.7.7", 7010);
		System.out.println("Difusion por canal. " + ipyport.ip + " / " + ipyport.port);


		try 
		{
			org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, props);
			org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
			POA poaRoot = POAHelper.narrow(obj);
			org.omg.CORBA.Policy[] policies = {
					poaRoot.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
					poaRoot.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL) 
			};
			POA poa = poaRoot.create_POA("CamaraIntServerImpl_poa",	poaRoot.the_POAManager(), policies);
			CamaraIntServerImpl servant = new CamaraIntServerImpl(orb,poa,ipyport);
			byte[] objectId = "AnyObjectID".getBytes();
			poa.activate_object_with_id(objectId, servant);
			poaRoot.the_POAManager().activate();
			obj = poa.servant_to_reference(servant);
			try
			{
				org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
				NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
				nc.rebind(nc.to_name("Camara"), obj);	
			} catch( Exception e )
			{
				e.printStackTrace();
				System.err.println( "Cámara no registrada" );
			}
			servant.start();
			System.out.println("Camara Server ready...");
			orb.run();
		}catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
}