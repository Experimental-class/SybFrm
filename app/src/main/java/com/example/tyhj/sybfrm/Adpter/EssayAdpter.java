package com.example.tyhj.sybfrm.Adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.activity.ShowEssay;
import com.example.tyhj.sybfrm.activity.ShowEssay_;
import com.example.tyhj.sybfrm.info.Essay;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Tyhj on 2016/10/3.
 */

public class EssayAdpter extends RecyclerView.Adapter<EssayAdpter.EssayViewHolder> {
    private static String TEXTTYPE_FANGTING_BLACK="fonts/huakang_black.TTF";
    private static String TEXTTYPE_YANHEI="fonts/yahei.ttf";
    Typeface typefaceYahei,typefaceLanting;
    private LayoutInflater mInflater;
    List<Essay> essayList;
    Context context;

    public EssayAdpter(Context context,List<Essay> essayList){
        this.context=context;
        this.essayList=essayList;
        this.mInflater=LayoutInflater.from(context);
        typefaceLanting=Typeface.createFromAsset(context.getAssets(),TEXTTYPE_FANGTING_BLACK);
        typefaceYahei=Typeface.createFromAsset(context.getAssets(),TEXTTYPE_YANHEI);
    }


    @Override
    public EssayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_essay,parent,false);
        EssayViewHolder essayViewHolder=new EssayViewHolder(view);
        return essayViewHolder;
    }

    @Override
    public void onBindViewHolder(EssayViewHolder holder, int position) {
        Picasso.with(context).load(essayList.get(position).getUserHeadImageUrl()).into(holder.iv_userHeadImage);
        holder.iv_userHeadImage.setClipToOutline(true);
        holder.iv_userHeadImage.setOutlineProvider(MyFunction.getOutline(true,10,0));

        Picasso.with(context).load(essayList.get(position).getEssayImageUrl()).into(holder.ivEssayImage);
        holder.ivEssayImage.setClipToOutline(true);
        holder.ivEssayImage.setOutlineProvider(MyFunction.getOutline(false,100,8));

        holder.tv_userName.setText(essayList.get(position).getUserName());

        holder.tvEssayTitle.setText(essayList.get(position).getEssayTitle());
        TextPaint tp = holder.tvEssayTitle.getPaint();
        tp.setFakeBoldText(true);
        //holder.tvEssayTitle.setTypeface(typefaceLanting);

        holder.tvEssayBody.setText(essayList.get(position).getEssayBody());
        holder.tvEssayBody.setTypeface(typefaceYahei);

        holder.tv_agree.setText(essayList.get(position).getAgree()+"赞");
        holder.tv_remark.setText(essayList.get(position).getRemark()+"评论");
        holder.tv_collect.setText(essayList.get(position).getCollect()+"收藏");
        holder.cdvEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ShowEssay_.class));
            }
        });
    }
    @Override
    public int getItemCount() {
        return essayList.size();
    }


    public void addItem(Essay essay){
        essayList.add(0,essay);
        notifyItemInserted(0);
    }

    public void deleteItem(Essay essay){
        Essay essay1=null;
        for(int i=0;i<essayList.size();i++){
            essay1=essayList.get(i);
            if(essay1.getId().equals(essay.getId())){
                essayList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }


    class EssayViewHolder extends RecyclerView.ViewHolder{
        CardView cdvEssay;
        ImageView iv_userHeadImage,ivEssayImage;
        TextView tv_userName,tvEssayTitle,tvEssayBody,tv_agree,tv_remark,tv_collect;
        public EssayViewHolder(View view) {
            super(view);
            cdvEssay= (CardView) view.findViewById(R.id.cdvEssay);
            iv_userHeadImage= (ImageView) view.findViewById(R.id.iv_userHeadImage);
            ivEssayImage= (ImageView) view.findViewById(R.id.ivEssayImage);
            tv_userName= (TextView) view.findViewById(R.id.tv_userName);
            tvEssayTitle= (TextView) view.findViewById(R.id.tvEssayTitle);
            tvEssayBody= (TextView) view.findViewById(R.id.tvEssayBody);
            tv_agree= (TextView) view.findViewById(R.id.tv_agree);
            tv_remark= (TextView) view.findViewById(R.id.tv_remark);
            tv_collect= (TextView) view.findViewById(R.id.tv_collect);
        }
    }
}
