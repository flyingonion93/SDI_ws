package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import interfaces.compute.ComputeServerInt;
import interfaces.compute.TaskInt;
import interfaces.echo.EchoInt;

public class ServCompute implements ComputeServerInt
{
	ArrayList<TaskInt> taskList = new ArrayList<TaskInt>();
	
	protected ServCompute()
	{
		super();
	}
	
	@Override
	public int loadTask( TaskInt a ) throws RemoteException
	{
		System.out.println( "Loading TASK" );
		taskList.add( a );
		return //EJERCICIO: returns an appropiate taskid
	}
	
	@Override
	public int removeTask( int idx ) throws RemoteException
	{
		System.out.println( "Removing TASK" );
		taskList.remove( idx );
		return 0;
	}
	
	@Override
	public Object executeTask( TaskInt a, Object params ) throws RemoteException
	{
		System.out.println( "Loading adn executing a task with param (" + params + ")" );
		return a.execute( params );
	}
	
	@Override
	public Object executeTask( int idx, Object params ) throws RemoteException
	{
		System.out.println( "Executing the task " + idx + " with param(" + params + ")" );
		return taskList.get( idx ).execute( params );
	}
	
	@Override
	public Object executeTask(TaskInt a) throws RemoteException {		
		return a.execute();
	}
	
	public static void main( String[] args )
	{
		if( System.getSecurityManager() == null )
			System.setSecurityManager( new SecurityManager() );
		
		try
		{
			Registry reg = LocateRegistry.getRegistry();
			ComputeServerInt stub = (ComputeServerInt) UnicastRemoteObject.exportObject( new ServCompute(),  0 );
			reg.rebind( "miComputeServer", stub );
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