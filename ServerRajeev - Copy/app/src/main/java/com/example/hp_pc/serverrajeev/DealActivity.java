package com.example.hp_pc.serverrajeev;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DealActivity extends AppCompatActivity {

    private Button BuyButton, SellButton, AdsButton, ChatButton, ProfileButton;
    private Fragment buyFragment, chatFragment, sellFragment, adsFragment, profileFragment;
    public String UserName;

    //get_user_name
    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, DealActivity.class);
        intent.setData(Uri.parse(userName));
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get_user_name
        UserName = getIntent().getData().toString();

        Toast.makeText(DealActivity.this, UserName, Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_deal);

        final FragmentManager fm = getSupportFragmentManager();

        buyFragment = BuyFragment.newInstance();
        chatFragment = ChatFragment.newInstance();
        sellFragment = SellFragment.newInstance();
        adsFragment = AdsFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        fm.beginTransaction()
                .add(R.id.fragment_container, buyFragment)
                .commit();

        BuyButton = (Button) findViewById(R.id.buy_button);
        BuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fm.findFragmentById(R.id.fragment_container) == buyFragment)
                    return;

                fm.beginTransaction()
                        .replace(R.id.fragment_container, buyFragment)
                        .commit();
            }
        });

        ChatButton = (Button) findViewById(R.id.chat_button);
        ChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fm.findFragmentById(R.id.fragment_container) == chatFragment)
                    return;

                fm.beginTransaction()
                        .replace(R.id.fragment_container, chatFragment)
                        .commit();
            }
        });

        SellButton = (Button) findViewById(R.id.sell_button);
        SellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fm.findFragmentById(R.id.fragment_container) == sellFragment)
                    return;

                fm.beginTransaction()
                        .replace(R.id.fragment_container, sellFragment)
                        .commit();

            }
        });

        AdsButton = (Button) findViewById(R.id.ads_button);
        AdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fm.findFragmentById(R.id.fragment_container) == adsFragment)
                    return;

                fm.beginTransaction()
                        .replace(R.id.fragment_container, adsFragment)
                        .commit();
            }
        });

        ProfileButton = (Button) findViewById(R.id.profile_button);
        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fm.findFragmentById(R.id.fragment_container) == profileFragment)
                    return;

                fm.beginTransaction()
                        .replace(R.id.fragment_container, profileFragment)
                        .commit();
            }
        });
    }
}
