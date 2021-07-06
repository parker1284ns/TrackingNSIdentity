package com.nsidentity.tracking.entidades;


import android.database.sqlite.SQLiteDatabase;


 public class usuario extends usuario_abs implements ObjectItem
{

  @Override
   public String toString()
  {
   return super.toString(); 
 }
 
  public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
 {
     usuario_abs.onUpgrade_abs(db,oldVersion, newVersion);
  }

           @Override
public String getString()
{
	return toString();
}

@Override
public int getId() {
	// TODO Auto-generated method stub
	return this.get_usuario_id();
}
 

}
