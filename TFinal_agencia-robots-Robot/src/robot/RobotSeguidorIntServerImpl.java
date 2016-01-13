package robot;

import comm.Difusion;
import corba.camara.CamaraInt;
import corba.camara.suscripcionD;
import corba.instantanea.EstadoRobotD;
import corba.instantanea.EstadoRobotDHolder;
import corba.instantanea.InstantaneaD;
import corba.instantanea.PuntosRobotD;
import corba.khepera.escenario.EscenarioD;
import corba.khepera.robot.PosicionD;
import corba.robot.RobotSeguidorInt;
import corba.robot.RobotSeguidorIntPOA;
import java.io.PrintStream;
import khepera.control.Braitenberg;
import khepera.control.Destino;
import khepera.control.Trayectoria;
import khepera.escenario.Escenario;
import khepera.robot.IzqDer;
import khepera.robot.Polares;
import khepera.robot.Posicion;
import khepera.robot.RobotKhepera;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

public class RobotSeguidorIntServerImpl extends RobotSeguidorIntPOA{

	private InstantaneaD instantanea;
	private int indexLider = -1;
	private Escenario escenario;
	private RobotKhepera robot;
	private Trayectoria tra;
	private Destino destino = new Destino();
	private Braitenberg bra = new Braitenberg();
	private POA poa_;
	ORB orb;
	CamaraInt camara;
	String minombre = "Robot";
	int miid;
	String miIOR;
	RobotSeguidorInt ref;
	PuntosRobotD puntos;
	Polares posicion = new Polares();
	PosicionD obj = new PosicionD(500.0F, 500.0F);
	int milider = -1;
  
  public RobotSeguidorIntServerImpl(POA poa){
    poa_ = poa;
    PosicionD centro = new PosicionD(1.0F, 1.0F);
    PosicionD[] sens = new PosicionD[9];
    PosicionD[] finsens = new PosicionD[9];
    PosicionD[] inter = new PosicionD[8];
    
    for (int i = 0; i < 9; i++) {
      sens[i] = new PosicionD(0.0F, 0.0F);
    }
    
    for (int i = 0; i < 9; i++) {
      finsens[i] = new PosicionD(0.0F, 0.0F);
    }
    
    for (int i = 0; i < 8; i++) {
      inter[i] = new PosicionD(0.0F, 0.0F);
    }
    puntos = new PuntosRobotD(centro, sens, finsens, inter);
  }
  
  public POA _default_POA(){
    if (poa_ != null) {
      return poa_;
    }
    return super._default_POA();
  }
  
  public synchronized void ObtenerEstado(EstadoRobotDHolder est){
    EstadoRobotD _robot = new EstadoRobotD(minombre, miid, miIOR, ref, puntos, obj, milider);
    
    est.value = _robot;
  }
  
  public synchronized void ModificarEscenario(EscenarioD esc){
    escenario = new Escenario(esc);
    robot = new RobotKhepera(new PosicionD(1.0F, 1.0F), this.escenario, 0);
    System.out.println("Nuevo Escenario");
  }
  
  public synchronized void ModificarObjetivo(PosicionD NuevoObj){
    obj = NuevoObj;
    milider = -1;
    System.out.println("Objetivo nuevo ~~> " + new Posicion(NuevoObj).toString());
  }
  
  public synchronized void ModificarPosicion(PosicionD npos){
    robot.fijarPosicion(npos);
    
    System.out.println("Posicion nueva ~~> " + new Posicion(npos).toString());
  }
  
  public synchronized void ModificarLider(int idLider){
    boolean encontrado = false;
    if (instantanea != null){
      for (int i = 0; i < instantanea.estadorobs.length; i++){
    	  EstadoRobotD sr = instantanea.estadorobs[i];
        if ((sr.id == idLider) && (sr.id != miid)){
          milider = idLider;
          encontrado = true;
          System.out.println("Nuevo Lider ~~> " + idLider);
          break;
        }
    }
      if (!encontrado) {
        System.out.println("Lider Invalido  " + idLider);
      }
    }
  }
  
  void avanzar(){
    
	  IzqDer nivel = new IzqDer();
	  IzqDer nivel2 = new IzqDer();
    
	  robot.avanzar();
	  posicion = robot.posicionPolares();
	  puntos = robot.posicionRobot();
    
	  tra = new Trayectoria(posicion, obj);
	  float[] ls = robot.leerSensores();
    
	  nivel = destino.calcularVelocidad(tra);
	  nivel2 = bra.calcularVelocidad(ls);
	  nivel.izq += nivel2.izq / 90;nivel.der += nivel2.der / 90;
	  robot.fijarVelocidad(nivel.izq, nivel.der);
  }
  
  public void start(){
    new RobotDifusion().start();
  }
  
  class RobotDifusion extends Thread{
	  
	  private suscripcionD suscripcion;
	  private Difusion difusion;
      private EstadoRobotD estador;
      private int mii = -1;
    
    
      RobotDifusion() {}
    
    public void run(){
    	suscripcion = RobotSeguidorIntServerImpl.this.camara.SuscribirRobot(RobotSeguidorIntServerImpl.this.miIOR);
      try{
        difusion = new Difusion(suscripcion.iport);
      }
      catch (Exception e){
        e.printStackTrace();
      }
      
      RobotSeguidorIntServerImpl.this.escenario = new Escenario(this.suscripcion.esc);
      RobotSeguidorIntServerImpl.this.robot = new RobotKhepera(new PosicionD(1.0F, 1.0F), RobotSeguidorIntServerImpl.this.escenario, 0);
      RobotSeguidorIntServerImpl.this.miid = this.suscripcion.id;
      RobotSeguidorIntServerImpl.this.instantanea = ((InstantaneaD)this.difusion.receiveObject());
      
      for(int i = 0; i < RobotSeguidorIntServerImpl.this.instantanea.estadorobs.length; i++){
        this.estador = RobotSeguidorIntServerImpl.this.instantanea.estadorobs[i];
        if (this.estador.id == RobotSeguidorIntServerImpl.this.miid) {
          this.mii = i;
        }
      }
      
      if (this.mii > 0){
        RobotSeguidorIntServerImpl.this.milider = RobotSeguidorIntServerImpl.this.instantanea.estadorobs[(this.mii - 1)].id;
        RobotSeguidorIntServerImpl.this.indexLider = (this.mii - 1);
      }
      
      while(true){
        RobotSeguidorIntServerImpl.this.instantanea = ((InstantaneaD)this.difusion.receiveObject());
        for (int i = 0; i < RobotSeguidorIntServerImpl.this.instantanea.estadorobs.length; i++){
          this.estador = RobotSeguidorIntServerImpl.this.instantanea.estadorobs[i];
          if ((this.estador.id == RobotSeguidorIntServerImpl.this.milider) && (this.estador.id != RobotSeguidorIntServerImpl.this.miid)) {
            RobotSeguidorIntServerImpl.this.indexLider = i;
          }
        }
        try{
          if (RobotSeguidorIntServerImpl.this.milider >= 0) {
            RobotSeguidorIntServerImpl.this.obj = RobotSeguidorIntServerImpl.this.instantanea.estadorobs[RobotSeguidorIntServerImpl.this.indexLider].puntrob.centro;
          }
        }
        catch (Exception ex)
        {
          RobotSeguidorIntServerImpl.this.milider = -1;
        }
        RobotSeguidorIntServerImpl.this.avanzar();
      }
    }
  }
}