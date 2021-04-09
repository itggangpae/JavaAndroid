package itstudy.kakao.supportlibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "tododb", null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        String memoSQL = "create table tb_todo " +
                "(_id integer primary key autoincrement," +
                "title," +
                "content," +
                "date," +
                "completed)";
        //0 - none, 1 - completed
        db.execSQL(memoSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tb_todo");
        onCreate(db);
    }
}


