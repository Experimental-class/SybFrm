package com.example.tyhj.sybfrm.Adpter;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.activity.ShowEssay;
import com.example.tyhj.sybfrm.activity.ShowEssay_;
import com.example.tyhj.sybfrm.info.Essay;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
    public void onBindViewHolder(final EssayViewHolder holder, final int position) {
        holder.ivEssayImage.setVisibility(View.VISIBLE);
        String headImage=essayList.get(position).getUserHeadImageUrl();
        final boolean[] turn = {false};
        final Essay essay=essayList.get(position);

        //Log.e("Tag","赞"+essay.getAgree()+essay.isZan()+"    收藏"+essay.getCollect()+essay.isCollect());

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

        setIv_like(holder, essay);

        setIv_Zan(holder, essay);

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

        final int[] likes = {Integer.parseInt(essay.getAgree()) + Integer.parseInt(essay.getCollect())};
        holder.tsLikesCounter.setText(likes[0] +" ");


        final ObjectAnimator animator=(ObjectAnimator) AnimatorInflater.loadAnimator(context,
                R.animator.likes);
        final ObjectAnimator animator2=(ObjectAnimator) AnimatorInflater.loadAnimator(context,
                R.animator.notlikes);

        final AnimatedVectorDrawable[] mAnimatedVectorDrawable = new AnimatedVectorDrawable[1];


        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        setIv_Zan(holder,essay);
                        if(essay.isZan())
                            likes[0]++;
                        else
                            likes[0]--;
                        holder.tsLikesCounter.setText(likes[0]+" ");
                        break;
                    case 2:
                        if(essay.isCollect())
                            likes[0]++;
                        else
                            likes[0]--;
                        holder.tsLikesCounter.setText(likes[0]+" ");
                        setIv_like(holder,essay);
                        break;
                }
            }
        };
        holder.iv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(essay.isCollect()){
                    mAnimatedVectorDrawable[0] = (AnimatedVectorDrawable) context.
                            getDrawable(R.drawable.essaynotcollect);
                    likes[0]--;
                    holder.tsLikesCounter.setText(likes[0]+" ");
                    essay.setCollect(false);
                }else {
                    mAnimatedVectorDrawable[0] = (AnimatedVectorDrawable) context.
                            getDrawable(R.drawable.essaycollect);
                    likes[0]++;
                    holder.tsLikesCounter.setText(likes[0]+" ");
                    essay.setCollect(true);

                }
                if(!turn[0]){
                    animator.setTarget(holder.iv_collect);
                    animator.start();
                }else {
                    animator2.setTarget(holder.iv_collect);
                    animator2.start();
                }
                holder.iv_collect.setImageDrawable(mAnimatedVectorDrawable[0]);
                mAnimatedVectorDrawable[0].start();
                turn[0] =!turn[0];
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if(!MyFunction.collectEsy(essay.getE_id(),essay.isCollect())) {
                            essay.setCollect(!essay.isCollect());
                            handler.sendEmptyMessage(2);
                        }
                    }
                }).start();
            }
        });
        setClick(holder, essay, likes, animator, animator2,mAnimatedVectorDrawable, handler);
    }






    //点击事件
    private void setClick(final EssayViewHolder holder, final Essay essay, final int[] likes,
                          final ObjectAnimator animator, final ObjectAnimator animator2,
                          final AnimatedVectorDrawable[] mAnimatedVectorDrawable, final Handler handler) {
        holder.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(essay.isZan()){
                    holder.iv_zan.setImageResource(R.drawable.zan);
                    likes[0]--;
                    holder.tsLikesCounter.setText(likes[0] +" ");
                    essay.setZan(false);
                } else {
                    holder.iv_zan.setImageResource(R.drawable.zaned);
                    likes[0]++;
                    holder.tsLikesCounter.setText(likes[0] +" ");
                    essay.setZan(true);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(!MyFunction.ZanEsy(essay.getE_id(),essay.isZan())){
                            essay.setZan(!essay.isZan());
                            handler.sendEmptyMessage(1);
                        }

                    }
                }).start();
            }
        });

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

    private void setIv_like(EssayViewHolder holder, Essay essay) {
        if(essay.isCollect())
            holder.iv_collect.setImageResource(R.drawable.icmecollected);
        else
            holder.iv_collect.setImageResource(R.drawable.icmecollect);
    }

    private void setIv_Zan(EssayViewHolder holder, Essay essay) {
        if(essay.isZan())
            holder.iv_zan.setImageResource(R.drawable.zaned);
        else
            holder.iv_zan.setImageResource(R.drawable.zan);
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
        ImageView iv_userHeadImage, ivEssayImage,iv_zan,iv_Left,iv_collect;
        TextView tv_userName, tvEssayTitle, tvEssayBody;
        TextSwitcher tsLikesCounter;

        public EssayViewHolder(View view) {
            super(view);
            cdvEssay = (CardView) view.findViewById(R.id.cdvEssay);
            iv_userHeadImage = (ImageView) view.findViewById(R.id.iv_userHeadImage);
            ivEssayImage = (ImageView) view.findViewById(R.id.ivEssayImage);
            tv_userName = (TextView) view.findViewById(R.id.tv_userName);
            tvEssayTitle = (TextView) view.findViewById(R.id.tvEssayTitle);
            tvEssayBody = (TextView) view.findViewById(R.id.tvEssayBody);
            iv_zan= (ImageView) view.findViewById(R.id.iv_zan);
            iv_Left= (ImageView) view.findViewById(R.id.iv_Left);
            iv_collect= (ImageView) view.findViewById(R.id.iv_collect);
            tsLikesCounter= (TextSwitcher) view.findViewById(R.id.tsLikesCounter);
            tsLikesCounter.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    TextView t = new TextView(context);
                    t.setGravity(Gravity.CENTER);
                    t.setTextColor(Color.parseColor("#2C5B84"));
                    return t;
                }
            });
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
