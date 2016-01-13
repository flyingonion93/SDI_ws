////package robot;
////import java.io.File;
////import java.io.FileOutputStream;
////import java.io.PrintWriter;
////import java.util.Properties;
////
////import org.omg.PortableServer.IdAssignmentPolicyValue;
////// import org.omg.PortableServer.LifespanPolicyValue;
////import org.omg.PortableServer.POA;
////import org.omg.PortableServer.POAHelper;
////import org.omg.PortableServer.ThreadPolicyValue;
////
////public class Server_AOM {
////
////	public static void main(String[] args) {
////
////		Properties props = System.getProperties();
////		props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
////		props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
////
////		try {
////			// Initialize the ORB.
////			org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, props);
////
////			// get a reference to the root POA
////			org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
////			POA poaRoot = POAHelper.narrow(obj);
////
////			// Create policies for our persistent POA
////			org.omg.CORBA.Policy[] policies = {
////					// poaRoot.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
////					poaRoot.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
////					poaRoot.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL) 
////			};
////
////			// Create myPOA with the right policies
////			POA poa = poaRoot.create_POA("RobotSeguidorIntServerImpl_poa",	poaRoot.the_POAManager(), policies);
////
////			// Create the servant
////			RobotSeguidorIntServerImpl servant = new RobotSeguidorIntServerImpl();
////
////			// Activate the servant with the ID on myPOA
////			byte[] objectId = "AnyObjectID".getBytes();
////			poa.activate_object_with_id(objectId, servant);
////			
////			// Activate the POA manager
////			poaRoot.the_POAManager().activate();
////
////			// Get a reference to the servant and write it down.
////			obj = poa.servant_to_reference(servant);
////
////			// ---- Uncomment below to enable Naming Service access. ----
////			// org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
////			// NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
////			// nc.bind(nc.to_name("MyServerObject"), obj);
////
////			PrintWriter ps = new PrintWriter(new FileOutputStream(new File("server.ior")));
////			ps.println(orb.object_to_string(obj));
////			ps.close();
////
////			System.out.println("CORBA Server ready...");
////
////			// Wait for incoming requests
////			orb.run();
////		}
////		catch(Exception ex) {
////			ex.printStackTrace();
////		}
////	}
////}
//package robot;
//import java.util.Properties;
//
//import org.omg.CORBA.ORBPackage.InvalidName;
//import org.omg.CosNaming.NamingContextExt;
//import org.omg.CosNaming.NamingContextExtHelper;
//import org.omg.CosNaming.NamingContextPackage.CannotProceed;
//import org.omg.CosNaming.NamingContextPackage.NotFound;
//import org.omg.PortableServer.IdAssignmentPolicyValue;
//// import org.omg.PortableServer.LifespanPolicyValue;
//import org.omg.PortableServer.POA;
//import org.omg.PortableServer.POAHelper;
//import org.omg.PortableServer.ThreadPolicyValue;
//
//import corba.camara.CamaraInt;
//
//public class Server_AOM {
//	
//	static CamaraInt camara;
//	static int ok=0;
//
//	public static void main(String[] args) {
//
//		Properties props = System.getProperties();
//		props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
//		props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
//
//		// Solo si se cambia el host 
//		props.put("org.omg.CORBA.ORBInitialHost", "localhost");
//		// Solo si se cambia el port 
//		props.put("org.omg.CORBA.ORBInitialPort", "1050");
//				
//				
//		try {
//			// Initialize the ORB.
//			org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, props);
//
//			// get a reference to the root POA
//			org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
//			POA poaRoot = POAHelper.narrow(obj);
//
//			// Create policies for our persistent POA
//			org.omg.CORBA.Policy[] policies = {
//					// poaRoot.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
//					poaRoot.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
//					poaRoot.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL) 
//			};
//
//			// Create myPOA with the right policies
//			POA poa = poaRoot.create_POA("RobotSeguidorIntServerImpl_poa",	poaRoot.the_POAManager(), policies);
//
//			// Create the servant
//			RobotSeguidorIntServerImpl servant = new RobotSeguidorIntServerImpl();
//
//			// Activate the servant with the ID on myPOA
//			byte[] objectId = "Robot".getBytes();
//			poa.activate_object_with_id(objectId, servant);
//			
//			// Activate the POA manager
//			poaRoot.the_POAManager().activate();
//
//			// Get a reference to the servant and write it down.
//			obj = poa.servant_to_reference(servant);
//
//			// ---- Uncomment below to enable Naming Service access. ----
//			// org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
//			// NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
//			// nc.bind(nc.to_name("MyServerObject"), obj);
//
//			//PrintWriter ps = new PrintWriter(new FileOutputStream(new File("server.ior")));
//			//ps.println(orb.object_to_string(obj));
//			//ps.close();
//			do{
//		        try{
//		        	//EJERCICIO:Conectar con el servidor de nombre y obtener una referencia 
//		        	//a la **camara** 
//		        	
//		    		try {
//		    			org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
//		    			NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
//		    			org.omg.CORBA.Object obje=null;
//		    			obje = nc.resolve_str("Camara");
//		    			camara = corba.camara.CamaraIntHelper.narrow(obje);
//		    		} catch (InvalidName e) {
//		    			e.printStackTrace();
//		    		} catch (NotFound e) {
//		    			e.printStackTrace();
//		    		} catch (CannotProceed e) {
//		    			e.printStackTrace();
//		    		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
//		    			e.printStackTrace();
//		    		}    		
//		        	
//
//		          System.out.println("Identificador: " + servant);
//			         //EJERCICIO: convertir la referencia al robot en un IOR en formato String 
//	              servant.miIOR = orb.object_to_string(obj); 
//
//		          servant.orb = orb;
//		          servant.camara = camara;
//		          if (args.length>0) servant.minombre = args[0]; else servant.minombre="Robot";
//		          ok=1;
//		        } catch(Exception ex) {
//		          System.out.println("El robot no se registro bien en la camara. Reintentando...");
//		        }
//		      } while(ok==0);
//
//		      servant.start();
//			System.out.println("CORBA Server ready...");
//
//			// Wait for incoming requests
//			orb.run();
//		}
//		catch(Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//}

