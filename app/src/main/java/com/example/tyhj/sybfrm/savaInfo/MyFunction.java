package com.example.tyhj.sybfrm.savaInfo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.example.tyhj.sybfrm.Login;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.info.Essay;
import com.example.tyhj.sybfrm.info.UserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Tyhj on 2016/9/23.
 */

public class MyFunction {
    static String TAG = "Myfuction";

    private static String URL = "http://139.129.24.151:5000";

    private static int IMAGE_SIZE = 100;

    public static UserInfo userInfo;

    public static Essay essay;

    private static boolean istour = true;

    public static boolean istour() {
        return istour;
    }

    public static void setIstour(boolean istour) {
        MyFunction.istour = istour;
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        MyFunction.userInfo = userInfo;
    }

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        MyFunction.URL = URL;
    }

    public static Essay getEssay() {
        return essay;
    }

    public static void setEssay(Essay essay) {
        MyFunction.essay = essay;
    }

    //展示图片的设置
    public static DisplayImageOptions getOption() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.girl)
                //.showImageOnFail(R.mipmap.girl)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    //Url转Drawable
    public static Drawable loadImageFromNetwork(String imageUrl) {
        if (imageUrl == null)
            return null;
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            //Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }
        return drawable;
    }

    //Url转bitmap
    public static Bitmap url2Bitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    //是否有网络
    public static boolean isIntenet(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if (wifi || internet) {
            return true;
        } else {
            MyFunction.Toast(context, context.getString(R.string.nointernet));
            return false;
        }
    }

    //是否有WIFI
    public static boolean isWifi(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        return con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
    }

    //Toast
    public static void Toast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    //保存bitmap为File
    public static void saveBitmapFile(Bitmap bm, String name, Context context) {
        if (bm == null)
            return;
        File f1 = new File(Environment.getExternalStorageDirectory() + context.getString(R.string.savaphotopath));
        if (!f1.exists()) {
            f1.mkdirs();
        }
        File imageFile = new File(Environment.getExternalStorageDirectory() + context.getString(R.string.savaphotopath), name);
        int options = 80;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > IMAGE_SIZE && options >= 5) {
            baos.reset();
            options -= 5;
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //轮廓
    public static ViewOutlineProvider getOutline(boolean b, final int x, final int y) {
        if (b) {
            return new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    //outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                    outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        } else {
            return new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, y);
                    //outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }

    }

    //读取文本
    public static String readToBuffer(int filePath, Context context) throws IOException {
        StringBuffer buffer = new StringBuffer();
        final InputStream stream = context.getResources().openRawResource(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        stream.close();
        return buffer.toString();
    }

    //用户登录
    public static String Login(String data, String pas, Context context) {
        JSONObject jsonObject = getJson(data, URL + "/sign_in");
        if (jsonObject != null) {
            try {
                if (jsonObject.getInt("code") == 1) {
                    UserInfo userInfo = new UserInfo(
                            jsonObject.getString("u_id"),
                            MyFunction.getUserHeadImage(jsonObject.getString("u_id")),
                            jsonObject.getString("u_name"),
                            jsonObject.getString("u_email"),
                            jsonObject.getString("u_intro"),
                            jsonObject.getString("u_reputation"),
                            jsonObject.getString("u_blog"),
                            jsonObject.getString("u_github"));
                    userInfo.setPassword(pas);
                    String tags[] = jsonObject.getString("u_tags").split(",", 0);
                    if(!tags[0].equals(""))
                        userInfo.setTags(tags);
                    new SharedData(context).saveUser(userInfo);
                    MyFunction.setUserInfo(userInfo);
                    return null;
                } else {
                    return jsonObject.getString("codeState");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "后台很菜，服务器崩溃了，请稍后再试";
    }

    //保存文章到本地
    public static void savaEssay(Context context, String title, String text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveEssay", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("title", title);
        editor.putString("text", text);
        editor.commit();
    }

    //删除本地文章
    public static void deleteEssay(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveEssay", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.clear();
        editor.commit();
    }

    //获取本地文章
    public static String[] getEssay(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveEssay", MODE_PRIVATE);
        String title = sharedPreferences.getString("title", null);
        String text = sharedPreferences.getString("text", null);
        return new String[]{title, text};
    }

    //Uri转File
    public static String getFilePathFromContentUri(Uri uri, ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = contentResolver.query(uri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    //文件复制
    public static void copyFile(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            if(inputChannel!=null)
                inputChannel.close();
            if(outputChannel!=null)
                outputChannel.close();
        }
    }


    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //图片压缩
    public static void ImgCompress(String filePath, File newFile, int kb) {
        int imageMg = 100;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        //规定要压缩图片的分辨率
        options.inSampleSize = calculateInSampleSize(options, 720, 1280);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, imageMg, baos);
        //如果文件大于100KB就进行质量压缩，每次压缩比例增加百分之五
        while (baos.toByteArray().length / 1024 > kb && imageMg > 50) {
            baos.reset();
            imageMg -= 5;
            bitmap.compress(Bitmap.CompressFormat.JPEG, imageMg, baos);
        }
        //然后输出到指定的文件中
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        // 一定要写len=is.read(buffer)
        // 如果while((is.read(buffer))!=-1)则无法将数据写入buffer中
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }

    //获取用户信息
    public static boolean doPostToGetUserInfo(Context context) {
        try {
            int time=0;
            String url = URL + "/u/query";
            String id=MyFunction.getUserInfo().getId();
            String data = "u_id=" + id;
            JSONObject jsonObject=getJson_GET(data,url);
                if (jsonObject!=null&&jsonObject.getInt("code") == 1) {
                    //Log.e("Tag",jsonObject.toString());
                    String headImage = MyFunction.getUserHeadImage(id);
                    while (headImage==null&&time<10){
                        //Log.e("while","我正在飞"+time);
                        time++;
                        headImage = MyFunction.getUserHeadImage(id);
                    }
                    UserInfo userInfo = new UserInfo(
                            id,
                            headImage,
                            jsonObject.getString("u_name"),
                            jsonObject.getString("u_email"),
                            jsonObject.getString("u_intro"),
                            jsonObject.getString("u_reputation"),
                            jsonObject.getString("u_blog"),
                            jsonObject.getString("u_github"));
                    userInfo.setPassword(getUserInfo().getPassword());
                    String tags[] = jsonObject.getString("u_tags").split(",", 0);
                    if(!tags[0].equals("null")){
                        /*List list = Arrays.asList(tags);
                        Set set = new HashSet(list);
                        tags= (String[]) set.toArray();*/
                        userInfo.setTags(tags);
                    }
                    MyFunction.setUserInfo(userInfo);
                    //Log.e("Info",userInfo.toString());
                    new SharedData(context).saveUser(userInfo);
                    return true;
                } else {
                    //Log.e("Tag",jsonObject.getString("codeState"));
                }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    //查询用户信息
    public static UserInfo getUserInfo(String id) throws JSONException {
        try {
            String url = URL + "/u/query";
            String data = "u_id=" + id;
                JSONObject jsonObject = getJson_GET(data,url);
                if(jsonObject==null)
                    return null;
                if (jsonObject.getInt("code") == 1) {
                    UserInfo userInfo1 = new UserInfo(
                            id,
                            MyFunction.getUserHeadImage(id),
                            jsonObject.getString("u_name"),
                            jsonObject.getString("u_email"),
                            jsonObject.getString("u_intro"),
                            jsonObject.getString("u_reputation"),
                            jsonObject.getString("u_blog"),
                            jsonObject.getString("u_github"));
                    //Log.e("TAG",userInfo1.toString());
                    return userInfo1;
                } else
                    Log.e("TAG", jsonObject.getString("codeState"));
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    //获取文章id组
    public static String[] getEssayId(int type) {
        String url = URL + "/t/display";
        String data="";
        String tags[]=MyFunction.getUserInfo().getTags();
        if(type==2) {
            for(int i=0;i<tags.length;i++){
                if(i!=tags.length-1)
                    data=data+tags[i]+",";
                else
                    data=data+tags[i];
            }
            data = "t_tags=" +data.toLowerCase()+ "&show_count=" + 200;
            //Log.e("TAGs",data);
        }else
            data = "show_count=" + 200;
        JSONObject jsonObject = getJson_GET(data, url);
        try {
            if (jsonObject!=null&&jsonObject.getInt("code") == 1) {
                String str = jsonObject.getString("t_ids");
                String[] strings = str.split("&");
                if(strings==null||strings.length==0||strings[0]==null||strings[1]==null)
                    return null;
                String[] strings1 = strings[0].split(",");
                String[] strings2 = strings[1].split(",");
                if (type == 0)
                    return strings1;
                else
                    return strings2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取信息
    public static JSONObject getJson(String data, String url) {
        HttpURLConnection conn = null;
        URL mURL = null;
        try {
            mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String state = getStringFromInputStream(is);
                //Log.e("Tag",state);
                JSONObject jsonObject = new JSONObject(state);
                if (jsonObject != null)
                    return jsonObject;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //获取信息
    public static JSONObject getJson_GET(String data, String url) {
        HttpURLConnection conn = null;
        URL mURL = null;
        try {
            mURL = new URL(url+"?"+data);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
                InputStream is = conn.getInputStream();
                String state = getStringFromInputStream(is);
                //Log.e("Tag",state);
                JSONObject jsonObject = new JSONObject(state);
                if (jsonObject != null)
                    return jsonObject;
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取文章
    public static Essay getEssay(String id) throws JSONException {
        String url = URL + "/t/query";
        JSONObject json = getJson_GET("t_id=" + id, url);
        if (json == null||json.getInt("code")!=1)
            return null;
        else {
            //Log.e("Essay",json.toString());
            String str = json.getString("t_text");
            String essayUrl = null;
            if (str.length() > 10 && str.contains("![](http://"))
                essayUrl = str.substring(str.indexOf("![](http://") + 4, str.indexOf(".JPEG)") + 5);
            //Log.e("TAg",str+"xxxxxx"+essayUrl+"");
            if(MyFunction.getUserInfo(json.getString("u_id"))==null)
                return null;
            Essay essay = new Essay(
                    MyFunction.getUserHeadImage(json.getString("u_id")),
                    MyFunction.getUserInfo(json.getString("u_id")).getName(),
                    essayUrl,
                    json.getString("t_title"),
                    json.getString("t_text"),
                    json.getString("t_like"),
                    json.getString("t_star"),
                    ifCollectOrCommand(0, id),
                    ifCollectOrCommand(1, id),
                    json.getString("t_date_latest"),
                    json.getString("t_id"),
                    json.getString("u_id")
            );
            //Log.e("EssayInfo",essay.toString());
            return essay;
        }
    }

    //获取用户头像
    public static String getUserHeadImage(String id) {
        AVQuery<AVObject> query = new AVQuery<>("HeadImage");
        query.whereEqualTo("userId", id);
        try {
            AVObject object = query.getFirst();
            //Log.e("头像",object.getAVFile("headImage").getUrl()+" ");
            if (object != null)
                return object.getAVFile("headImage").getUrl();
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }

    //修改用户头像
    public static void setUserHeadImage(String id, AVFile file) {
        AVQuery<AVObject> query = new AVQuery<>("HeadImage");
        query.whereEqualTo("userId", id);
        try {
            AVObject object = query.getFirst();
            if (object != null)
                object.delete();
            AVObject avObject = new AVObject("HeadImage");
            avObject.put("userId", id);
            avObject.put("headImage", file);
            avObject.save();
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    //获取文章关系，是否收藏，点赞
    public static boolean ifCollectOrCommand(int what, String t_id) {
        JSONObject object = getJson("t_id=" + t_id + "&u_id=" + MyFunction.getUserInfo().getId() + "&u_psw=" + MyFunction.getUserInfo().getPassword(),
                URL + "/t/query_pro");
        if (object != null) {
            //Log.e(TAG,"拿到了返回值");
            try {
                if (what == 0) {
                    if (object.getInt("t_star_bool") == 1)
                        return true;
                    else
                        return false;

                } else {
                    if (object.getInt("t_recommend_bool") == 1)
                        return true;
                    else
                        return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Log.e(TAG, "Not拿到了返回值");
        return false;
    }

    //收藏文章
    public static boolean collectEsy(String t_id, boolean is) {
        int what = 0;
        if (is)
            what = 1;
        JSONObject object = getJson("u_id=" + MyFunction.getUserInfo().getId() + "&u_psw="
                + MyFunction.getUserInfo().getPassword() + "&t_id=" + t_id + "&u_act=" + what, URL + "/t/star");
        if (object != null) {
            return true;
        } else
            return false;
    }

    //推荐文章
    public static boolean ZanEsy(String t_id, boolean is) {
        int what = 0;
        if (is)
            what = 1;
        JSONObject object = getJson("u_id=" + MyFunction.getUserInfo().getId() + "&u_psw="
                + MyFunction.getUserInfo().getPassword() + "&t_id=" + t_id + "&u_act=" + what, URL + "/t/recommend");
        if (object != null)
            return true;
        else
            return false;
    }

    //发表文章评论
    public static boolean remarkEsy(String t_id, int what, String mark) {
        String type = "";
        if (what == 0)
            type = "article";
        else if (what == 1)
            type = "question";
        else if (what == 2)
            type = "answer";
        JSONObject object = getJson("u_id=" + MyFunction.getUserInfo().getId() + "&u_psw="
                + MyFunction.getUserInfo().getPassword() + "&ec_type=" + type + "&ec_id=" + t_id + "&c_text=" + mark, URL + "/c/add");
        if (object != null)
            return true;
        else
            return false;
    }

    //发表文章
    public static String publishEssay(String title ,String text,String tags){
        String url = URL+"/t/add";
        String data =  "u_id=" + MyFunction.getUserInfo().getId() + "&u_psw=" + MyFunction.getUserInfo().getPassword()+"&t_title="+title+"&t_text="+text+"&t_tags="+tags;
        JSONObject jsonObject=getJson(data,url);
        if(jsonObject!=null){
            try {
                if(jsonObject.getInt("code")==1){
                    return null;
                }else {
                    return jsonObject.getString("codeState");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "失败";
    }

    //修改信息
    public static boolean updateUser(String blog,String github,String tags,String intro){
        String data="u_id=" +MyFunction.getUserInfo().getId()
                +"&u_psw=" +MyFunction.getUserInfo().getPassword()
                +"&u_blog="+blog
                +"&u_github="+github
                +"&u_tags="+tags
                +"&u_intro="+intro;
        String url=URL+"/u/update";
        JSONObject object=getJson(data,url);
        if(object!=null){
            try {
                if(object.getInt("code")==1)
                    return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }




}
