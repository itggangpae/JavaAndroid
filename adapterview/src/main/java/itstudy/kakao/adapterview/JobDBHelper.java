package itstudy.kakao.adapterview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JobDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public JobDBHelper(Context context){
        super(context, "jobdb", null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableSql="create table job_data ("+
                "_id integer primary key autoincrement," +
                "name text not null," +
                "content text)";
        db.execSQL(tableSql);
        db.execSQL("insert into job_data (name, content) values ('SI','외부 시스템 개발')");
        db.execSQL("insert into job_data (name, content) values ('SM','시스템 유지 보수')");
        db.execSQL("insert into job_data (name, content) values ('QA','품질 관리 및 테스트')");
        db.execSQL("insert into job_data (name, content) values ('Back-End','서버 프로그램 개발')");
        db.execSQL("insert into job_data (name, content) values ('Front-End','클라이언트 프로그램 개발')");
        db.execSQL("insert into job_data (name, content) values ('Full-Stack','애플리케이션 전체 개발')");
        db.execSQL("insert into job_data (name, content) values ('DevOps','개발환경 과 운영환경 구축')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table job_data");
            onCreate(db);
        }
    }
}
