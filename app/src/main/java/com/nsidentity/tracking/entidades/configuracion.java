package com.nsidentity.tracking.entidades;


import android.database.sqlite.SQLiteDatabase;



 public class configuracion extends configuracion_abs implements ObjectItem
{

  @Override
   public String toString()
  {
   return super.toString(); 
 }
 
  public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
 {
     configuracion_abs.onUpgrade_abs(db,oldVersion, newVersion);
  }

           @Override
public String getString()
{
	return toString();
}

@Override
public int getId() {
	// TODO Auto-generated method stub
	return this.get_configuracion_id();
}
 

}
