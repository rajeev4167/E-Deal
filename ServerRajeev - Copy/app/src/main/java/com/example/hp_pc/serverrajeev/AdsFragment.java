package com.example.hp_pc.serverrajeev;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp-Pc on 10/8/2017.
 */
public class AdsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Photo> Items = new ArrayList<Photo>();
    private PhotoAdapter photoAdapter;

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        AdsFragment fragment = new AdsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ads, container, false);

        new FetchItemsTask().execute();

        recyclerView = (RecyclerView) view.findViewById(R.id.sell_recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImageView;
        private TextView itemPriceText, itemNameText;
        private Photo item;

        public PhotoHolder(View itemView) {
            super(itemView);
            itemImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
            itemPriceText = (TextView) itemView.findViewById(R.id.item_price_text_view);
            itemNameText = (TextView) itemView.findViewById(R.id.item_name_text_view);
            itemView.setOnClickListener(this);
        }

        public void  bindItem(Photo thisItem) {

            item = thisItem;
            itemPriceText.setText("₹ " + thisItem.getPrice());
            itemNameText.setText(thisItem.getmName());

            Picasso.with(getActivity())
                    .load(item.getmUrl())
                    .placeholder(R.drawable.shop_placeholder)
                    .into(itemImageView);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), item.getmName() + " " + item.getDes(), Toast.LENGTH_SHORT).show();
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<Photo> items;

        public PhotoAdapter(List<Photo> items) {
            this.items = items;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.item_photo, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {

            Photo item = items.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(List<Photo> items) {
            this.items = items;
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<Photo>> {

        @Override
        protected List<Photo> doInBackground(Void... voids) {
            DealActivity dealActivity = (DealActivity) getActivity();
            return new FlickrFetcher().downloadSellItems(dealActivity.UserName);
        }

        @Override
        protected void onPostExecute(List<Photo> photos) {
            Items = photos;
            photoAdapter = new PhotoAdapter(Items);
            recyclerView.setAdapter(photoAdapter);
        }
    }
}
