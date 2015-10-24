package corba.instantanea;


/**
* corba/instantanea/EstadoRobotDHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /home/ubuntu_sdi/SDI/ws/PR04_prj-difusion/src/robot.idl
* viernes 23 de octubre de 2015 09H11' CEST
*/

abstract public class EstadoRobotDHelper
{
  private static String  _id = "IDL:corba/instantanea/EstadoRobotD:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.instantanea.EstadoRobotD that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.instantanea.EstadoRobotD extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "nombre",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ulong);
          _members0[1] = new org.omg.CORBA.StructMember (
            "id",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "IORrob",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (corba.instantanea.EstadoRobotDHelper.id (), "EstadoRobotD", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.instantanea.EstadoRobotD read (org.omg.CORBA.portable.InputStream istream)
  {
    corba.instantanea.EstadoRobotD value = new corba.instantanea.EstadoRobotD ();
    value.nombre = istream.read_string ();
    value.id = istream.read_ulong ();
    value.IORrob = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.instantanea.EstadoRobotD value)
  {
    ostream.write_string (value.nombre);
    ostream.write_ulong (value.id);
    ostream.write_string (value.IORrob);
  }

}
