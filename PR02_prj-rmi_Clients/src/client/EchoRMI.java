package client;

import java.io.*;
import java.rmi.Naming;
import interfaces.echo.EchoInt;

public class EchoRMI 
{
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		if( args.length < 1 )
		{
			System.out.println( "Uso echo <host>" );
			System.exit(1);			
		}
		if( System.getSecurityManager() == null )
		{
			System.setSecurityManager( new SecurityManager() );			
		}
		BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );
		PrintWriter stdOut = new PrintWriter( System.out );
		String input, output;
		try
		{			
			EchoInt eo = (EchoInt) Naming.lookup("//"+args[0]+"/miEcho");
			stdOut.print( "> " );
			stdOut.flush();
			while( ( input = stdIn.readLine() ) != null )
			{
				output = eo.echo( input );
				stdOut.println( output );
				stdOut.print( "> " );
				stdOut.flush();
			}
		}catch( Exception e )
		{
			System.err.println( "RMI Echo Client error: " + e.getLocalizedMessage() );
		}
	}
}
