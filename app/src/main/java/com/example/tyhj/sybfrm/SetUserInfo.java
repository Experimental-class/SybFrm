package com.example.tyhj.sybfrm;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.Adpter.TagsAdpter;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.ACTION_GET_CONTENT;
import static com.example.tyhj.sybfrm.activity.WriteEssay.PICK_PHOTO;

@EActivity(R.layout.activity_set_user_info)
public class SetUserInfo extends AppCompatActivity {
    boolean ifMessageChange;
    File headImagefile = null;
    Animation animation;
    Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    int LINES = 0;
    String date, essayUrl;
    Button camoral, images;
    ContentResolver contentResolver;
    String path = Environment.getExternalStorageDirectory() + "/SybFrm";
    String fileName;
    SimpleAdapter simpleAdapter;
    List<String> tags = new ArrayList<>();

    TagsAdpter tags_face, tags_back, tags_mobile, tags_data, tags_yun, tags_test, tags_view;

    List<String> ls_face, ls_back, ls_mobile, ls_data, ls_yun, ls_test, ls_view;

    TagsAdpter adpter[];

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animation = AnimationUtils.loadAnimation(this, R.anim.bottombarup);
        contentResolver = getContentResolver();

        if (MyFunction.getUserInfo().getTags() != null)
            for (int i = 0; i < MyFunction.getUserInfo().getTags().length; i++) {
                tags.add(MyFunction.getUserInfo().getTags()[i]);
            }

