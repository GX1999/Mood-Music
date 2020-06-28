package gx.upc.edu.gittest;


import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class fragment extends Fragment {
    private String name="";
    private String emotion="";
    private long id=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment, container, false);
        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        if(bundle!=null){
            name = bundle.getString("name");
            emotion=bundle.getString("emotion");
            id=bundle.getLong("id");
        }
        System.out.println(name);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) getActivity().findViewById(R.id.QQ);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path=name+".mp3";
                try {
                    getAssetsCacheFile(getActivity(),path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File cacheFile = new File(getActivity().getExternalCacheDir(),path);
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                Uri  imageUri;
                //判断版本号
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(cacheFile);
                } else {
                    imageUri = FileProvider.getUriForFile(getActivity(), "gx.upc.edu.gittest.fileProvider",cacheFile);
                }
                shareImage(imageUri);

            }
        });
        Button back = (Button) getActivity().findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ServiceActivity.class);
                getActivity().startActivity(intent);
            }
        });
        Button button3 = (Button) getActivity().findViewById(R.id.delete);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setTitle("取消收藏");
                dialog.setMessage("是否要删除"+name);
                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Myhelper helper = new Myhelper(getActivity());
                        int delete=helper.delete(name);
                        if(delete==1){
                            Toast.makeText(getActivity(),"取消收藏成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getActivity(),lovemusic.class);
                            getActivity().startActivity(intent);
                        }


                    }

                });
                dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "收藏保留", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.create();
                dialog.show();
            }
        });
    }
    public void shareImage(Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
        // shareIntent.setType("image/*");//选择图片
        shareIntent.setType("audio/mp3"); //选择音频
        // shareIntent.setType(MIME_TYPE_IMAGE); //选择视频
        //    shareIntent.setType("video/;image/");//同时选择音频和视频
        startActivity(shareIntent);
    }
    public void getAssetsCacheFile(Context context,String fileName) throws IOException {
        File cacheFile = new File(getActivity().getExternalCacheDir(),fileName);
        try {
            if (cacheFile.exists()) {
                cacheFile.delete();
            }
            cacheFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = context.getAssets().open(emotion+fileName);
        FileOutputStream outputStream = new FileOutputStream(cacheFile);
        byte[] buf = new byte[40000000];
        int len=-1;
        while ((len = inputStream.read(buf)) > 0)
        { outputStream.write(buf, 0, len);
        }
        outputStream.close();
        inputStream.close();

    }




}