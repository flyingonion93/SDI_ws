package corba.khepera.robot;

/**
* corba/khepera/robot/PosicionDHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TFinal_agencia-robots-Corba/robot.idl
* domingo 10 de enero de 2016 12H29' CET
*/

public final class PosicionDHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.khepera.robot.PosicionD value = null;

  public PosicionDHolder ()
  {
  }

  public PosicionDHolder (corba.khepera.robot.PosicionD initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.khepera.robot.PosicionDHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.khepera.robot.PosicionDHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.khepera.robot.PosicionDHelper.type ();
  }

}
