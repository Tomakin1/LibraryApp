package com.example.libraryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryapp.databinding.ActivityBookBinding;

import java.util.concurrent.TimeUnit;

public class BookActivity extends AppCompatActivity {

    private ActivityBookBinding binding;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private boolean returnBtnClicked;
    private long endTimeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.timeTxt.setVisibility(View.INVISIBLE);
        binding.textView6.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedBook")) {
            BookDetails selectedBook = (BookDetails) intent.getSerializableExtra("selectedBook");

            if (selectedBook != null) {
                binding.bookName.setText(selectedBook.bookName);
                binding.img.setImageResource(selectedBook.image);
            } else {
                Toast.makeText(this, "Kitap bilgileri alınamadı", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Intent bilgisi alınamadı", Toast.LENGTH_SHORT).show();
            finish();
        }

        binding.takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        binding.returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                returnBtnClicked = true;
            }
        });

        // Eğer süre devam ediyorsa veya daha önce başlatılmışsa süreci başlat
        if (timerRunning && !returnBtnClicked) {
            startTimer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void startTimer() {
        if (!timerRunning) {
            binding.timeTxt.setVisibility(View.VISIBLE);
            binding.textView6.setVisibility(View.INVISIBLE);

            long millisInFuture;
            if (endTimeMillis > System.currentTimeMillis()) {
                millisInFuture = endTimeMillis - System.currentTimeMillis();
            } else {
                millisInFuture = 3 * 60 * 60 * 1000; // 3 saat
                endTimeMillis = System.currentTimeMillis() + millisInFuture;
            }

            countDownTimer = new CountDownTimer(millisInFuture, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                    binding.timeTxt.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                }

                @Override
                public void onFinish() {
                    showAlertDialog();
                }
            }.start();

            timerRunning = true;
        }
    }

    private void stopTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            binding.textView6.setVisibility(View.VISIBLE);
            binding.timeTxt.setVisibility(View.INVISIBLE);
            timerRunning = false;
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("İade süresi bitti")
                .setMessage("Kitabı iade etmeniz gerekmektedir.")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
