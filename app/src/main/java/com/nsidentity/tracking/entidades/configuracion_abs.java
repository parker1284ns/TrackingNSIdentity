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
  public abstract class configuracion_abs extends Object implements Serializable {

    public static String msgError="";
    public static Boolean Error=false;

 public  configuracion_abs(){  

  }
	protected Integer configuracion_id = -1;
	protected String parametro ="";
	protected String valor ="";
	protected String descripcion=null;


	public Integer get_configuracion_id()
	{
		return  configuracion_id; 
}
	public void  set_configuracion_id(Integer value)
	{
    configuracion_id=value;
	}

	public String get_parametro()
	{
		return  parametro; 
}
	public void  set_parametro(String value)
	{
    parametro=value;
	}

	public String get_valor()
	{
		return  valor; 
}
	public void  set_valor(String value)
	{
    valor=value;
	}

	public String get_descripcion()
	{
		return  descripcion; 
}
	public void  set_descripcion(String value)
	{
    descripcion=value;
	}

   public static void onUpgrade_abs(SQLiteDatabase db, int oldVersion, int newVersion) {

      db.execSQL("DROP TABLE IF EXISTS configuracion");
      configuracion.onCreate(db);
     }
   public static void onCreate(SQLiteDatabase db) {

 try    { 
   String CreateString=" CREATE TABLE IF NOT EXISTS configuracion ("+
"configuracion_id INTEGER NOT NULL PRIMARY KEY "+
",parametro TEXT NOT NULL "+
",valor TEXT NOT NULL "+
",descripcion TEXT"+
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
if(!dr.isNull(0))  configuracion_id= Integer.valueOf(dr.getString(0));  else configuracion_id= null;
if(!dr.isNull(1))  parametro= String.valueOf(dr.getString(1));  else parametro= null;
if(!dr.isNull(2))  valor= String.valueOf(dr.getString(2));  else valor= null;
if(!dr.isNull(3))  descripcion= String.valueOf(dr.getString(3));  else descripcion= null;

}
   public static ArrayList<configuracion> GetAll(enm_configuracion OrderBy) {
   msgError = "";
   Error = false;

    SQLiteDatabase BD=null;
    ArrayList<configuracion> lst=null;
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
 public static ArrayList<configuracion> GetAll(enm_configuracion OrderBy, SQLiteDatabase BD )
{
 msgError="";
 Error=false;
try {
 ArrayList<configuracion> lst= new ArrayList<configuracion>();
String query= "SELECT * FROM configuracion Order by "+ OrderBy; 
Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
do {
configuracion obj= new configuracion();
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

values.put("parametro", parametro);
values.put("valor", valor);
if(descripcion!=null) values.put("descripcion", descripcion);
else values.putNull("descripcion");


Boolean result=false;
if(configuracion_id!=-1)
{
configuracion objeto = configuracion.GetByID(configuracion_id,BD);
 if (objeto != null) 
return BD.update("configuracion", values,"configuracion_id="+ configuracion_id.toString(),null)>0;

}
configuracion_id= (int)(long)BD.insert("configuracion", null, values);
if(configuracion_id>0) result=true; 

return result;
}

catch(Exception ex){
msgError= ex.toString();
Error=true;
 return false;}

}

        public static configuracion GetByID(int id)
   {
      msgError = "";
      Error = false;

      SQLiteDatabase BD = null;
      configuracion res = null;

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
 public static configuracion GetByID(int id, SQLiteDatabase BD)
{
 msgError="";
 Error=false;
 try {
String query= "SELECT * FROM configuracion Where configuracion_id=" + id;

configuracion obj= null;
Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
 obj =  new configuracion();
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
        result = BD.delete("configuracion", "configuracion_id=" + Id.toString(), null);
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
        result = BD.delete("configuracion", Where , null);
        return result;
    } catch (Exception ex)
   {
        msgError= ex.toString();
        Error=true;
       return -1;
   }
 }
   public static ArrayList<configuracion> GetWhere(String Where) {
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
 public static ArrayList<configuracion> GetWhere(String Where, SQLiteDatabase BD)
{
 msgError="";
 Error=false;

 try {
 ArrayList lst= new ArrayList();
String query= "SELECT * FROM configuracion Where "+ Where; 

Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
do {
configuracion obj= new configuracion();
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
public enum enm_configuracion {configuracion_id,parametro,valor,descripcion}

}