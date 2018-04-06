package com.example.hp_pc.serverrajeev;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.List;

public class BuyDetailsActivity extends AppCompatActivity {

    //get_user_name
    public static Intent newIntent(Context context, Photo photo) {
        Intent intent = new Intent(context, BuyDetailsActivity.class);
        intent.putExtra("item", photo);
        return intent;
    }

    private Photo item;
    private ImageView itemImageView;
    private TextView itemOwnerText, itemPriceText, itemNameText, itemDescriptionText;
    private Button OwnerCallButton;
    private UserInfo OwnerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_details);

        item = (Photo) getIntent().getSerializableExtra("item");

        itemImageView = (ImageView) findViewById(R.id.buy_details_image_view);
        Picasso.with(BuyDetailsActivity.this)
                .load(item.getmUrl())
                .placeholder(R.drawable.shop_placeholder)
                .into(itemImageView);

        itemOwnerText = (TextView) findViewById(R.id.buy_details_item_owner);

        itemPriceText = (TextView) findViewById(R.id.buy_details_item_price);
        itemPriceText.setText("₹ " + item.getPrice());

        itemNameText = (TextView) findViewById(R.id.buy_details_item_name);
        itemNameText.setText(item.getmName());

        itemDescriptionText = (TextView) findViewById(R.id.buy_details_item_description);
        itemDescriptionText.setText(item.getDes());

        OwnerCallButton = (Button) findViewById(R.id.buy_details_call_button);
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<UserInfo>> {

        @Override
        protected List<UserInfo> doInBackground(Void... voids) {
            return new FlickrFetcher().getProfileDetails(item.getUserName());
        }

        @Override
        protected void onPostExecute(List<UserInfo> userInfos) {
            OwnerInfo = userInfos.get(0);
            itemOwnerText.setText(OwnerInfo.getName());
        }
    }
}
