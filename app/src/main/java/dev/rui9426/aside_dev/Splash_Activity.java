package dev.rui9426.aside_dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * 启动页
 * @author Rui
 * */
public class Splash_Activity extends Activity {
    //版本信息JSON_URL
    private static final String URL_UPDATE_SERVER = "http://192.168.0.12:8080/test/ver2.json";
    private String versionName;
    private static Context mContext;
    private static final int UPDATE_MSG = 1;
    private static final int LATEST_MSG = 2;
    private static final int CHECK_ERR = 3;
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_MSG:
                    JSONObject verMsg = (JSONObject) msg.obj;
                    AlertDialog alertDialog = null;
                    try {
                        alertDialog = new AlertDialog.Builder(mContext)
                                .setTitle("有新版本更新啦")
                                .setMessage(verMsg.getString("versionName"))
                                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //                                    Toast.makeText(mContext, "你点击了PositiveButton", Toast.LENGTH_SHORT).show();
                                        startUpdate();
                                        enterHome();

                                    }
                                })
                                .setNegativeButton("算了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //                                    Toast.makeText(mContext, "你点击了NegativeButton", Toast.LENGTH_SHORT).show();
                                        enterHome();
                                    }
                                })
                                .create();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertDialog.show();
                    break;
                case LATEST_MSG:
                    Toast.makeText(mContext, "已经是最新的喽", Toast.LENGTH_SHORT).show();
                    enterHome();
                    finish();
                    break;
                case CHECK_ERR:
                    Toast.makeText(mContext, "检查更新出错", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                default:
                    break;
            }

        }
    };
    private JSONObject jsonObject;
    private TextView tv_versionName;

    private static void startUpdate() {
        //开始下载新版本逻辑
    }

    private void enterHome() {
        //进入主Activity
        Intent intent = new Intent(mContext,HomePage_Activity.class);
        mContext.startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        try {
            final PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
            tv_versionName = findViewById(R.id.tv_splash_versionCode);
            tv_versionName.setText(packageInfo.versionName);
            mContext = Splash_Activity.this;
            System.out.println(packageInfo.versionName);
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    checkUpdate(packageInfo.versionName);
                }
            }.start();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    //检查版本更新逻辑
    void checkUpdate(String versionName){
        jsonObject = null;
        Message obtain = Message.obtain();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(URL_UPDATE_SERVER)
                .build();
        try(Response response = okHttpClient.newCall(request).execute()){
            jsonObject = new JSONObject(response.body().string());
            if(jsonObject == null){
                obtain.what = CHECK_ERR;
            }else if(!versionName.equals(jsonObject.getString("versionName"))){
                obtain.what = UPDATE_MSG;
                obtain.obj = jsonObject;
            }else if(versionName.equals(jsonObject.get("versionName"))){
                obtain.what = LATEST_MSG;
            }
        } catch (IOException | JSONException e) {
            obtain.what = CHECK_ERR;
            e.printStackTrace();
        }finally {
            handler.sendMessage(obtain);
        }
    }

}
