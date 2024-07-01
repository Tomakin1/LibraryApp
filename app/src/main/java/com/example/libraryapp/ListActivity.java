package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.libraryapp.databinding.ActivityListBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ActivityListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<BookDetails> bookArrayList;
        super.onCreate(savedInstanceState);

        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookArrayList = new ArrayList<>();

        BookDetails ciftlik = new BookDetails("Hayvan Çiftliği",R.drawable.hayvan);
        BookDetails dorian = new BookDetails("Dorian Gray'in Portresi",R.drawable.dorian);
        BookDetails fare = new BookDetails("Fareler Ve İnsanlar",R.drawable.fareler);
        BookDetails calikusu = new BookDetails("Çalıkuşu",R.drawable.calikusu);
        BookDetails sefiller = new BookDetails("Sefiller",R.drawable.sefiller);
        BookDetails seker = new BookDetails("Şeker Portakalı",R.drawable.seker);
        BookDetails simyaci = new BookDetails("Simyacı",R.drawable.simyaci);
        BookDetails suc = new BookDetails("Suç Ve Ceza",R.drawable.suc);
        BookDetails ucurtma = new BookDetails("Uçurtma Avcısı",R.drawable.ucurtma);

        bookArrayList.add(ciftlik);
        bookArrayList.add(dorian);
        bookArrayList.add(calikusu);
        bookArrayList.add(fare);
        bookArrayList.add(seker);
        bookArrayList.add(sefiller);
        bookArrayList.add(simyaci);
        bookArrayList.add(suc);
        bookArrayList.add(ucurtma);

        binding.rec.setLayoutManager(new LinearLayoutManager(this));
        BookAdapter bookAdapter = new BookAdapter(bookArrayList);
        binding.rec.setAdapter(bookAdapter);

        ChipNavigationBar chipNavigationBar = binding.bottomMenu;


        chipNavigationBar.setItemSelected(R.id.list, true);


        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                if (id == R.id.goHome) {
                    Intent profileIntent = new Intent(ListActivity.this, MainActivity.class);
                    startActivity(profileIntent);
                } else if ((id == R.id.Profile)) {
                    Intent intent = new Intent(ListActivity.this, ProfileActivity.class);
                    startActivity(intent);

                }

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
