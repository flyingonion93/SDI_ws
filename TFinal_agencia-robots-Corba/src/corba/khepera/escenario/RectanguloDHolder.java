package corba.khepera.escenario;

/**
* corba/khepera/escenario/RectanguloDHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TFinal_agencia-robots-Corba/robot.idl
* domingo 10 de enero de 2016 12H29' CET
*/

public final class RectanguloDHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.khepera.escenario.RectanguloD value = null;

  public RectanguloDHolder ()
  {
  }

  public RectanguloDHolder (corba.khepera.escenario.RectanguloD initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.khepera.escenario.RectanguloDHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.khepera.escenario.RectanguloDHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.khepera.escenario.RectanguloDHelper.type ();
  }

}
