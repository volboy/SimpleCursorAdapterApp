package com.example.simplecursoradapterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView userList;
    TextView header;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header=findViewById(R.id.header);
        userList=findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper=new DatabaseHelper(getApplicationContext());
        //создаем базу данных
        databaseHelper.create_db();
    }

    @Override
    public void onResume(){
        super.onResume();
        //открываем подключение
        db=databaseHelper.open();

        //получаем данные из БД в виде курсора
        userCursor=db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        //определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers=new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};

        //создаем адаптер, передаем в него курсор
        userAdapter=new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, userCursor, headers,
                new int[]{android.R.id.text1, android.R.id.text2}, 0);

        header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
        userList.setAdapter(userAdapter);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        userCursor.close();
    }
    public void add(View view){
        Intent intent=new Intent(this, UserActivity.class);
        startActivity(intent);
    }
}