package robot;

import corba.camara.CamaraInt;
import corba.camara.CamaraIntHelper;
import java.io.PrintStream;
import java.util.Properties;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.ThreadPolicyValue;

public class Server_AOM_Robot
{
  static CamaraInt camara;
  static int ok = 0;
  static String nombre = "Robot";
  
  public static void main(String[] args)
  {
    Properties props = System.getProperties();
    
    props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
    props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
    props.put("org.omg.CORBA.ORBInitialHost", "localhost");
	// Solo si se cambia el port 
	props.put("org.omg.CORBA.ORBInitialPort", "1050");
	
    if (args.length > 0) {
      nombre = args[(args.length - 1)];
    }
    System.out.println("Nombre robot: " + nombre);
    try
    {
      ORB orb = ORB.init(args, props);
      
      org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
      POA poaRoot = POAHelper.narrow(obj);
      
      Policy[] policies = {
      
        poaRoot.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID), 
        poaRoot.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL) };
      
      POA poa = poaRoot.create_POA("RobotSeguidorIntServerImpl_poa", poaRoot.the_POAManager(), policies);
      
      RobotSeguidorIntServerImpl servant = new RobotSeguidorIntServerImpl(poa);
      
      byte[] objectId = "AnyObjectID".getBytes();
      poa.activate_object_with_id(objectId, servant);
      
      poaRoot.the_POAManager().activate();
      
      obj = poa.servant_to_reference(servant);
      do
      {
        try
        {
          org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
          NamingContext ncRef = NamingContextHelper.narrow(objRef);
          NameComponent nc = new NameComponent("Camara", "");
          NameComponent[] path = { nc };
          camara = CamaraIntHelper.narrow(ncRef.resolve(path));
          
          System.out.println("Identificador: " + servant);
          
          servant.miIOR = orb.object_to_string(obj);
          servant.orb = orb;
          servant.camara = camara;
          servant.minombre = nombre;
          ok = 1;
        }
        catch (Exception ex)
        {
          System.out.println("El robot no se registro bien en la camara. Reintentando en 10 seg...");
          try
          {
            Thread.sleep(10000L);
          }
          catch (InterruptedException localInterruptedException) {}
        }
      } while (ok == 0);
      servant.start();
      
      System.out.println("ROBOT Server ready...");
      
      orb.run();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
