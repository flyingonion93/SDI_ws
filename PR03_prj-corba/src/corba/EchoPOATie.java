package corba;


/**
* corba/EchoPOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/PR03_prj-corba/src/Echo.idl
* martes 13 de octubre de 2015 13H13' CEST
*/

public class EchoPOATie extends EchoPOA
{

  // Constructors

  public EchoPOATie ( corba.EchoOperations delegate ) {
      this._impl = delegate;
  }
  public EchoPOATie ( corba.EchoOperations delegate , org.omg.PortableServer.POA poa ) {
      this._impl = delegate;
      this._poa      = poa;
  }
  public corba.EchoOperations _delegate() {
      return this._impl;
  }
  public void _delegate (corba.EchoOperations delegate ) {
      this._impl = delegate;
  }
  public org.omg.PortableServer.POA _default_POA() {
      if(_poa != null) {
          return _poa;
      }
      else {
          return super._default_POA();
      }
  }
  public String Echo (String input)
  {
    return _impl.Echo(input);
  } // Echo

  private corba.EchoOperations _impl;
  private org.omg.PortableServer.POA _poa;

} // class EchoPOATie
