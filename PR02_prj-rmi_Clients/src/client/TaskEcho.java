package client;

import java.io.Serializable;
import java.rmi.RemoteException;
import interfaces.compute.TaskInt;

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