package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    // 1) عناصر الواجهة
    View rootYb;
    TextView tvIconeYb, tvMessageYb, tvDetailYb;
    Button btnNouveauYb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 2) ربط العناصر بالـ XML (نفس الـ id الموجودة في الملف)
        rootYb = findViewById(R.id.main);
        tvIconeYb = findViewById(R.id.tv_icone_yb);
        tvMessageYb = findViewById(R.id.tv_message_yb);
        tvDetailYb = findViewById(R.id.tv_detail_yb);
        btnNouveauYb = findViewById(R.id.btn_nouveau_yb);

        // 3) استقبال الاسم والقرار (قيمة منطقية) بمفاتيح مبنية بالدالة الثابتة
        String m = IntentKeys.MATRICULE;
        Intent intent = getIntent();
        String nom = intent.getStringExtra(IntentKeys.cleNom(m));
        boolean approuve = intent.getBooleanExtra(IntentKeys.cleVerdict(m), false);

        // 4) تغيير لون الخلفية والرمز والرسالة حسب القرار
        if (approuve) {
            rootYb.setBackgroundColor(Color.parseColor("#2E7D32")); // أخضر
            tvIconeYb.setText("✓");
            tvMessageYb.setText("Bon voyage " + nom);
            tvDetailYb.setText("Le saut temporel est approuvé.");
        } else {
            rootYb.setBackgroundColor(Color.parseColor("#C62828")); // أحمر
            tvIconeYb.setText("✗");
            tvMessageYb.setText(nom + " est en état d'arrestation temporelle");
            tvDetailYb.setText("Le saut temporel est refusé : risque de paradoxe.");
        }

        // 5) حركة الرمز: يبدأ كبيرا وشفافا ثم يستقر
        tvIconeYb.setScaleX(2f);
        tvIconeYb.setScaleY(2f);
        tvIconeYb.setAlpha(0f);
        tvIconeYb.animate()
                .scaleX(1f).scaleY(1f).alpha(1f)
                .setStartDelay(300).setDuration(500)
                .start();

        // 6) زر مسافر جديد: العودة للنشاط الأول وإغلاق كل النشاطات السابقة
        btnNouveauYb.setOnClickListener(v -> {
            Intent retour = new Intent(ResultActivity.this, MainActivity.class);
            retour.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(retour);
            finish();
        });
    }
}