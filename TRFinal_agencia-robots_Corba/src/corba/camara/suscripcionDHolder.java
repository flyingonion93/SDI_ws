package corba.camara;

/**
* corba/camara/suscripcionDHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TRFinal_agencia-robots_UDP/robot.idl
* domingo 10 de enero de 2016 11H04' CET
*/

public final class suscripcionDHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.camara.suscripcionD value = null;

  public suscripcionDHolder ()
  {
  }

  public suscripcionDHolder (corba.camara.suscripcionD initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.camara.suscripcionDHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.camara.suscripcionDHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.camara.suscripcionDHelper.type ();
  }

}
