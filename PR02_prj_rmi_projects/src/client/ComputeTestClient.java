package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import interfaces.compute.ComputeServerInt;
import interfaces.compute.TaskInt;

public class ComputeTestClient implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	class TaskEcho implements TaskInt, Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public Object execute() throws RemoteException
		{
			//EJERCICIO
		}
		
		public Object execute( Object params ) throws RemoteException
		{
			//EJERCICIO
		}
	}
	
	public TaskInt echoTask = new TaskEcho();
	
	public static void vmain( String[] args )
	{
		String server_name = new String();
		if( args.length == 1 )
			server_name = "//" + args[0] + "/Compute";
		else
			server_name = "//localhost/Compute";
		
		ComputeTestClient computeClient = new ComputeTestClient();
		
		if( System.getSecurityManager() == null )
			System.setSecurityManager( new SecurityManager() );
		
		BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );
		PrintWriter stdOut = new PrintWriter( System.out );
		String input, output;
		
		try
		{
			//EJERCICIO: "lookup" the Compute server RMI object
			//EJERCICIO: load the task (computeClient.echoTask) to the computeServer
			
			stdOut.println("> ");
			stdOut.flush();
			while( ( input = stdIn.readLine() ) != null )
			{
				//EJERCICIO: execute the loaded task. Get the response in "output"
				stdOut.println( output );
				stdOut.print( "> " );
				stdOut.flush();
			}
		}catch( Exception e )
		{
			System.err.println( "Error en el cliente de echo RMI: " + e.getMessage() );
			e.printStackTrace();
		}
	}
}
