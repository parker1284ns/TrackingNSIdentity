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
  public abstract class log_tracking_abs extends Object implements Serializable {

    public static String msgError="";
    public static Boolean Error=false;

 public  log_tracking_abs(){  

  }
	protected Integer log_tracking_id = -1;
	protected String imei=null;
	protected Double latitud=null;
	protected Double longitud=null;
	protected Date fecha_registro=null;
	protected Integer cuenta_id=null;


	public Integer get_log_tracking_id()
	{
		return  log_tracking_id; 
}
	public void  set_log_tracking_id(Integer value)
	{
    log_tracking_id=value;
	}

	public String get_imei()
	{
		return  imei; 
}
	public void  set_imei(String value)
	{
    imei=value;
	}

	public Double get_latitud()
	{
		return  latitud; 
}
	public void  set_latitud(Double value)
	{
    latitud=value;
	}

	public Double get_longitud()
	{
		return  longitud; 
}
	public void  set_longitud(Double value)
	{
    longitud=value;
	}

	public Date get_fecha_registro()
	{
		return  fecha_registro; 
}
	public void  set_fecha_registro(Date value)
	{
    fecha_registro=value;
	}

	public Integer get_cuenta_id()
	{
		return  cuenta_id; 
}
	public void  set_cuenta_id(Integer value)
	{
    cuenta_id=value;
	}

   public static void onUpgrade_abs(SQLiteDatabase db, int oldVersion, int newVersion) {

      db.execSQL("DROP TABLE IF EXISTS log_tracking");
      log_tracking.onCreate(db);
     }
   public static void onCreate(SQLiteDatabase db) {

 try    { 
   String CreateString=" CREATE TABLE IF NOT EXISTS log_tracking ("+
"log_tracking_id INTEGER NOT NULL PRIMARY KEY "+
",imei TEXT"+
",latitud REAL"+
",longitud REAL"+
",fecha_registro TEXT"+
",cuenta_id INTEGER"+
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
if(!dr.isNull(0))  log_tracking_id= Integer.valueOf(dr.getString(0));  else log_tracking_id= null;
if(!dr.isNull(1))  imei= String.valueOf(dr.getString(1));  else imei= null;
if(!dr.isNull(2))  latitud= Double.valueOf(dr.getString(2));  else latitud= null;
if(!dr.isNull(3))  longitud= Double.valueOf(dr.getString(3));  else longitud= null;
if(!dr.isNull(4)) {
 try {
 fecha_registro = df.parse(dr.getString(4)); 
 }  catch (ParseException e) {  }
}else fecha_registro= null;
if(!dr.isNull(5))  cuenta_id= Integer.valueOf(dr.getString(5));  else cuenta_id= null;

}
   public static ArrayList<log_tracking> GetAll(enm_log_tracking OrderBy) {
   msgError = "";
   Error = false;

    SQLiteDatabase BD=null;
    ArrayList<log_tracking> lst=null;
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
 public static ArrayList<log_tracking> GetAll(enm_log_tracking OrderBy, SQLiteDatabase BD )
{
 msgError="";
 Error=false;
try {
 ArrayList<log_tracking> lst= new ArrayList<log_tracking>();
String query= "SELECT * FROM log_tracking Order by "+ OrderBy; 
Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
do {
log_tracking obj= new log_tracking();
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

if(imei!=null) values.put("imei", imei);
else values.putNull("imei");

if(latitud!=null) values.put("latitud", latitud);
else values.putNull("latitud");

if(longitud!=null) values.put("longitud", longitud);
else values.putNull("longitud");

if(fecha_registro!=null) values.put("fecha_registro", df.format(fecha_registro.getTime()));
else values.putNull("fecha_registro");

if(cuenta_id!=null) values.put("cuenta_id", cuenta_id);
else values.putNull("cuenta_id");


Boolean result=false;
if(log_tracking_id!=-1)
{
log_tracking objeto = log_tracking.GetByID(log_tracking_id,BD);
 if (objeto != null) 
return BD.update("log_tracking", values,"log_tracking_id="+ log_tracking_id.toString(),null)>0;

}
log_tracking_id= (int)(long)BD.insert("log_tracking", null, values);
if(log_tracking_id>0) result=true; 

return result;
}

catch(Exception ex){
msgError= ex.toString();
Error=true;
 return false;}

}

        public static log_tracking GetByID(int id)
   {
      msgError = "";
      Error = false;

      SQLiteDatabase BD = null;
      log_tracking res = null;

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
 public static log_tracking GetByID(int id, SQLiteDatabase BD)
{
 msgError="";
 Error=false;
 try {
String query= "SELECT * FROM log_tracking Where log_tracking_id=" + id;

log_tracking obj= null;
Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
 obj =  new log_tracking();
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
        result = BD.delete("log_tracking", "log_tracking_id=" + Id.toString(), null);
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
        result = BD.delete("log_tracking", Where , null);
        return result;
    } catch (Exception ex)
   {
        msgError= ex.toString();
        Error=true;
       return -1;
   }
 }
   public static ArrayList<log_tracking> GetWhere(String Where) {
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
 public static ArrayList<log_tracking> GetWhere(String Where, SQLiteDatabase BD)
{
 msgError="";
 Error=false;

 try {
 ArrayList lst= new ArrayList();
String query= "SELECT * FROM log_tracking Where "+ Where; 

Cursor cursor = BD.rawQuery(query, null);
if (cursor.moveToFirst()) {
do {
log_tracking obj= new log_tracking();
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
public enum enm_log_tracking {log_tracking_id,imei,latitud,longitud,fecha_registro,cuenta_id}

}