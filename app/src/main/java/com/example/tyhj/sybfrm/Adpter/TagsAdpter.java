package com.example.tyhj.sybfrm.Adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by _Tyhj on 2016/8/8.
 */
public class TagsAdpter extends RecyclerView.Adapter<TagsAdpter.MyViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private List<String> datas;
    String[] colors;
    ImageLoader imageLoader;
    int type;
    public TagsAdpter(Context context, List<String> datas,int type){
        this.context=context;
        this.datas=datas;
        this.mInflater=LayoutInflater.from(context);
        imageLoader=ImageLoader.getInstance();
        colors=context.getResources().getStringArray(R.array.color);
        this.type=type;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_tags,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.tvTags.setText(datas.get(position));
        holder.ivTags.setBackgroundColor(Color.parseColor(colors[new Random(System.currentTimeMillis()).nextInt(colors.length)]));
        holder.ivTags.setClipToOutline(true);
        holder.ivTags.setOutlineProvider(MyFunction.getOutline(true,10,0));

        if(type==1){
            holder.ivTags.setImageResource(ics_face[position]);
        }else if(type==2){
            holder.ivTags.setImageResource(ics_back[position]);
        }else if(type==3){
            holder.ivTags.setImageResource(ics_mobile[position]);
        }else if(type==4){
            holder.ivTags.setImageResource(ics_data[position]);
        }else if(type==5){
            holder.ivTags.setImageResource(ics_yun[position]);
        }else if(type==6){
            holder.ivTags.setImageResource(ics_test[position]);
        }else if(type==7){
            holder.ivTags.setImageResource(ics_view[position]);
        }

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
        TextView tvTags;
        ImageButton ivTags;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTags= (TextView) itemView.findViewById(R.id.tv_tags);
            ivTags= (ImageButton) itemView.findViewById(R.id.iv_tags);
        }
    }


    int[] ics_face=new int[]{
            R.drawable.html,R.drawable.javascrip,R.drawable.jquery,R.drawable.node,R.drawable.angular,
            R.drawable.webapp,R.drawable.tools,R.drawable.css,R.drawable.bootstrap,R.drawable.react,
            R.drawable.vue,R.drawable.sass};

    int[] ics_back=new int[]{
            R.drawable.php, R.drawable.java, R.drawable.python, R.drawable.c, R.drawable.c_pro, R.drawable.go,
            R.drawable.c_sharp
    };

    int[] ics_mobile=new int[]{
            R.drawable.android,R.drawable.ios,R.drawable.unity_3d,R.drawable.cocos2d_x
    };

    int[] ics_data=new int[]{
            R.drawable.mysql_in,R.drawable.mongodb,R.drawable.oracle,R.drawable.sqlserver
    };

    int[] ics_yun=new int[]{
            R.drawable.yun,R.drawable.bgdata
    };

    int[] ics_test=new int[]{
            R.drawable.linux,R.drawable.check
    };

    int[] ics_view=new int[]{
            R.drawable.photoshop,R.drawable.pr,R.drawable.maya,R.drawable.zb
    };
}