package client;

import java.io.*;
import java.net.*;
import server.EchoObject;

public class Echo 
{
	
	private static EchoObject ss;
	
	public static void main( String[] args )
	{
		ss = new EchoObject();
		BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );
		PrintWriter stdOut = new PrintWriter( System.out );
		String input, output;
		try
		{
			while( true )
			{
				input = stdIn.readLine();
				output = ss.echo( input );
				stdOut.println( output );
			}
		} catch( UnknownHostException e ) {
			System.err.println( "Don't know about host: " + args[0] );
		} catch( IOException e ) {
			System.err.println( "I/O failed for connection to host: " + args[0] );
		}
	}

}
