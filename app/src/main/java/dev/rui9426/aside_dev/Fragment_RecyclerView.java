package dev.rui9426.aside_dev;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/**
 * 主页RecyclerView //TODO:布局待重写
 * @author Rui
 * */
public class Fragment_RecyclerView extends Fragment {

    private RecyclerView recyclerView;
    private View inflate;
    private ArrayList<Bean_DiscuzItem> dataList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = View.inflate(getContext(), R.layout.fragment_recyclerview, null);
        setRecyclerViewData(dataList);
        initRecyclerView();
        return inflate;
    }

    public void setRecyclerViewData(ArrayList<Bean_DiscuzItem> dataList) {
            this.dataList = dataList;
    }
    private void initRecyclerView() {
        recyclerView = inflate.findViewById(R.id.fragment_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ModeLight_Recycler_Adatper modeLight_recycler_adatper = new ModeLight_Recycler_Adatper(getContext(), dataList);
        modeLight_recycler_adatper.setRecyclerView_onItemClick(new RecyclerView_OnItemClick() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), "点击了第+"+position+"个条目", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(modeLight_recycler_adatper);
    }
}
