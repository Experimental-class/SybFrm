package com.example.tyhj.sybfrm.Adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.activity.ShowEssay;
import com.example.tyhj.sybfrm.activity.ShowEssay_;
import com.example.tyhj.sybfrm.info.Essay;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by Tyhj on 2016/10/3.
 */

public class EssayAdpter extends RecyclerView.Adapter<EssayAdpter.EssayViewHolder> {
    private static String TEXTTYPE_FANGTING_BLACK = "fonts/huakang_black.TTF";
    private static String TEXTTYPE_YANHEI = "fonts/yahei.ttf";
    Typeface typefaceYahei, typefaceLanting;
    private LayoutInflater mInflater;
    List<Essay> essayList;
    Context context;
    ImageLoader imageLoader;
    public EssayAdpter(final Context context, List<Essay> essayList) {
        this.context = context;
        this.essayList = essayList;
        this.mInflater = LayoutInflater.from(context);
        imageLoader=ImageLoader.getInstance();
        typefaceLanting = Typeface.createFromAsset(context.getAssets(), TEXTTYPE_FANGTING_BLACK);
        typefaceYahei = Typeface.createFromAsset(context.getAssets(), TEXTTYPE_YANHEI);
    }


    @Override
    public EssayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_essay, parent, false);
        EssayViewHolder essayViewHolder = new EssayViewHolder(view);
        return essayViewHolder;
    }

    @Override
    public void onBindViewHolder(EssayViewHolder holder, int position) {
        holder.ivEssayImage.setVisibility(View.VISIBLE);
        String headImage=essayList.get(position).getUserHeadImageUrl();

        final Essay essay=essayList.get(position);

        if (headImage != null)
            imageLoader.displayImage(headImage, holder.iv_userHeadImage, MyFunction.getOption());
        else
            Picasso.with(context).load(R.mipmap.girl).into(holder.iv_userHeadImage);


        holder.iv_userHeadImage.setClipToOutline(true);
        holder.iv_userHeadImage.setOutlineProvider(MyFunction.getOutline(true, 10, 0));

        String imgeUrl = essay.getEssayImageUrl();

        if(imgeUrl!=null)
            imageLoader.displayImage(imgeUrl, holder.ivEssayImage, MyFunction.getOption());
        else
            holder.ivEssayImage.setVisibility(View.GONE);

        holder.ivEssayImage.setClipToOutline(true);
        holder.ivEssayImage.setOutlineProvider(MyFunction.getOutline(false, 100, 8));

        holder.tv_userName.setText(essay.getUserName());

        holder.tvEssayTitle.setText(essay.getEssayTitle());
        TextPaint tp = holder.tvEssayTitle.getPaint();
        tp.setFakeBoldText(true);
        //holder.tvEssayTitle.setTypeface(typefaceLanting);
        String text = essay.getEssayBody();
        if (text.contains("![](http://")) {
            text = text.substring(0, text.indexOf("![](http://")) + text.substring(text.indexOf(".JPEG)") + 6, text.length());
        }
        holder.tvEssayBody.setText(text);
        holder.tvEssayBody.setTypeface(typefaceYahei);

        holder.tv_agree.setText(essay.getAgree() + "赞");
        holder.tv_remark.setText(essay.getRemark() + "评论");
        holder.tv_collect.setText(essay.getCollect() + "收藏");

        startAct(holder, essay);
    }

    private void startAct(EssayViewHolder holder, final Serializable essay) {
        holder.ivEssayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowEssay_.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("essay", essay);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.iv_userHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowEssay_.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("essay", essay);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowEssay_.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("essay", essay);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.tv_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowEssay_.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("essay", essay);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.tvEssayBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowEssay_.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("essay", essay);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.tvEssayTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowEssay_.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("essay", essay);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return essayList.size();
    }


    public void addItem(Essay essay) {
        essayList.add(0, essay);
        notifyItemInserted(0);
    }

    public void deleteItem(Essay essay) {
        Essay essay1 = null;
        for (int i = 0; i < essayList.size(); i++) {
            essay1 = essayList.get(i);
            if (essay1.getE_id().equals(essay.getE_id())) {
                essayList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }


    class EssayViewHolder extends RecyclerView.ViewHolder {
        CardView cdvEssay;
        ImageView iv_userHeadImage, ivEssayImage;
        TextView tv_userName, tvEssayTitle, tvEssayBody, tv_agree, tv_remark, tv_collect;

        public EssayViewHolder(View view) {
            super(view);
            cdvEssay = (CardView) view.findViewById(R.id.cdvEssay);
            iv_userHeadImage = (ImageView) view.findViewById(R.id.iv_userHeadImage);
            ivEssayImage = (ImageView) view.findViewById(R.id.ivEssayImage);
            tv_userName = (TextView) view.findViewById(R.id.tv_userName);
            tvEssayTitle = (TextView) view.findViewById(R.id.tvEssayTitle);
            tvEssayBody = (TextView) view.findViewById(R.id.tvEssayBody);
            tv_agree = (TextView) view.findViewById(R.id.tv_agree);
            tv_remark = (TextView) view.findViewById(R.id.tv_remark);
            tv_collect = (TextView) view.findViewById(R.id.tv_collect);
        }
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
