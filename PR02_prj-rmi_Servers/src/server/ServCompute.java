package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import interfaces.compute.ComputeServerInt;
import interfaces.compute.TaskInt;

public class ServCompute implements ComputeServerInt
{
	//EJERCICIO: define a Task container( e.g an ArrayList )
	
	protected ServCompute()
	{
		super();
	}
	
	@Override
	public int loadTask( TaskInt a ) throws RemoteException
	{
		System.out.println( "Loading TASK" );
		//EJERCICIO: add task to the defined container
		return //EJERCICIO: returns an appropiate taskid
	}
	
	@Override
	public int removeTask( int idx ) throws RemoteException
	{
		System.out.println( "Removing TASK" );
		//EJERCICIO: remove the task from the container
		return 0;
	}
	
	@Override
	public Object executeTask( TaskInt a, Object params ) throws RemoteException
	{
		System.out.println( "Loading adn executing a task with param (" + params + ")" );
		//EJERCICIO: execute the passed task
		return //EJERCICIO:
	}
	
	@Override
	public Object executeTask( int idx, Object params ) throws RemoteException
	{
		System.out.println( "Executing the task " + idx + " with param(" + params + ")" );
		//EJERCICIO: execute the previosuly loaded task
		return //EJERCICIO:
	}
	
	public static void main( String[] args )
	{
		if( System.getSecurityManager() == null )
			System.setSecurityManager( new SecurityManager() );
		
		try
		{
			//EJERCICIO: get the local registry
			//EJERCICIO: build the ServCompute stub
			//EJERCICIO: bind (or rebind) the stub into the local registry
		}catch( RemoteException e )
		{
			System.err.println( "Something wrong happened on the remote end" );
			e.printStackTrace();
			System.exit( -1 );
		}catch( Exception e )
		{
			System.err.println( "Something wrong" );
			e.printStackTrace();
			System.exit( -1 );
		}
		System.out.println( "The server is ready for tasks: " );
	}
}