        initTags();

    }

    private void initTags() {
        ls_face = new ArrayList<String>();
        ls_test = new ArrayList<String>();
        ls_data = new ArrayList<String>();
        ls_mobile = new ArrayList<String>();
        ls_back = new ArrayList<String>();
        ls_view = new ArrayList<String>();
        ls_yun = new ArrayList<String>();

        String tag_face[] = getResources().getStringArray(R.array.tags_face);
        String tag_back[] = getResources().getStringArray(R.array.tags_back);
        String tag_mobile[] = getResources().getStringArray(R.array.tags_mobile);
        String tag_data[] = getResources().getStringArray(R.array.tags_date);
        String tag_yun[] = getResources().getStringArray(R.array.tags_yun);
        String tag_test[] = getResources().getStringArray(R.array.tags_test);
        String tag_view[] = getResources().getStringArray(R.array.tags_view);

        for (int i = 0; i < tag_back.length; i++) {
            ls_back.add(tag_back[i]);
        }
        for (int i = 0; i < tag_data.length; i++) {
            ls_data.add(tag_data[i]);
        }
        for (int i = 0; i < tag_face.length; i++) {
            ls_face.add(tag_face[i]);
        }
        for (int i = 0; i < tag_mobile.length; i++) {
            ls_mobile.add(tag_mobile[i]);
        }
        for (int i = 0; i < tag_test.length; i++) {
            ls_test.add(tag_test[i]);
        }
        for (int i = 0; i < tag_view.length; i++) {
            ls_view.add(tag_view[i]);
        }
        for (int i = 0; i < tag_yun.length; i++) {
            ls_yun.add(tag_yun[i]);
        }
    }

    @ViewById
    LinearLayout ll_show_to_sava;

    @ViewById
    Button btn_savaChangeInfo;

    @ViewById
    ImageView iv_userHeadImage;

    @ViewById
    ImageButton iv_back;

    @ViewById
    CardView cdvIfSave;

    @ViewById
    EditText et_name, et_Signature, et_blog, et_github;

    @ViewById
    LinearLayout ll_tags;

    @ViewById
    TextView tvEmail, tvReputation, tv_userName;

    @ViewById
    RecyclerView rcyv_tags;

    @ViewById
    RecyclerView rcyv_face, rcyv_back, rcyv_moblie, rcyv_data, rcyv_yun, rcyv_test, rcyv_view;

    @Click(R.id.iv_userHeadImage)
    void changeHeadImage() {
        if (!MyFunction.istour())
            dialog();
        else
            mToast(iv_userHeadImage, "游客身份没有该权限", Snackbar.LENGTH_SHORT);
    }

    @Click(R.id.btn_savaChangeInfo)
    void save() {
        savaToCloud(headImagefile);
    }

    @Click(R.id.iv_close)
    void close() {
        ll_tags.setVisibility(View.GONE);
        updateTags();
    }

    @UiThread
    void mToast(View view, String str, int time) {
        Snackbar.make(view, str, time).show();
    }

    @UiThread
    void finishActivity() {
        this.finish();
    }

    @Background
    void savaHeadImage() {
        AVFile file = null;
        try {
            file = AVFile.withAbsoluteLocalPath("headImage.JPEG", fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MyFunction.setUserHeadImage(MyFunction.getUserInfo().getId(), file);
    }

    @Background
    void savaToCloud(File file) {
        /*
        * 保存信息
        *
        *
        */

        mToast(tv_userName, "已保存", Snackbar.LENGTH_INDEFINITE);
        finishActivity();
    }

    @Click(R.id.iv_back)
    void back() {
        upTodate();
        this.finish();
    }

    @Click(R.id.ll_setTags)
    void chose() {
        ll_tags.setVisibility(View.VISIBLE);
    }

    @AfterViews
    void afterViews() {
        iv_userHeadImage.setClipToOutline(true);
        iv_userHeadImage.setOutlineProvider(MyFunction.getOutline(true, 10, 0));
        getImageUrl();
        initView();
        initRecycleVies();
        setTagsAdpter();
    }

    private void setTagsAdpter() {
        simpleAdapter = new SimpleAdapter(SetUserInfo.this, tags);
        rcyv_tags.setAdapter(simpleAdapter);
        if (tags.size() / 4 + 1 <= 4)
            LINES = tags.size() / 4 + 1;
        else
            LINES = 4;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(LINES, StaggeredGridLayoutManager.HORIZONTAL);
        rcyv_tags.setLayoutManager(staggeredGridLayoutManager);
        rcyv_tags.setItemAnimator(new DefaultItemAnimator());
    }

    private void initRecycleVies() {
        tags_face = new TagsAdpter(this, ls_face, 1);
        tags_back = new TagsAdpter(this, ls_back, 2);
        tags_mobile = new TagsAdpter(this, ls_mobile, 3);
        tags_data = new TagsAdpter(this, ls_data, 4);
        tags_yun = new TagsAdpter(this, ls_yun, 5);
        tags_test = new TagsAdpter(this, ls_test, 6);
        tags_view = new TagsAdpter(this, ls_view, 7);
        RecyclerView rcyv[] = {rcyv_face, rcyv_back, rcyv_moblie, rcyv_data, rcyv_yun, rcyv_test, rcyv_view};
        adpter = new TagsAdpter[]{tags_face, tags_back, tags_mobile, tags_data, tags_yun, tags_test, tags_view};
        for (int i = 0; i < rcyv.length; i++) {
            adpter[i].initTags();
            rcyv[i].setAdapter(adpter[i]);
            rcyv[i].setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        }
    }

    private void initView() {
        UserInfo userInfo = MyFunction.getUserInfo();
        if (userInfo != null) {
            tv_userName.setText(userInfo.getName());
            et_name.setText(userInfo.getName());
            et_Signature.setText(userInfo.getSignature());
            tvEmail.setText(userInfo.getEmail());
            tvReputation.setText(userInfo.getReputation());
            et_blog.setText(userInfo.getBlog());
            et_github.setText(userInfo.getGithub());
        }
    }

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
                getDate();
                File file = new File(path, date);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
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
                if (resultCode == SetUserInfo.this.RESULT_OK) {
                    File file = new File(path, date);
                    cropPhoto(Uri.fromFile(file));
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
                    try {
                        MyFunction.copyFile(new File(path_pre), newFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cropPhoto(Uri.fromFile(newFile));
                }
                break;
            //剪裁图片返回数据,就是原来的文件
            case CROP_PHOTO:
                if (resultCode == SetUserInfo.this.RESULT_OK) {
                    fileName = path + "/" + date;
                    File newFile = new File(Environment.getExternalStorageDirectory() + "/SybFrm", date);
                    MyFunction.ImgCompress(fileName, newFile, 1000);
                    if (!MyFunction.isIntenet(SetUserInfo.this))
                        return;
                    savaHeadImage();
                    iv_userHeadImage.setImageDrawable(Drawable.createFromPath(fileName));
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
        AVQuery<AVObject> query = new AVQuery<>("HeadImage");
        query.whereEqualTo("userId", MyFunction.getUserInfo().getId());
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                // object 就是符合条件的第一个 AVObject
                if (e == null && avObject != null) {
                    essayUrl = avObject.getAVFile("headImage").getUrl();
                    if (essayUrl != null)
                        Picasso.with(SetUserInfo.this).load(essayUrl).into(iv_userHeadImage);
                    else
                        Picasso.with(SetUserInfo.this).load(R.mipmap.girl).into(iv_userHeadImage);
                } else
                    Picasso.with(SetUserInfo.this).load(R.mipmap.girl).into(iv_userHeadImage);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (ll_tags.getVisibility() == View.VISIBLE) {
            ll_tags.setVisibility(View.GONE);
            updateTags();
        } else {
            upTodate();
            super.onBackPressed();
        }
    }

    private void updateTags() {
        tags.clear();
        simpleAdapter.notifyDataSetChanged();
        for (int i = 0; i < adpter.length; i++)
            for (int j = 0; j < adpter[i].getTags().size(); j++) {
                String newTag=adpter[i].getTags().get(j).trim();
                if ( newTag!= null&&!newTag.equals("")&&!tags.contains(newTag))
                    tags.add(0, adpter[i].getTags().get(j));
            }
        if (tags.size() / 4 + 1 <= 4)
            LINES = tags.size() / 4 + 1;
        else
            LINES = 4;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(LINES, StaggeredGridLayoutManager.HORIZONTAL);
        rcyv_tags.setLayoutManager(staggeredGridLayoutManager);
    }

    @Background
    void upTodate() {
        if (!MyFunction.isIntenet(this))
            return;
        String tag = "";
        for (int i = 0; i < tags.size(); i++) {
            if(!tags.get(i).equals(""))
            tag = tag + tags.get(i) + ",";
        }
        MyFunction.updateUser(et_blog.getText().toString().trim() + ""
                , et_github.getText().toString().trim() + ""
                , tag, et_Signature.getText().toString().trim() + "");
    }
}
