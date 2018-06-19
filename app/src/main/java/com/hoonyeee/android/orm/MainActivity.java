package com.hoonyeee.android.orm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CustomAdapter.ItemInterface {
    Database db;
    CustomAdapter adapter;
    RecyclerView recyclerView;
    Button btn_write;
    EditText edit_memo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        btn_write = findViewById(R.id.btn_write);
        edit_memo = findViewById(R.id.edit_memo);

        btn_write.setOnClickListener(this);

        // 1. data 불러오기
        db = new Database(this);

        // 2. data 세팅
        List<Memo> data = db.readAll();
        Log.e("Database",data.size()+"");
        adapter = new CustomAdapter(db, this);
        adapter.setData(data);

        // 3. recyclerview adapter 연결
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_write:
                String text = edit_memo.getText().toString();
                if (!text.equals("")) {
                    if (mode == MODE_WRTIE) {
                        Memo memo = new Memo();
                        memo.memo = edit_memo.getText().toString();
                        db.insert(memo);
                        // refresh
                        setDataRefresh();
                    }else if(mode == MODE_UPDATE){
                        // 수정 버튼이 클릭되면 값을 변경
                        currentMemo.memo = text;
                        db.update(currentMemo);
                        // 리스트 화면 갱신
                        setDataRefresh();
                        // 메인 화면 write 모드로 갱신
                        mode = MODE_WRTIE;
                        btn_write.setText("Write");
                    }
                } else {
                        Toast.makeText(getBaseContext(), "memo must be inserted", Toast.LENGTH_SHORT).show();
                }
                edit_memo.setText("");

                break;
        }
    }

    private void setDataRefresh(){
        // 값이 입력되면 data를 갱신하고 adapter에 알려준 후, adapter도 갱신
        List<Memo> data = db.readAll();
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    private static final int MODE_WRTIE = 0;
    private static final int MODE_UPDATE = 1;
    int mode = MODE_WRTIE;
    Memo currentMemo = null;
    @Override
    public void setItem(Memo memo) {
        mode = MODE_UPDATE;
        currentMemo = memo;
        btn_write.setText("Modify");
        edit_memo.setText(currentMemo.memo);
    }
}
