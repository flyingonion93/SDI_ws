package robot;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import corba.camara.CamaraInt;
import corba.camara.suscripcionD;
import corba.instantanea.EstadoRobotD;
import corba.instantanea.EstadoRobotDHolder;
import corba.instantanea.InstantaneaD;
import corba.instantanea.PuntosRobotD;
import corba.khepera.escenario.EscenarioD;
import corba.khepera.robot.PosicionD;
import corba.robot.RobotSeguidorInt;
import khepera.control.Braitenberg;
import khepera.control.Destino;
import khepera.control.Trayectoria;
import khepera.escenario.Escenario;
import khepera.robot.Polares;
import khepera.robot.Posicion;
import khepera.robot.RobotKhepera;

/**
 * This class is the implemetation object for your IDL interface.
 *
 * Let the Eclipse complete operations code by choosing 'Add unimplemented methods'.
 */
public class RobotSeguidorIntServerImpl extends corba.robot.RobotSeguidorIntPOA {
	
	private POA poa_;
	ORB orb_;
	CamaraInt camara;
	String minombre;
	int miid;
	String miIOR;	 
	private InstantaneaD instantanea;
	RobotSeguidorInt referencia;
	PuntosRobotD puntos;
	Polares posicion = new Polares();
	PosicionD posRef = new PosicionD( 500, 500 );
	int liderId = -1;
	private int indexLider = -1;
	private Escenario escenario;
	private RobotKhepera khepera;
	private Trayectoria trayectoria;
	private Destino destino = new Destino();
	private Braitenberg bra = new Braitenberg();
	
	/**
	 * Constructor for RobotSeguidorIntServerImpl 
	 */
	public RobotSeguidorIntServerImpl( POA poa ) {
		poa_ = poa;
		PosicionD centro = new PosicionD(0,0);
		PosicionD[] sens = new PosicionD[9];
		PosicionD[] finsens = new PosicionD[9];
		PosicionD[] inter = new PosicionD[8];
		for( int i = 0; i < 9; i++ )
		{
			sens[i] = new PosicionD(0,0);
		}
		for( int i = 0; i < 9; i++ )
		{
			finsens[i] = new PosicionD(0,0);
		}
		for( int i = 0; i < 9; i++ )
		{
			inter[i] = new PosicionD(0,0);
		}
		puntos = new PuntosRobotD( centro, sens, finsens, inter );
	}
	
	public POA _default_POA()
	{
		if (poa_ != null) {
			return poa_;
	    }
	    return super._default_POA();
	}

	@Override
	public void ObtenerEstado(EstadoRobotDHolder est) {
		EstadoRobotD estadoRobot = new EstadoRobotD(
				minombre,
				miid,
				miIOR,
				referencia,
				puntos,
				posRef,
				liderId);
		est.value = estadoRobot;
	}

	@Override
	public void ModificarEscenario(EscenarioD esc) {
		escenario = new Escenario( esc );
		khepera = new RobotKhepera( new PosicionD( 0, 0), escenario, 0);
		System.out.println( "Escenario modificado" );		
	}

	@Override
	public void ModificarObjetivo(PosicionD NuevoObj) {
		posRef = NuevoObj;
		liderId = -1;
		System.out.println("Nuevo Objetivo: " + new Posicion(NuevoObj).toString());
	}

	@Override
	public void ModificarPosicion(PosicionD npos) {
		khepera.fijarPosicion(npos);
		System.out.println("Nuevo Objetivo: " + new Posicion(npos).toString());
		
	}

	@Override
	public void ModificarLider(int idLider) {
		boolean encontrado = false;
		if( instantanea != null )
		{
			for( int i = 0; i < instantanea.estadorobs.length; i++ )
			{
				EstadoRobotD estadoRob = instantanea.estadorobs[i];
				if( estadoRob.id == idLider  && estadoRob.id != this.miid )
				{
					liderId = idLider;
					encontrado = true;
					System.out.println(( "Nuevo Lider: " + idLider));
					break;
				}
			}
			if( !encontrado )
				System.out.println("Lider erroneo: " + idLider);
		}
	}
	
	void MovimientoRobot()
	{
		
	}
	
	public void start()
	{
	
	}
	
	class RobotDifusion extends Thread
	{
		private Difusion difusion;
		private EstadoRobotD estadoRob;
		private suscripcionD sus;
		
		public void run()
		{
			
		}
	}
}
