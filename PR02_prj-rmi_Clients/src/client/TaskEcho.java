package client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
			Date d = new Date();
			String strDate = DateFormat.getTimeInstance( DateFormat.LONG, Locale.FRANCE ).format( d );
			String ret = "Client: " + strDate + "> Task sent";
			try
			{
				Thread.sleep(3000);
			} catch( InterruptedException e )
			{
				e.printStackTrace();
			}
			return ret;
		}
		
		public Object execute( Object params ) throws RemoteException
		{
			Date d = new Date();
			String strDate = DateFormat.getTimeInstance( DateFormat.LONG, Locale.FRANCE ).format( d );
			String ret = "Client: " + strDate + "> " + params;
			try
			{
				Thread.sleep(3000);
			} catch( InterruptedException e )
			{
				e.printStackTrace();
			}
			return ret;
		}
	}