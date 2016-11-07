package com.example.tyhj.sybfrm;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.tyhj.sybfrm.info.UserInfo;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.squareup.picasso.Picasso;
import com.tyhj.myfist_2016_6_29.MyTime;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileNotFoundException;

import static android.content.Intent.ACTION_GET_CONTENT;
import static com.example.tyhj.sybfrm.activity.WriteEssay.PICK_PHOTO;

@EActivity(R.layout.activity_set_user_info)
public class SetUserInfo extends AppCompatActivity {
    boolean ifMessageChange;
    File headImagefile=null;
    Animation animation;
    Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    String date,essayUrl;
    Button camoral, images;
    ContentResolver contentResolver;
    String path = Environment.getExternalStorageDirectory() + "/SybFrm";
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animation= AnimationUtils.loadAnimation(this,R.anim.bottombarup);
        contentResolver=getContentResolver();
    }

    @ViewById
    LinearLayout ll_show_to_sava;

    @ViewById
    Button btn_savaChangeInfo;

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
        if(!MyFunction.istour())
            dialog();
        else
            mToast(iv_userHeadImage,"游客身份没有该权限",Snackbar.LENGTH_SHORT);
    }

    @Click(R.id.btn_savaChangeInfo)
    void save(){
        savaToCloud(headImagefile);
    }

    @AfterViews
    void afterViews(){
        iv_userHeadImage.setClipToOutline(true);
        iv_userHeadImage.setOutlineProvider(MyFunction.getOutline(true,10,0));
        Picasso.with(this).load(MyFunction.getUserInfo().getUrl()).into(iv_userHeadImage);
        initView();
        clik();
    }

    @UiThread
    void mToast(View view,String str,int time){
        Snackbar.make(view,str,time).show();
    }

    @UiThread
    void finishActivity(){
        this.finish();
    }

    @Background
    void savaToCloud(File file){
        /*
        * 保存信息
        *
        *
        */

        mToast(tv_userName,"已保存",Snackbar.LENGTH_INDEFINITE);
        finishActivity();
    }

    private void initView() {
        UserInfo userInfo=MyFunction.getUserInfo();
        if(userInfo!=null) {
            tvId.setText(userInfo.getId());
            tv_userName.setText(userInfo.getName());
            et_name.setText(userInfo.getName());
            et_Signature.setText(userInfo.getSignature());
            tvEmail.setText(userInfo.getEmail());
            tvReputation.setText(userInfo.getReputation());
            et_blog.setText(userInfo.getBlog());
            et_github.setText(userInfo.getGithub());
        }
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

        }

        @Override
        public void afterTextChanged(Editable s) {
            ifMessageChange=true;
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
    Animation.AnimationListener listener2=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            cdvIfSave.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
    // 上传用户头像
    //选择图片
    private void dialog() {
        AlertDialog.Builder di;
        di = new AlertDialog.Builder(SetUserInfo.this);
        di.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(SetUserInfo.this);
        View layout = inflater.inflate(R.layout.headchoose, null);
        di.setView(layout);
        final Dialog dialog = di.show();
        camoral = (Button) layout.findViewById(R.id.camoral);
        images = (Button) layout.findViewById(R.id.images);
        // 相机
        camoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        // 相册
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, PICK_PHOTO);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //这是从相机返回的数据
            case TAKE_PHOTO:
                getDate();
                if (resultCode == SetUserInfo.this.RESULT_OK) {
                    if (data != null) {
                        imageUri = data.getData();
                    }
                    String path_pre = MyFunction.getFilePathFromContentUri(imageUri, contentResolver);
                    File newFile = new File(Environment.getExternalStorageDirectory() + "/SybFrm", date);
                    MyFunction.ImgCompress(path_pre, newFile);
                    cropPhoto(Uri.fromFile(newFile));
                }
                break;
            //这是从相册返回的数据
            case PICK_PHOTO:
                getDate();
                if (resultCode == SetUserInfo.this.RESULT_OK) {
                    if (data != null) {
                        imageUri = data.getData();
                    }
                    String path_pre = MyFunction.getFilePathFromContentUri(imageUri, contentResolver);
                    File newFile = new File(Environment.getExternalStorageDirectory() + "/SybFrm", date);
                    MyFunction.ImgCompress(path_pre, newFile);
                    cropPhoto(Uri.fromFile(newFile));
                }
                break;
            //剪裁图片返回数据,就是原来的文件
            case CROP_PHOTO:
                if (resultCode == SetUserInfo.this.RESULT_OK) {
                    final String fileName = path + "/" + date;
                    File newFile = new File(Environment.getExternalStorageDirectory() + "/SybFrm", date);
                    MyFunction.ImgCompress(fileName, newFile);
                        if (!MyFunction.isIntenet(SetUserInfo.this))
                            return;
                    AVObject avObject = new AVObject("EssayImage");
                    final AVFile file;
                    try {
                        file = AVFile.withAbsoluteLocalPath("Essay.JPEG", fileName);
                        avObject.put("image", file);
                        avObject.put("name", MyFunction.getUserInfo().getId() + date);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    getImageUrl();
                                } else {
                                    MyFunction.Toast(SetUserInfo.this, e.getMessage());
                                }
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void cropPhoto(Uri imageUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);
    }
    public void getDate() {
        MyTime myTime = new MyTime();
        date = myTime.getYear() + myTime.getMonth_() + myTime.getDays() +
                myTime.getWeek_() + myTime.getHour() + myTime.getMinute() +
                myTime.getSecond() + MyFunction.getUserInfo().getName() + ".JPEG";
    }
    //获取图片URl
    public void getImageUrl() {
        AVQuery<AVObject> query = new AVQuery<>("EssayImage");
        query.whereEqualTo("name", MyFunction.getUserInfo().getId() + date);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                // object 就是符合条件的第一个 AVObject
                essayUrl = avObject.getAVFile("image").getUrl();
                if (essayUrl != null)
                  Picasso.with(SetUserInfo.this).load(essayUrl).into(iv_userHeadImage);
            }
        });
    }
    @Override
    public void onBackPressed() {

        if(!MyFunction.istour()&&ifMessageChange&&cdvIfSave.getVisibility()!=View.VISIBLE) {
            cdvIfSave.startAnimation(animation);
        }else
            super.onBackPressed();
    }

}
