package com.example.genuvaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Home_page extends AppCompatActivity implements View.OnClickListener {
CardView sakia_card, opera_card;
Button createBtn,logOutBtn, myTicketsBtn;
ImageView profileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        profileData=findViewById(R.id.profilePage);
        profileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profilepage=new Intent(Home_page.this, Profile.class);
                startActivity(profilepage);
            }
        });
        Bundle bundle=getIntent().getExtras();
        String Admin=bundle.getString("Email");

        sakia_card=findViewById(R.id.sakia_card);
        sakia_card.setOnClickListener(this);

        opera_card=findViewById(R.id.opera_card);
        opera_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent operaParties=new Intent(getApplicationContext(), OperaParties.class);
                startActivity(operaParties);
            }
        });



        createBtn = findViewById(R.id.control_btn);
        if(Admin.equals("kamal@yahoo.com")) {
            createBtn.setVisibility(View.VISIBLE);
            createBtn.setOnClickListener(this);
        }
        else {
            createBtn.setVisibility(View.GONE);
        }


        logOutBtn=findViewById(R.id.logout_btn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnLogin=new Intent(Home_page.this, Login.class);
                startActivity(returnLogin);
                finish();
            }
        });
        myTicketsBtn=findViewById(R.id.tickets_btn);
        myTicketsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myTickets=new Intent(Home_page.this, MyTickets.class);
                startActivity(myTickets);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.sakia_card){
            Intent sakiaParties=new Intent(Home_page.this, SakiaParties.class);
            startActivity(sakiaParties);
        }
        if(view.getId()==R.id.control_btn){
                Intent create_party=new Intent(Home_page.this, CreateParty.class);
                startActivity(create_party);
        }
    }
}
