package server;
/**
 * This class is the implemetation object for your IDL interface.
 *
 * Let the Eclipse complete operations code by choosing 'Add unimplemented methods'.
 */
public class EchoServerImpl extends corba.EchoPOA {
	/**
	 * Constructor for EchoServerImpl 
	 */
	EchoObject eo = new EchoObject();
	
	public EchoServerImpl() {
	}

	@Override
	public String Echo(String input) {
		return eo.echo( input );
	}	
}