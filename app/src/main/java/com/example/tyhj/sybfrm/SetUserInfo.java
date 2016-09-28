package com.example.tyhj.sybfrm;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyhj.sybfrm.savaInfo.MyInfo;
import com.squareup.picasso.Picasso;
import com.tyhj.myfist_2016_6_29.MyTime;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import custom.MyFunction;

@EActivity(R.layout.activity_set_user_info)
public class SetUserInfo extends AppCompatActivity {

    Animation animation;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animation= AnimationUtils.loadAnimation(this,R.anim.bottombarup);
    }


    @ViewById
    LinearLayout ll_show_to_sava;

    @ViewById
    Button btn_savaChangeInfo,btn_noSavaChangeInfo;

    @ViewById
    ImageView iv_userHeadImage;

    @ViewById
    CardView cdvIfSave;

    @ViewById
    EditText et_name,et_Signature,et_blog,et_github;

    @ViewById
    TextView tvId,tvEmail,tvReputation,tv_userName;

    @Click(R.id.iv_userHeadImage)
    void changeHeadImage(){
        dialog();
    }

    @AfterViews
    void afterViews(){
        iv_userHeadImage.setClipToOutline(true);
        iv_userHeadImage.setOutlineProvider(MyFunction.getOutline(true,10));
        Picasso.with(this).load("http://ac-fgtnb2h8.clouddn.com/21d88c8102759c96ecdf.jpg").into(iv_userHeadImage);

        clik();

    }

    private void clik() {
        animation.setAnimationListener(listener);
        et_name.addTextChangedListener(textWatcher);
        et_Signature.addTextChangedListener(textWatcher);
        et_blog.addTextChangedListener(textWatcher);
        et_github.addTextChangedListener(textWatcher);
    }

    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(cdvIfSave.getVisibility()!=View.VISIBLE){
                cdvIfSave.startAnimation(animation);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    Animation.AnimationListener listener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            cdvIfSave.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    int WHERE_PHOTO = 0;
    String date;
    Button camoral, images;
    // 上传用户头像
    private void dialog() {
        AlertDialog.Builder di;
        di = new AlertDialog.Builder(SetUserInfo.this);
        di.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(SetUserInfo.this);
        View layout = inflater.inflate(R.layout.headchoose, null);
        di.setView(layout);
        final Dialog dialog=di.show();
        camoral = (Button) layout.findViewById(R.id.camoral);
        images = (Button) layout.findViewById(R.id.images);
        // 相机
        camoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                File f1 = new File(Environment.getExternalStorageDirectory()+"/SybFrm");
                if(!f1.exists()){
                    f1.mkdirs();
                }
                File outputImage = new File(Environment
                        .getExternalStorageDirectory()+"/SybFrm", "head.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                WHERE_PHOTO = 1;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        // 相册
        images.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
                WHERE_PHOTO = 2;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == SetUserInfo.this.RESULT_OK) {
                    if (data != null) {
                        imageUri = data.getData();
                    }
                    SimpleDateFormat df = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    MyTime myTime=new MyTime();
                    date =myTime.getYear()+myTime.getMonth_()+myTime.getDays()+
                            myTime.getWeek_()+myTime.getHour()+myTime.getMinute()+
                            myTime.getSecond()+ MyInfo.getUserInfo().getName()+".JPEG";
                    Bitmap bitmap= null;
                    try {
                        bitmap = BitmapFactory.decodeStream(SetUserInfo.this.getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    MyInfo.saveBitmapFile(bitmap,date,SetUserInfo.this);

                    File file=new File(Environment.getExternalStorageDirectory()+"/SybFrm",date);
                    //换图片
                    imageUri = Uri.fromFile(file);
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == SetUserInfo.this.RESULT_OK) {
                    try {
                        Picasso.with(SetUserInfo.this).load(imageUri).into(iv_userHeadImage);
                        String path=Environment.getExternalStorageDirectory()+"/SybFrm";
                        WHERE_PHOTO = 0;
                        if(!MyInfo.isIntenet(SetUserInfo.this))
                            return;
                        final File file=new File(path,date);
                        final String fileName=MyInfo.getUserInfo().getId()+".JPEG";
                        /*
                        *
                        * 保存图片
                        并且保存URL到数据库
                        */
                        cdvIfSave.startAnimation(animation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
