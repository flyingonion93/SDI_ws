package corba.camara;


/**
* corba/camara/ListaSuscripcionD.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TFinal_agencia-robots-Corba/robot.idl
* domingo 10 de enero de 2016 12H29' CET
*/

public final class ListaSuscripcionD implements org.omg.CORBA.portable.IDLEntity
{

  //IORs en formato string
  public String IORrobots[] = null;
  public String IORconsolas[] = null;

  public ListaSuscripcionD ()
  {
  } // ctor

  public ListaSuscripcionD (String[] _IORrobots, String[] _IORconsolas)
  {
    IORrobots = _IORrobots;
    IORconsolas = _IORconsolas;
  } // ctor

} // class ListaSuscripcionD
