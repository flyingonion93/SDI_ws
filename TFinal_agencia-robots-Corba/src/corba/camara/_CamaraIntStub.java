package corba.camara;


/**
* corba/camara/_CamaraIntStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/TFinal_agencia-robots-Corba/robot.idl
* domingo 10 de enero de 2016 12H29' CET
*/

public class _CamaraIntStub extends org.omg.CORBA.portable.ObjectImpl implements corba.camara.CamaraInt
{

  public corba.camara.suscripcionD SuscribirRobot (String IORrob)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("SuscribirRobot", true);
                $out.write_string (IORrob);
                $in = _invoke ($out);
                corba.camara.suscripcionD $result = corba.camara.suscripcionDHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return SuscribirRobot (IORrob        );
            } finally {
                _releaseReply ($in);
            }
  } // SuscribirRobot

  public corba.camara.suscripcionD SuscribirConsola (String IORcons)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("SuscribirConsola", true);
                $out.write_string (IORcons);
                $in = _invoke ($out);
                corba.camara.suscripcionD $result = corba.camara.suscripcionDHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return SuscribirConsola (IORcons        );
            } finally {
                _releaseReply ($in);
            }
  } // SuscribirConsola

  public void BajaRobot (String IORrob)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("BajaRobot", true);
                $out.write_string (IORrob);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                BajaRobot (IORrob        );
            } finally {
                _releaseReply ($in);
            }
  } // BajaRobot

  public void BajaConsola (String IORcons)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("BajaConsola", true);
                $out.write_string (IORcons);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                BajaConsola (IORcons        );
            } finally {
                _releaseReply ($in);
            }
  } // BajaConsola

  public corba.camara.ListaSuscripcionD ObtenerLista ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("ObtenerLista", true);
                $in = _invoke ($out);
                corba.camara.ListaSuscripcionD $result = corba.camara.ListaSuscripcionDHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return ObtenerLista (        );
            } finally {
                _releaseReply ($in);
            }
  } // ObtenerLista

  public corba.camara.IPYPortD ObtenerIPYPortDifusion ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("ObtenerIPYPortDifusion", true);
                $in = _invoke ($out);
                corba.camara.IPYPortD $result = corba.camara.IPYPortDHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return ObtenerIPYPortDifusion (        );
            } finally {
                _releaseReply ($in);
            }
  } // ObtenerIPYPortDifusion

  public corba.instantanea.InstantaneaD ObtenerInstantanea ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("ObtenerInstantanea", true);
                $in = _invoke ($out);
                corba.instantanea.InstantaneaD $result = corba.instantanea.InstantaneaDHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return ObtenerInstantanea (        );
            } finally {
                _releaseReply ($in);
            }
  } // ObtenerInstantanea

  public void ModificarEscenario (corba.khepera.escenario.EscenarioD esc)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("ModificarEscenario", true);
                corba.khepera.escenario.EscenarioDHelper.write ($out, esc);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                ModificarEscenario (esc        );
            } finally {
                _releaseReply ($in);
            }
  } // ModificarEscenario

  public corba.khepera.escenario.EscenarioD ObtenerEscenario ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("ObtenerEscenario", true);
                $in = _invoke ($out);
                corba.khepera.escenario.EscenarioD $result = corba.khepera.escenario.EscenarioDHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return ObtenerEscenario (        );
            } finally {
                _releaseReply ($in);
            }
  } // ObtenerEscenario

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/camara/CamaraInt:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _CamaraIntStub
