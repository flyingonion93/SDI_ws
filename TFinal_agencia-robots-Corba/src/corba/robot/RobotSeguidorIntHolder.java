package corba.robot;

/**
* corba/robot/RobotSeguidorIntHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TFinal_agencia-robots-Corba/robot.idl
* domingo 10 de enero de 2016 12H29' CET
*/

public final class RobotSeguidorIntHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.robot.RobotSeguidorInt value = null;

  public RobotSeguidorIntHolder ()
  {
  }

  public RobotSeguidorIntHolder (corba.robot.RobotSeguidorInt initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.robot.RobotSeguidorIntHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.robot.RobotSeguidorIntHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.robot.RobotSeguidorIntHelper.type ();
  }

}
