package corba.khepera.escenario;


/**
* corba/khepera/escenario/EscenarioD.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TFinal_agencia-robots-Corba/robot.idl
* domingo 10 de enero de 2016 12H29' CET
*/

public final class EscenarioD implements org.omg.CORBA.portable.IDLEntity
{
  public corba.khepera.escenario.RectanguloD recs[] = null;
  public int nrecs = (int)0;
  public int color = (int)0;

  public EscenarioD ()
  {
  } // ctor

  public EscenarioD (corba.khepera.escenario.RectanguloD[] _recs, int _nrecs, int _color)
  {
    recs = _recs;
    nrecs = _nrecs;
    color = _color;
  } // ctor

} // class EscenarioD
