package com.example.simplecursoradapterapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH; //полный путь к БД
    private static String DB_NAME="cityinfo.db"; //название БД
    private static final int SCHEMA=1; //версия БД
    static final String TABLE="users"; //название таблицы в БД

    //название стобцов
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_YEAR="year";
    private Context myContext;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH=context.getFilesDir().getPath()+DB_NAME; //получаем путь к БД приложения который выделяется по умолчанию
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    void create_db(){
        InputStream myInput=null; //поток входящий для того чтобы забрать БД из папки assets
        OutputStream myOutput=null; //поток исходящий для того чтобы положить БД по штатному адресу
        try{
            File file=new File(DB_PATH); //создаем объект файла БД по адресу
            if (!file.exists()){ //если файл не существует то
               // this.getReadableDatabase(); //получаем БД приложения для чтения

                //получаем локальную бд как поток
                myInput=myContext.getAssets().open(DB_NAME);
                //Путь к новой БД
                String outFileName=DB_PATH;
                //Открываем пустую бд
                myOutput=new FileOutputStream(outFileName);

                //побайтово копируем данные
                byte[] buffer=new byte[1024];
                int length;
                while ((length=myInput.read(buffer))>0){
                    myOutput.write(buffer,0,length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch (IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }

    }
    public SQLiteDatabase open() throws SQLException{
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
