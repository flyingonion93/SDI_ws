package client;

import java.net.*;
import java.io.*;
import rmi.EchoInt;
import java.util.Timer;
import java.util.TimerTask;

public class EchoObjectStub2 implements EchoInt
{
	
	private Socket echoSocket = null;
	private PrintWriter os = null;
	private BufferedReader is = null;
	private String host = "localhost";
	private int port = 1200;
	private String output = "Error";
	private boolean connected = false;
	private boolean toutActive = false;
	Timeout tout = null;
	
	public void setHostAndPort( String host, int port )
	{
		this.host = host;
		this.port = port;
		tout = new Timeout( 10, this );
	}
	
	public String echo( String input ) throws java.rmi.RemoteException
	{
		connect();
		if( echoSocket != null && os != null && is != null )
		{
			try
			{
				os.println( input );
				os.flush();
				output = is.readLine();				
			} catch ( IOException e) {
				System.err.println( "I/O failed in reading/writing socket" );
			}
		}
		programDisconnection();
		return output;
	}

	private synchronized void connect() throws java.rmi.RemoteException
	{
		if( connected )
		{
			toutActive = false;
			tout.cancel();
		}
		try
		{
			echoSocket = new Socket( host, port );
			is = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) );
			os = new PrintWriter( echoSocket.getOutputStream() );
		} catch( IOException e )
		{
			System.err.println( "Connection error" );
			e.printStackTrace();
			return;
		}
		connected = true;
	}
	
	private synchronized void disconnect()
	{
		if( toutActive )
		{
			try
			{
				echoSocket.close();
				os.close();
				is.close();
			} catch( IOException e )
			{
				System.err.println( "Communication error" );
				e.printStackTrace();
				return;
			}
			connected = false;	
		}		
	}
	
	private synchronized void programDisconnection()
	{
		tout.start();
		toutActive = true;
	}	
	
	class Timeout
	{
		Timer timer;
		EchoObjectStub2 stub;
		int seconds;
		
		public Timeout( int seconds, EchoObjectStub2 stub )
		{
			this.seconds = seconds;
			this.stub = stub;
		}
		
		public void start()
		{
			timer = new Timer();
			timer.schedule( new TimeoutTask(), seconds * 1000 );
		}
		
		public void cancel()
		{
			timer.cancel();
		}
		
		class TimeoutTask extends TimerTask
		{
			public void run()
			{
				stub.disconnect();
			}
		}
	}
}