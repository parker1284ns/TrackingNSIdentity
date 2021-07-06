  package com.nsidentity.tracking.entidades;
 import android.app.Application;
 import android.content.Context;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteOpenHelper;
                import java.text.DateFormat;
              import java.text.SimpleDateFormat;

 
 
     public class SQLiteHelper extends SQLiteOpenHelper {
 
     private static SQLiteHelper _BDHelper=null;
 
     // Database Version
     private static  int DATABASE_VERSION =0;
     // Database Name
    private static  String DATABASE_NAME ="";
 
 
     public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
 
 
    public static void Inicializa(Application app, String databaseName, int dataVersion )
    {
        if(_BDHelper==null)
        {
            SQLiteHelper.DATABASE_VERSION=dataVersion;
            SQLiteHelper.DATABASE_NAME= databaseName;
            _BDHelper= new SQLiteHelper(app);
       }
    }
 
 
    public static void InicializaDatos()
    {
 DateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


   }
 
 
    public static SQLiteHelper get_BDHelper()
    {
        return _BDHelper;
   }
 
 
    @Override
    public void onCreate(SQLiteDatabase db)
     {
configuracion.onCreate(db);
log_tracking.onCreate(db);
usuario.onCreate(db);

    }
 
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
configuracion.onUpgrade(db,oldVersion, newVersion);
log_tracking.onUpgrade(db,oldVersion, newVersion);
usuario.onUpgrade(db,oldVersion, newVersion);
    }
    }

