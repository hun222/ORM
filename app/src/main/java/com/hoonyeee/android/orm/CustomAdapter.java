package com.hoonyeee.android.orm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{
    List<Memo> data;
    Database db;
    ItemInterface itemInterface;

    public CustomAdapter(Database db, ItemInterface itemInterface){
        this.db = db;
        this.itemInterface = itemInterface;
    }

    public void setData(List<Memo> data){
        this.data = data;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout,parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // 1.데이터를 먼저 꺼낸다
        Memo memo = data.get(position);
        // 2.데이터를 세팅한다.
        holder.setId(memo.id);
        holder.setNo(memo.id);
        holder.setContent(memo.memo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemInterface{
        public void setItem(Memo memo);
    }
    public class Holder extends RecyclerView.ViewHolder{
        private int id;
        TextView text_no, text_content;
        Button btn_x;
        public Holder(View itemView) {
            super(itemView);
            text_no = itemView.findViewById(R.id.text_no);

            // 수정처리
            text_content = itemView.findViewById(R.id.text_content);
            text_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Memo memo = new Memo();
                    memo.id = Holder.this.id;
                    memo.memo = Holder.this.text_content.getText().toString();
                    itemInterface.setItem(memo);
                }
            });

            // 삭제처리
            btn_x = itemView.findViewById(R.id.btn_x);
            btn_x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 현재 아이템 삭제
                    Memo deleteMemo = null;
                    for(Memo memo : data){
                        if(memo.id == Holder.this.id){
                            deleteMemo = memo;
                            break;
                        }
                    }
                    data.remove(deleteMemo);
                    db.delete(deleteMemo.id);
                    notifyDataSetChanged();
                }
            });
        }
        public void setId(int id){
            this.id = id;
        }
        public void setNo(int no){
            text_no.setText(no+"");
        }
        public void setContent(String content){
            text_content.setText(content);
        }
    }
}
