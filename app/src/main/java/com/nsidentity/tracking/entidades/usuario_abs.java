package com.nsidentity.tracking.entidades;



import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.content.Context;
import java.text.ParseException;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;


  @SuppressWarnings("serial")
  public abstract class usuario_abs extends Object implements Serializable {

    public static String msgError="";
    public static Boolean Error=false;

 public  usuario_abs(){  

  }
	protected Integer usuario_id = -1;
	protected String nombre ="";
	protected String paterno=null;
	protected String materno=null;
	protected String nombre_usuario ="";
	protected String contrasena ="";
	protected Boolean activo = false;


	public Integer get_usuario_id()
	{
		return  usuario_id; 
}
	public void  set_usuario_id(Integer value)
	{
    usuario_id=value;
	}

	public String get_nombre()
	{
		return  nombre; 
}
	public void  set_nombre(String value)
	{
    nombre=value;
	}

	public String get_paterno()
	{
		return  paterno; 
}
	public void  set_paterno(String value)
	{
    paterno=value;
	}

	public String get_materno()
	{
		return  materno; 
}
	public void  set_materno(String value)
	{
    materno=value;
	}

	public String get_nombre_usuario()
	{
		return  nombre_usuario; 
}
	public void  set_nombre_usuario(String value)
	{
    nombre_usuario=value;
	}

	public String get_contrasena()
	{
		return  contrasena; 
}
	public void  set_contrasena(String value)
	{
    contrasena=value;
	}

	public Boolean get_activo()
	{
		return  activo; 
}
	public void  set_activo(Boolean value)
	{
    activo=value;
	}

   public static void onUpgrade_abs(SQLiteDatabase db, int oldVersion, int newVersion) {

      db.execSQL("DROP TABLE IF EXISTS usuario");
      usuario.onCreate(db);
     }
   public static void onCreate(SQLiteDatabase db) {

 try    { 
   String CreateString=" CREATE TABLE IF NOT EXISTS usuario ("+
"usuario_id INTEGER NOT NULL PRIMARY KEY "+
",nombre TEXT NOT NULL "+
",paterno TEXT"+
",materno TEXT"+
",nombre_usuario TEXT NOT NULL "+
",contrasena TEXT NOT NULL "+
",activo INTEGER NOT NULL "+
")";

  db.execSQL(CreateString);

   }
   catch (Exception ex)
   {
 Log.i("BDException",ex.toString());
   }
   }




 protected void LlenaClase(Cursor dr)
{

  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
if(!dr.isNull(0))  usuario_id= Integer.valueOf(dr.getString(0));  else usuario_id= null;
if(!dr.isNull(1))  nombre= String.valueOf(dr.getString(1));  else nombre= null;
if(!dr.isNull(2))  paterno= String.valueOf(dr.getString(2));  else paterno= null;
if(!dr.isNull(3))  materno= String.valueOf(dr.getString(3));  else materno= null;
if(!dr.isNull(4))  nombre_usuario= String.valueOf(dr.getString(4));  else nombre_usuario= null;
if(!dr.isNull(5))  contrasena= String.valueOf(dr.getString(5));  else contrasena= null;
if(!dr.isNull(6))  activo= dr.getInt(6)!=0;  else activo= null;

}
   public static ArrayList<usuario> GetAll(enm_usuario OrderBy) {
   msgError = "";
   Error = false;

    SQLiteDatabase BD=null;
    ArrayList<usuario> lst=null;
     try {
      BD = SQLiteHelper.get_BDHelper().getWritableDatabase();
        lst = GetAll(OrderBy, BD);
   }
    catch (Exception ex) {
        msgError = ex.toString();
        Error = true;
         lst=null;
    }

   if(BD!=null && BD.isOpen())
   {
       BD.close();
       BD = null;
   }

     return lst;
 }
 public static ArrayList<usuario> GetAll(enm_usuario OrderBy, SQLiteDatabase BD )
{
 msgError="";
 Error=false;
try {
 ArrayList<usuario> lst= new ArrayList<usuario>();
String query= "SELECT * FROM usuario Order by "+ OrderBy; 
Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
do {
usuario obj= new usuario();
 obj.LlenaClase(cursor);
 lst.add(obj);
}
 while(cursor.moveToNext());}
 return lst;
 }
 catch(Exception ex)
{
  msgError= ex.toString();
Error=true;
  return null;
}

}
    public Boolean Save() {
  msgError = "";
  Error = false;

  SQLiteDatabase BD = null;
  Boolean res = null;

   try {
       BD = SQLiteHelper.get_BDHelper().getWritableDatabase();
       res = Save( BD);

   } catch (Exception ex) {
       msgError = ex.toString();
     Error = true;
      res = false;
  }

  if (BD != null && BD.isOpen()) {
      BD.close();
      BD = null;
   }

     return res;
  }
