package com.hoonyeee.android.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "memo.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Memo.class);
        } catch (SQLException e) {
            Log.e("Database",e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public void insert(Memo memo){
        try {
            Dao<Memo, Integer> dao = getDao(Memo.class);
            dao.create(memo);
        }catch (Exception e){
            Log.e("Database",e.getMessage());
        }
    }

    public List<Memo> readAll(){
        List<Memo> result = null;
        try {
            Dao<Memo, Integer> dao = getDao(Memo.class);
            result = dao.queryForAll();
        }catch (Exception e){
            Log.e("Database",e.getMessage());
        }
        return result;
    }

    // 특정 데이터 검색하기
    public List<Memo> search(String word){
        List<Memo> result = null;
        try {
            Dao<Memo, Integer> dao = getDao(Memo.class);
            String query = "select * from memo where memo like '%"+word+"%'";
            GenericRawResults<Memo> rawResult = dao.queryRaw(query, dao.getRawRowMapper());
            result = rawResult.getResults();
        }catch (Exception e){
            Log.e("Database",e.getMessage());
        }
        return result;
    }

    public void update(Memo memo){
        try {
            Dao<Memo, Integer> dao = getDao(Memo.class);
            dao.update(memo);
        }catch (Exception e){
            Log.e("Database",e.getMessage());
        }
    }

    public void delete(int id){
        try {
            Dao<Memo, Integer> dao = getDao(Memo.class);
            dao.deleteById(id);
        }catch (Exception e){
            Log.e("Database",e.getMessage());
        }
    }
}
