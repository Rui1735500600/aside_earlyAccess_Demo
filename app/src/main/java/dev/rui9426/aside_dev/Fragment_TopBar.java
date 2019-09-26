package dev.rui9426.aside_dev;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
/**
 * 顶边栏
 * //TODO:布局待重写
 * @author Rui
 * */
public class Fragment_TopBar extends Fragment {

    private TextView topbarText;//Delete in future

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.fragment_topbar, null);
        topbarText = inflate.findViewById(R.id.tv_topbar);
        return inflate;
    }
    public void setTopbarText(String text){
        topbarText.setText(text);
    }
}
