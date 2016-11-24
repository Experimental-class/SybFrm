package com.example.tyhj.sybfrm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.tyhj.sybfrm.Adpter.TagsAdpter;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.SetUserInfo;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.squareup.picasso.Picasso;
import com.tyhj.myfist_2016_6_29.MyTime;
import com.zzhoujay.richtext.RichText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.ACTION_GET_CONTENT;
import static android.content.Intent.ACTION_OPEN_DOCUMENT;
import static com.example.tyhj.sybfrm.savaInfo.MyFunction.getStringFromInputStream;

@EActivity(R.layout.activity_write_essay)
public class WriteEssay extends AppCompatActivity {
    boolean what;
    Uri imageUri;
    String date, essayUrl;
    Button camoral, images;
    ContentResolver contentResolver;
    String PATH = Environment.getExternalStorageDirectory() + "/SybFrm";
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int PICK_PHOTO = 0;

    TagsAdpter tags_face, tags_back, tags_mobile, tags_data, tags_yun, tags_test, tags_view;

    List<String> ls_face, ls_back, ls_mobile, ls_data, ls_yun, ls_test, ls_view;

    TagsAdpter adpter[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "9TEdV93wArW7MsWdxs68q3SA-gzGzoHsz", "GWOVe9GReFOYa6HlnefJzE98");
        what = getIntent().getBooleanExtra("what", true);
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
            rcyv[i].setAdapter(adpter[i]);
            rcyv[i].setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        }
    }

    @ViewById
    TextView tv_show_essay, tv_title;

    @ViewById
    EditText et_title, edit_text;

    @ViewById
    ImageView iv_showEssay, iv_tags, iv_at, iv_add_image, iv_back, iv_publish;

    @ViewById
    NestedScrollView scrollView;

    @ViewById
    RecyclerView rcyv_face, rcyv_back, rcyv_moblie, rcyv_data, rcyv_yun, rcyv_test, rcyv_view;

    @ViewById
    LinearLayout ll_tags;

    @Click(R.id.iv_showEssay)
    void showMarkDown() {
        if (scrollView.getVisibility() != View.VISIBLE) {
            scrollView.setVisibility(View.VISIBLE);
            RichText.fromMarkdown(et_title.getText().toString() + "\n" + "===" + "\n" + edit_text.getText().toString()).into(tv_show_essay);
        }
    }

    @Click(R.id.iv_back)
    void back() {
        if (what)
            MyFunction.savaEssay(this, et_title.getText().toString(), edit_text.getText().toString());
        this.finish();
    }

    //发表文章
    @Click(R.id.iv_publish)
    void iv_publish() {
        String title = et_title.getText().toString();
        String text = edit_text.getText().toString();
        if (title.equals("") || text.equals("")) {
            Snackbar.make(iv_publish, "标题或者内容不能为空", Snackbar.LENGTH_SHORT).show();
        } else {
            publishEssay(title, text, getTags());
        }
    }

    private String getTags() {
        String tags = "";
        for (int i = 0; i < adpter.length; i++) {
            for (int j = 0; j < adpter[i].getTags().size(); j++) {
                if(j!=adpter[i].getTags().size()-1)
                    tags = tags + adpter[i].getTags().get(j) + ",";
                else
                    tags = tags + adpter[i].getTags().get(j);
            }
        }
        return tags;
    }

    //at
    @Click(R.id.iv_at)
    void iv_at() {

    }

    //tags
    @Click(R.id.iv_tags)
    void addTags() {
        ll_tags.setVisibility(View.VISIBLE);
    }

    //添加图片
    @Click(R.id.iv_add_image)
    void addPhoto() {
        dialog();
    }

    @Background
    void findEyes() {
        /*
        *
        * 查找关注的人
        * */

    }

    @UiThread
    void finishActivity() {
        Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show();
        MyFunction.deleteEssay(this);
        this.finish();
    }

    @UiThread
    void snackBar(String string, int time) {
        Snackbar.make(iv_publish, string, time).show();
    }

    @UiThread
    void setEssayUrl() {
        edit_text.setText(edit_text.getText().toString() + "![](" + essayUrl + ")");
    }

    @AfterViews
    void afterViews() {
        if (!what) {
            tv_title.setText("提问题");
            et_title.setHint("请输入问题");
            edit_text.setHint("输入详细内容以便于解答");
        } else {
            et_title.setText(MyFunction.getEssay(this)[0]);
            edit_text.setText(MyFunction.getEssay(this)[1]);
        }
        contentResolver = getContentResolver();
        initRecycleVies();
    }

    //连接服务器并发表
    @Background
    public void publishEssay(String title, String text, String tags) {
        String log = MyFunction.publishEssay(title, text, tags);
        if (log == null)
            finishActivity();
        else
            snackBar(log,Snackbar.LENGTH_SHORT);
    }

    //选择图片
    private void dialog() {
        AlertDialog.Builder di;
        di = new AlertDialog.Builder(WriteEssay.this);
        di.setCancelable(true);
        LayoutInflater inflater = LayoutInflater.from(WriteEssay.this);
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
                File file = new File(PATH, date);
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
                if (resultCode == WriteEssay.this.RESULT_OK) {
                    File file = new File(PATH, date);
                    cropPhoto(Uri.fromFile(file));
                }
                break;
            //这是从相册返回的数据
            case PICK_PHOTO:
                getDate();
                if (resultCode == WriteEssay.this.RESULT_OK) {
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
                if (resultCode == WriteEssay.this.RESULT_OK) {
                    final String fileName = PATH + "/" + date;
                    File newFile = new File(Environment.getExternalStorageDirectory() + "/SybFrm", date);
                    MyFunction.ImgCompress(fileName, newFile, 00);
                    try {
                        if (!MyFunction.isIntenet(WriteEssay.this))
                            return;
                        AVObject avObject = new AVObject("EssayImage");
                        final AVFile file = AVFile.withAbsoluteLocalPath("Essay.JPEG", fileName);
                        avObject.put("image", file);
                        avObject.put("name", MyFunction.getUserInfo().getId() + date);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    getImageUrl();
                                } else {
                                    MyFunction.Toast(WriteEssay.this, e.getMessage());
                                }
                            }
                        });
                    } catch (Exception e) {
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
                new File(PATH + "/" + date).delete();
                if (essayUrl != null)
                    setEssayUrl();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (scrollView.getVisibility() == View.VISIBLE)
            scrollView.setVisibility(View.GONE);
        else if (ll_tags.getVisibility() == View.VISIBLE)
            ll_tags.setVisibility(View.GONE);

        else {
            if (what)
                MyFunction.savaEssay(this, et_title.getText().toString(), edit_text.getText().toString());
            super.onBackPressed();
        }
    }
}
