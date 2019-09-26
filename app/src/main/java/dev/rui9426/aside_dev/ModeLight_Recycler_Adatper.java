package dev.rui9426.aside_dev;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
/**
 * light模式主页中的recyclerView适配器
 * @author Rui
 * */
public class ModeLight_Recycler_Adatper extends RecyclerView.Adapter<ModeLight_Recycler_Adatper.ModeLight_Recycler_ViewHolder> {
    private Context mContext;
    private ArrayList<Bean_DiscuzItem> dataList;
    private RecyclerView_OnItemClick recyclerView_onItemClick;

    public void setRecyclerView_onItemClick(RecyclerView_OnItemClick recyclerView_onItemClick) {
        this.recyclerView_onItemClick = recyclerView_onItemClick;
    }

    public ModeLight_Recycler_Adatper(Context mContext, ArrayList<Bean_DiscuzItem> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ModeLight_Recycler_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.item_discuz, null);
        return new ModeLight_Recycler_ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ModeLight_Recycler_ViewHolder holder, int position) {
//        holder.textTitle.setText(dataList.get(position).getTextTitle());
//        holder.content.setText(dataList.get(position).getContent());
        //holder.imageTitle.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class ModeLight_Recycler_ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageTitle;
        private TextView textTitle;
        private TextView content;
        public ModeLight_Recycler_ViewHolder(@NonNull final View itemView) {
            super(itemView);
            initComponent(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerView_onItemClick.onClick(itemView,getLayoutPosition());
                }
            });
        }

        private void initComponent(@NonNull View itemView) {
            imageTitle =  itemView.findViewById(R.id.iv_discuz_item_imageTitle);
            textTitle = itemView.findViewById(R.id.tv_discuz_item_textTitle);
            content = itemView.findViewById(R.id.tv_discuz_item_content);
        }
    }
}
