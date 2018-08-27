package com.alen.rollimage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private static final int TEXT = 0, IMAGE = 1;
    private RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(new Adapter());
    }


    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            switch (i){
                case TEXT:
                    return new Holder(LayoutInflater.from(MainActivity.this).inflate(R.layout.recycler_item_text, viewGroup, false));
                case IMAGE:
                    return new ImageHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.recycler_item_image, viewGroup, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int i) {
            switch (getItemViewType(i)){
                case TEXT:
                    holder.textView.setText("这是第" + i + "个条目");
                    break;
                case IMAGE:
                    Glide.with(MainActivity.this)
                            .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535350702249&di=6a8eff3f751e47894b2f9af221c5eec5&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201504%2F22%2F20150422H1756_sNuWa.thumb.700_0.jpeg")
                            .into(((ImageHolder) holder).image);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 15){
                return IMAGE;
            }
            return TEXT;
        }

        private int dp2px(int dp){
            float density = getResources().getDisplayMetrics().density;
            return (int) (dp*density+0.5f);
        }

        class Holder extends RecyclerView.ViewHolder{
            TextView textView;

            public Holder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text);
            }
        }

        class ImageHolder extends Holder{
            RollImage image;

            public ImageHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }


}
