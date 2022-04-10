package com.example.retrofitmarvel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Hero> heroesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate recyclerview
        recyclerView = findViewById(R.id.heroesRecyclerView);
        heroesList = new ArrayList<>();
        //calling a method to display the REST server data
        getHeroes();
    }

    private void getHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Api object (API is an interface)
        Api api = retrofit.create(Api.class);

        // use api getHeroes method
        Call<List<Hero>> call = api.getHeroes();

        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heroes = response.body();

                for(int i = 0; i < heroes.size(); i++) {
                    Hero hero = new Hero(heroes.get(i).getName(),
                    heroes.get(i).getRealname(),
                    heroes.get(i).getTeam(),
                    heroes.get(i).getImageurl(),
                    heroes.get(i).getBio());

                    heroesList.add(hero);
                }

                putDataIntoRecyclerView(heroesList);
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private void putDataIntoRecyclerView(List<Hero> heroesList) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, heroesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}