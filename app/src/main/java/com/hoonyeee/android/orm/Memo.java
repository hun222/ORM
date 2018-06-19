package com.hoonyeee.android.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "memo")
public class Memo {
    @DatabaseField(generatedId = true)  // 자동증가하는 필드
    int id;
    @DatabaseField
    String memo;
}
