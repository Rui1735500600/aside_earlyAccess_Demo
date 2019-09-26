package dev.rui9426.aside_dev;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
/**
 * Light模式主页
 * //TODO:布局待重写
 * @author Rui
 *
 * */
public class HomePage_Activity extends AppCompatActivity {
    private static final boolean MODE_LIGHT = true;
    private static final boolean MODE_DARK = false;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Bean_DiscuzItem> dataList;
    FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean timeMode = checkTimeMode();
        supportFragmentManager = getSupportFragmentManager();
        if (timeMode)
            enterLightMode();
        else
            enterDarkMode();

    }

    private void enterDarkMode() {
        Toast.makeText(this, "DarkMode", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.layout_homepage_dark);
    }

    private void enterLightMode() {
        Toast.makeText(this, "LightMode", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.layout_homepage_light);
        Fragment_TopBar fragment_topbar = (Fragment_TopBar) supportFragmentManager.findFragmentById(R.id.fragment_light_topbar);
        assert fragment_topbar != null;
        fragment_topbar.setTopbarText("Light Mode Now");
        initSwipeRefreshLayout();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        Fragment_RecyclerView fragment_recyclerView = new Fragment_RecyclerView();
        initDiscuzData();
        fragment_recyclerView.setRecyclerViewData(dataList);
        fragmentTransaction.replace(R.id.fragment_light_recyclerView,fragment_recyclerView);
        fragmentTransaction.commit();
    }

    private void initDiscuzData() {
        dataList = new ArrayList<>();
        //请求数据
        for (int i = 0; i < 10; i++) {
            dataList.add(new Bean_DiscuzItem());
        }
    }

    //装载下拉刷新布局
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.layout_swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        //刷新逻辑
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
    //检查时间
    boolean checkTimeMode() {
        int AM_PM = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return AM_PM<20 && AM_PM>8 ? MODE_LIGHT : MODE_DARK;
    }
}