public Boolean Save(SQLiteDatabase BD)
{
 msgError="";
 Error=false;
  try
{
  ContentValues values = new ContentValues();

  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

values.put("nombre", nombre);
if(paterno!=null) values.put("paterno", paterno);
else values.putNull("paterno");

if(materno!=null) values.put("materno", materno);
else values.putNull("materno");

values.put("nombre_usuario", nombre_usuario);
values.put("contrasena", contrasena);
values.put("activo", activo? 1 : 0);

Boolean result=false;
if(usuario_id!=-1)
{
usuario objeto = usuario.GetByID(usuario_id,BD);
 if (objeto != null) 
return BD.update("usuario", values,"usuario_id="+ usuario_id.toString(),null)>0;

}
usuario_id= (int)(long)BD.insert("usuario", null, values);
if(usuario_id>0) result=true; 

return result;
}

catch(Exception ex){
msgError= ex.toString();
Error=true;
 return false;}

}

        public static usuario GetByID(int id)
   {
      msgError = "";
      Error = false;

      SQLiteDatabase BD = null;
      usuario res = null;

      try {
          BD = SQLiteHelper.get_BDHelper().getWritableDatabase();
          res = GetByID(id, BD);

      } catch (Exception ex) {
           msgError = ex.toString();
          Error = true;
           res = null;
      }

       if (BD != null && BD.isOpen()) {
          BD.close();
          BD = null;
     }

    return res;
 }
 public static usuario GetByID(int id, SQLiteDatabase BD)
{
 msgError="";
 Error=false;
 try {
String query= "SELECT * FROM usuario Where usuario_id=" + id;

usuario obj= null;
Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
 obj =  new usuario();
 obj.LlenaClase(cursor);
}
 return obj;
            }catch(Exception ex)
         {
          msgError= ex.toString();
           Error=true;
            return null; 
         }
}
   public static Integer DeleteByID(Integer Id) {
   msgError = "";
    Error = false;

    SQLiteDatabase BD=null;
    int res=-1;

     try 
     {
          BD= SQLiteHelper.get_BDHelper().getWritableDatabase();
         res = DeleteByID(Id, BD);
         
    } catch (Exception ex) {
        msgError = ex.toString();
        Error = true;
     res = -1;
    }

  if(BD!=null && BD.isOpen())
  {
       BD.close();
       BD = null;
   }
               
     return res;
  }

  public static Integer DeleteByID(Integer Id, SQLiteDatabase BD) 
  { 
     msgError = "";
     Error=false;
    try {
       Integer result;
        result = BD.delete("usuario", "usuario_id=" + Id.toString(), null);
        return result;
    } catch (Exception ex)
   {
        msgError= ex.toString();
        Error=true;
       return -1;
   }
 }
   public static Integer DeleteWhere(String Where) {
   msgError = "";
    Error = false;

    SQLiteDatabase BD=null;
    int res=-1;

     try 
     {
          BD= SQLiteHelper.get_BDHelper().getWritableDatabase();
         res = DeleteWhere(Where,BD);
         
    } catch (Exception ex) {
        msgError = ex.toString();
        Error = true;
     res = -1;
    }

  if(BD!=null && BD.isOpen())
  {
       BD.close();
       BD = null;
   }
               
     return res;
  }

  public static Integer DeleteWhere(String  Where, SQLiteDatabase BD) 
  { 
     msgError = "";
     Error=false;
    try {
       Integer result;
        result = BD.delete("usuario", Where , null);
        return result;
    } catch (Exception ex)
   {
        msgError= ex.toString();
        Error=true;
       return -1;
   }
 }
   public static ArrayList<usuario> GetWhere(String Where) {
   msgError = "";
   Error = false;

    SQLiteDatabase BD=null;
    ArrayList lst=null;
     try {
      BD = SQLiteHelper.get_BDHelper().getWritableDatabase();
       lst = GetWhere(Where, BD);
   }
    catch (Exception ex) {
        msgError = ex.toString();
        Error = true;
         lst=null;
    }

   if(BD!=null && BD.isOpen())
   {
       BD.close();
       BD = null;
   }

     return lst;
 }
 public static ArrayList<usuario> GetWhere(String Where, SQLiteDatabase BD)
{
 msgError="";
 Error=false;

 try {
 ArrayList lst= new ArrayList();
String query= "SELECT * FROM usuario Where "+ Where; 

Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
do {
usuario obj= new usuario();
 obj.LlenaClase(cursor);
 lst.add(obj);
 }
while(cursor.moveToNext());}
 return lst;
 }
 catch(Exception ex)
{
  msgError= ex.toString();
Error=true;
  return null;
}

}
public enum enm_usuario {usuario_id,nombre,paterno,materno,nombre_usuario,contrasena,activo}

}