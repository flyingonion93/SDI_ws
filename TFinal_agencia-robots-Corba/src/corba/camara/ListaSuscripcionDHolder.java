package corba.camara;

/**
* corba/camara/ListaSuscripcionDHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TFinal_agencia-robots-Corba/robot.idl
* domingo 10 de enero de 2016 12H29' CET
*/

public final class ListaSuscripcionDHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.camara.ListaSuscripcionD value = null;

  public ListaSuscripcionDHolder ()
  {
  }

  public ListaSuscripcionDHolder (corba.camara.ListaSuscripcionD initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.camara.ListaSuscripcionDHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.camara.ListaSuscripcionDHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.camara.ListaSuscripcionDHelper.type ();
  }

}
