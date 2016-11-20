package com.example.tyhj.sybfrm.Adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;

import java.util.List;
import java.util.Random;

/**
 * Created by _Tyhj on 2016/8/8.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private List<String> datas;
    String[] colors;
    public SimpleAdapter(Context context, List<String> datas){
        this.context=context;
        this.datas=datas;
        this.mInflater=LayoutInflater.from(context);
        colors=context.getResources().getStringArray(R.array.color);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.textView.setText(datas.get(position));
        holder.textView.setClipToOutline(true);
        holder.textView.setBackgroundColor(Color.parseColor(colors[new Random(System.currentTimeMillis()).nextInt(colors.length)]));
        holder.textView.setOutlineProvider(MyFunction.getOutline(false,10,30));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(int pos){
        datas.add(pos,"insert One");
        notifyItemInserted(pos);
    }
    public void deleteData(int pos){
        datas.remove(pos);
        notifyItemRemoved(pos);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.text);
        }
    }
}