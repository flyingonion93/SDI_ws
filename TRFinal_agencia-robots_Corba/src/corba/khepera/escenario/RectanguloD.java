package corba.khepera.escenario;


/**
* corba/khepera/escenario/RectanguloD.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TRFinal_agencia-robots_UDP/robot.idl
* domingo 10 de enero de 2016 11H04' CET
*/

public final class RectanguloD implements org.omg.CORBA.portable.IDLEntity
{
  public float x = (float)0;
  public float y = (float)0;
  public float ancho = (float)0;
  public float alto = (float)0;
  public int color = (int)0;

  public RectanguloD ()
  {
  } // ctor

  public RectanguloD (float _x, float _y, float _ancho, float _alto, int _color)
  {
    x = _x;
    y = _y;
    ancho = _ancho;
    alto = _alto;
    color = _color;
  } // ctor

} // class RectanguloD