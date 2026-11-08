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
    TextView tvNomYb, tvVerdictYb, tvStampYb;
    Button btnRetourYb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 2) ربط العناصر بالـ XML
        rootYb = findViewById(R.id.root_result_yb);
        tvNomYb = findViewById(R.id.tv_nom_yb);
        tvVerdictYb = findViewById(R.id.tv_verdict_yb);
        tvStampYb = findViewById(R.id.tv_stamp_yb);
        btnRetourYb = findViewById(R.id.btn_retour_yb);

        // 3) استقبال الاسم والقرار
        String m = IntentKeys.MATRICULE;
        Intent intent = getIntent();
        String nom = intent.getStringExtra(IntentKeys.cleNom(m));
        boolean approuve = intent.getBooleanExtra(IntentKeys.cleVerdict(m), false);

        tvNomYb.setText(nom);

        // 4) تغيير لون الخلفية والرسالة حسب القرار
        if (approuve) {
            rootYb.setBackgroundColor(Color.parseColor("#A5D6A7")); // أخضر
            tvVerdictYb.setText("Bon voyage " + nom);
            tvStampYb.setText("ADMIS");
            tvStampYb.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            rootYb.setBackgroundColor(Color.parseColor("#EF9A9A")); // أحمر
            tvVerdictYb.setText(nom + " est en état d'arrestation temporelle");
            tvStampYb.setText("REFUSÉ");
            tvStampYb.setTextColor(Color.parseColor("#F44336"));
        }

        // 5) حركة الختم: يبدأ كبيرا وشفافا ثم ينزل
        tvStampYb.setScaleX(3f);
        tvStampYb.setScaleY(3f);
        tvStampYb.setAlpha(0f);
        tvStampYb.animate()
                .scaleX(1f).scaleY(1f).alpha(1f)
                .setStartDelay(400).setDuration(500)
                .start();

        // 6) زر مسافر جديد: العودة للنشاط الأول وإغلاق كل النشاطات السابقة
        btnRetourYb.setOnClickListener(v -> {
            Intent retour = new Intent(ResultActivity.this, MainActivity.class);
            retour.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(retour);
            finish();
        });
    }
}