package com.example.greensolarenergy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    // Database Version
    private  static final int DATABASE_VERSION = 1;

    //Database Name
    private static  final String DATABASE_NAME = "greenS_E.db";

    //Table Name
    private static final String TABLE_NAME ="raktar";

    private static final String TABLE_NAME2 ="megrendelesek";

    private  static  final String TABLE_NAME3="termekrendeles";

    //Table Fields
    private  static  final String SERIALNUMBER = "Serialnumber";
    private static  final String MEGNEVEZES= "Megnevezes";
    private static  final String DARABSZAM ="Darabszam";
    private  static  final  String AR= "Ar";
    private static final String ELHELYEZKEDES="Elhelyezkedes";

    //Table 2  Fields
    private  static  final String ID = "Id";
    private static  final String MEGRENDELO= "Megrendelo";
    private static  final String Datum ="Datum";
    private  static  final  String osszeg= "Osszeg";

    private static final String Statusz = "Statusz";

    //Table 3
    private  static  final String azon = "Azon";
    private static  final String szemely= "Szemely";
    private static  final String TERMEK ="Termek";
    private  static  final  String DARAB= "Darab";


    private Retrofit retrofit;
    private RetroInterface retroInterface;
    private String BASE_URL = "http://10.0.2.2:3000";


    SQLiteDatabase database;

   public DatabaseHandler(Context context){
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
    this.context = context;
    database = this.getWritableDatabase();

   }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+SERIALNUMBER+" INTEGER PRIMARY KEY, "+MEGNEVEZES+" TEXT,"+DARABSZAM+ " INTEGER, "+AR+" INTEGER,"+ELHELYEZKEDES+" TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_NAME2 + " ( " + ID + " INTEGER PRIMARY KEY, " + MEGRENDELO + " TEXT, " + Datum + " TEXT, " + osszeg + " INTEGER, " + Statusz + " TEXT)");

        db.execSQL("CREATE TABLE "+TABLE_NAME3+" ( "+azon+" INTEGER PRIMARY KEY, "+szemely+" TEXT,"+TERMEK+ " TEXT, "+DARAB+" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);

        onCreate(db);
   }

   //hozzaad a raktarthoz
  void addraktar(String megnevezes,int darab, int ar,String elhelyez){
       SQLiteDatabase db=this.getWritableDatabase();
      ContentValues cv = new ContentValues();

      cv.put(MEGNEVEZES,megnevezes);
      cv.put(DARABSZAM,darab);
      cv.put(AR,ar);
      cv.put(ELHELYEZKEDES,elhelyez);


      long result = db.insert(TABLE_NAME,null,cv);
      if(result == -1){
          Toast.makeText(context,"Sikertelen",Toast.LENGTH_SHORT).show();

      }else{
          Toast.makeText(context,"Sikeres",Toast.LENGTH_SHORT).show();
      }


      retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build();

      retroInterface = retrofit.create(RetroInterface.class);

      HashMap<String, String> map = new HashMap<>();

      map.put("megnevezes", megnevezes);
      map.put("darabszam", Integer.toString(darab));
      map.put("ar", Integer.toString(ar));
      map.put("elhelyezkedes", elhelyez);

      Call<Void> call = retroInterface.execute(map);

      call.enqueue(new Callback<Void>() {
          @Override
          public void onResponse(Call<Void> call, Response<Void> response) {
              if (response.code() == 200) {
                  Toast.makeText(context, "Sikeres", Toast.LENGTH_LONG).show();
              } else {
                  Toast.makeText(context, "Sikertelen", Toast.LENGTH_LONG).show();
              }
          }

          @Override
          public void onFailure(Call<Void> call, Throwable t) {
              Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
          }
      });
   }

   // hozzaad megrendelesek

    void addMegrendeles(String megrendelo, String datum, int arak) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MEGRENDELO, megrendelo);
        cv.put(Datum, datum);
        cv.put(osszeg, arak);
        cv.put(Statusz, "Rögzítve");;

        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Sikertelen", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sikeres", Toast.LENGTH_SHORT).show();
        }

        Log.d("DatabaseHandler", "addMegrendeles result: " + result);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retroInterface = retrofit.create(RetroInterface.class);

        HashMap<String, String> map = new HashMap<>();

        map.put("megrendelo", megrendelo);
        map.put("datum", datum);
        map.put("arak", Integer.toString(arak));

        Call<Void> call = retroInterface.addmegrendelesek(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Sikeres", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Sikertelen", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    //hozzaad a termekrendeleshez


    void addtermekrendel(String megrendelo, String termek, int darab) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(szemely, megrendelo);
        cv.put(TERMEK, termek);
        cv.put(DARAB, darab);

        long result = db.insert(TABLE_NAME3, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Sikertelen", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sikeres", Toast.LENGTH_SHORT).show();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retroInterface = retrofit.create(RetroInterface.class);

        HashMap<String, String> map = new HashMap<>();

        map.put("megrendelo", megrendelo);
        map.put("datum", termek);
        map.put("arak", Integer.toString(darab));

        Call<Void> call = retroInterface.addtermekrendeles(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Sikeres", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Sikertelen", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //kiir

    List<String> getRaktarAdatok() {
        List<String> adatokListaja = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int serialNumber = cursor.getInt(cursor.getColumnIndex(SERIALNUMBER));
                @SuppressLint("Range") String megnevezes = cursor.getString(cursor.getColumnIndex(MEGNEVEZES));
                @SuppressLint("Range") int darabszam = cursor.getInt(cursor.getColumnIndex(DARABSZAM));
                @SuppressLint("Range") int ar = cursor.getInt(cursor.getColumnIndex(AR));
                @SuppressLint("Range") String elhelyezkedes = cursor.getString(cursor.getColumnIndex(ELHELYEZKEDES));
                adatokListaja.add(serialNumber + " " + megnevezes + " " + darabszam + " " + ar + " " + elhelyezkedes);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return adatokListaja;
    }
//
    //törles serialnumber alapjan raktar
    void deleteRaktarData(int serialNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, SERIALNUMBER + "=?", new String[]{String.valueOf(serialNumber)});
        if(result == 0){
            Toast.makeText(context,"Sikertelen",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Sikeres",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }


   // list view megrendelesek

    public Cursor viewData(){
       SQLiteDatabase DB =this.getReadableDatabase();
       String query = "Select * FROM "+ TABLE_NAME2;
       Cursor cursor = DB.rawQuery(query,null);
       return cursor;
    }

    // list view raktar
    public Cursor solarData(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query2 = "Select * FROM "+ TABLE_NAME+ " WHERE "+MEGNEVEZES+" LIKE '%panel%'";
        Cursor cur = db.rawQuery(query2,null);
        return cur;
    }



    // list view raktar
    public Cursor inverterData(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query2 = "Select * FROM "+ TABLE_NAME+ " WHERE "+MEGNEVEZES+" LIKE '%Inverter%'";
        Cursor cur = db.rawQuery(query2,null);
        return cur;
    }

    // list raktar
    public Cursor listraktarinArray(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query2 = "Select * FROM "+ TABLE_NAME;
        Cursor cur = db.rawQuery(query2,null);
        return cur;
    }


    //törles serialnumber alapjan Megrendesesk
    void deleteMegrendelesek(int serialNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME2, ID + "=?", new String[]{String.valueOf(serialNumber)});
        if(result == 0){
            Toast.makeText(context,"Sikertelen",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Sikeres",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void projektinditas(int serialNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Statusz, "Folyamatban");

        int result = db.update(TABLE_NAME2, values, ID + "=?", new String[]{String.valueOf(serialNumber)});

        if(result == 0){
            Toast.makeText(context,"Sikertelen",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Sikeres",Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    void projektlezaras(int serialNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Statusz, "Lezárva");

        int result = db.update(TABLE_NAME2, values, ID + "=?", new String[]{String.valueOf(serialNumber)});

        if(result == 0){
            Toast.makeText(context,"Sikertelen",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Sikeres",Toast.LENGTH_SHORT).show();
        }
        db.close();

    }




    //

    void modifydata(int serialNumber, int price){
        SQLiteDatabase db = this.getWritableDatabase();




        db.execSQL("UPDATE "+TABLE_NAME+" SET AR = "+"'"+price+"' "+ "WHERE SERIALNUMBER = "+"'"+serialNumber+"'");

      /*  if(result == 0){
            Toast.makeText(context,"Sikertelen",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Sikeres",Toast.LENGTH_SHORT).show();
        }*/

        db.close();
    }

}
