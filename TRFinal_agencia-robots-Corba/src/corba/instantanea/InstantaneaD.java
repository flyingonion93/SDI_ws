package corba.instantanea;


/**
* corba/instantanea/InstantaneaD.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TRFinal_agencia-robots-Corba/robot.idl
* domingo 10 de enero de 2016 12H16' CET
*/

public final class InstantaneaD implements org.omg.CORBA.portable.IDLEntity
{
  public corba.instantanea.EstadoRobotD estadorobs[] = null;

  public InstantaneaD ()
  {
  } // ctor

  public InstantaneaD (corba.instantanea.EstadoRobotD[] _estadorobs)
  {
    estadorobs = _estadorobs;
  } // ctor

} // class InstantaneaD
