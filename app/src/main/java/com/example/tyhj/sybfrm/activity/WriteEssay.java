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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    String path = Environment.getExternalStorageDirectory() + "/SybFrm";
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int PICK_PHOTO=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "9TEdV93wArW7MsWdxs68q3SA-gzGzoHsz", "GWOVe9GReFOYa6HlnefJzE98");
        what = getIntent().getBooleanExtra("what", true);
    }


    @ViewById
    TextView tv_show_essay, tv_title;
    @ViewById
    EditText et_title, edit_text;
    @ViewById
    ImageView iv_showEssay, iv_tags, iv_at, iv_add_image, iv_back, iv_publish;
    @ViewById
    NestedScrollView scrollView;

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
            publishEssay(title,text,"tags");
        }
    }

    //at
    @Click(R.id.iv_at)
    void iv_at() {

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
    @android.support.annotation.UiThread
    void snackBar(String string,int time){
        Snackbar.make(iv_publish,string,time).show();
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
    }

    //连接服务器并发表
    @Background
    public void publishEssay(String title ,String text,String tags){

        try {
            HttpURLConnection conn = null;
            String url = getString(R.string.url)+"/t/add";
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            String data =  "u_id=" + MyFunction.getUserInfo().getId() + "&u_psw=" + MyFunction.getUserInfo().getPassword()+"&t_title="+title+"&t_text="+text+"&t_tags="+tags;
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String state = getStringFromInputStream(is);
                JSONObject jsonObject=new JSONObject(state);
                if(jsonObject.getInt("code")==1){
                    finishActivity();
                }else {
                    snackBar(jsonObject.getString("codeState")+MyFunction.getUserInfo().getPassword(),Snackbar.LENGTH_SHORT);
                }
            } else {
                Log.i("TAG", "访问失败" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取tags
    @Background
    void getTags(){

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
                File file=new File(path,date);
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
                    File file=new File(path,date);
                    File newFile = new File(path, date);
                    MyFunction.ImgCompress(file.getAbsolutePath(), newFile,100);
                    cropPhoto(Uri.fromFile(newFile));
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
                    MyFunction.ImgCompress(path_pre, newFile,100);
                    cropPhoto(Uri.fromFile(newFile));
                }
                break;
            //剪裁图片返回数据,就是原来的文件
            case CROP_PHOTO:
                if (resultCode == WriteEssay.this.RESULT_OK) {
                    final String fileName = path + "/" + date;
                    File newFile = new File(Environment.getExternalStorageDirectory() + "/SybFrm", date);
                    MyFunction.ImgCompress(fileName, newFile,100);

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
                if (essayUrl != null)
                    setEssayUrl();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (scrollView.getVisibility() == View.VISIBLE)
            scrollView.setVisibility(View.GONE);
        else {
            if (what)
                MyFunction.savaEssay(this, et_title.getText().toString(), edit_text.getText().toString());
            super.onBackPressed();
        }
    }
}
