package gx.upc.edu.gittest;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Fragment1 extends Fragment {


    private Uri imageUri;
    private ImageView cameraPic;
    public String Access;

    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);
        cameraPic = getActivity().findViewById(R.id.CameraPic);

        //  System.out.println(getActivity().getExternalCacheDir());
        File outputImage = new File(getActivity().getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists())
            {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) { }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        //判断版本号
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(getActivity(),
                    "gx.upc.edu.gittest.fileProvider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 1);

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
            cameraPic.setImageBitmap(bitmap);

            faceDetect();
            //将图片解析成Bitmap对象，并把它显现出来
        } catch (Exception e) {

        }
    }

    private void getAccess_Token()  //获取 Access_Token  一个月更新一次
    {
        new Thread(new Runnable(){     //Android 4.0以上，网络连接不能放在主线程上
            @Override
            public void run() {
                AuthService authService=new AuthService();         //获取Access Token
                Access=authService.getAuth("pkbTnCCXmme35PUL5L4mTxcz","4kTkbNm4Itw0hh87twZCiFlbBUehOqSG");
                System.out.println(Access);

            }
        }).start();
    }

    public void faceDetect()
    {
        new Thread(new Runnable(){     //Android 4.0以上，网络连接不能放在主线程上
            @Override
            public void run() {
                Looper.prepare();

                AuthService authService=new AuthService();         //获取Access Token
                Access=authService.getAuth("pkbTnCCXmme35PUL5L4mTxcz","4kTkbNm4Itw0hh87twZCiFlbBUehOqSG");
                System.out.println(Access);


                String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
                try {
                    String Filepath = "/storage/emulated/0/Android/data/gx.upc.edu.gittest/cache/output_image.jpg";
                    String image = Base64Util.encode(FileUtil.readFileByBytes(Filepath));
                    Map<String, Object> map = new HashMap<>();
                    map.put("image",image);
                    map.put("face_field","age,emotion,race");
                    map.put("image_type", "BASE64");
                    String param = GsonUtils.toJson(map);
                    HttpUtil httpUtil = new HttpUtil();
                 //   String result = httpUtil.post(url, "24.df17cf76a06c2c1e33081155881f3447.2592000.1595065302.282335-20470266", param);
                    String result = httpUtil.post(url, Access, param);
                    System.out.println(result);

                  analyse(result);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Looper.loop();
            }

        }).start();
    }

    public void analyse(String result)    //JSON解析
    {
        try {

            JSONObject jsonObject = new JSONObject(result);
            String result1 = jsonObject.getString("result");

            JSONObject json1 = new JSONObject(result1);
            String face_list1 = json1.getString("face_list");

            JSONArray json2 = new JSONArray(face_list1);
            int length1 = json2.length();
            String string = null;
            String s = null;
            for (int n = 0; n < length1; n++) {
                s = json2.getString(n);
                string = s + string;

            }

            JSONObject json3 = new JSONObject(string);

            String emotion = json3.getString("emotion");
            JSONObject json4 = new JSONObject(emotion);
            String em = json4.getString("type");
            //      System.out.println("emotion="+em);

            String race = json3.getString("race");
            JSONObject json5 = new JSONObject(race);
            String ra = json5.getString("type");
            //    System.out.println("race="+race);

            String age = json3.getString("age");
            //      System.out.println("age="+age);

            TextView t2 = getActivity().findViewById(R.id.t2);
            t2.setText("年龄:" + age+"\n" + "表情:" + em +"\n"+ "人种:" + ra);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment1, container, false);
    }

}
