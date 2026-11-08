package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // 1) عناصر الواجهة
    TextView tvNomYb, tvDestinationYb, tvAgeYb, tvMotifYb;
    Button btnApprouverYb, btnRefuserYb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // 2) ربط العناصر بالـ XML
        tvNomYb = findViewById(R.id.tv_nom_value_yb);
        tvDestinationYb = findViewById(R.id.tv_destination_value_yb);
        tvAgeYb = findViewById(R.id.tv_age_value_yb);
        tvMotifYb = findViewById(R.id.tv_motif_value_yb);
        btnApprouverYb = findViewById(R.id.btn_approuver_yb);
        btnRefuserYb = findViewById(R.id.btn_refuser_yb);

        // 3) استقبال البيانات بمفاتيح مبنية بالدالة الثابتة
        String m = IntentKeys.MATRICULE;
        Intent intent = getIntent();
        String nom = intent.getStringExtra(IntentKeys.cleNom(m));
        int naissance = intent.getIntExtra(IntentKeys.cleNaissance(m), 0);
        int destination = intent.getIntExtra(IntentKeys.cleDestination(m), 0);
        String motif = intent.getStringExtra(IntentKeys.cleMotif(m));

        // 4) حساب العمر الزمني (الفرق بين سنة الوجهة وسنة الميلاد)
        int age = destination - naissance;

        // 5) عرض الملخص
        tvNomYb.setText(nom);
        tvDestinationYb.setText(String.valueOf(destination));
        tvAgeYb.setText(age + " ans");
        tvMotifYb.setText(motif);

        // 6) تعطيل زر الموافقة إذا كان السبب "Modification du passé"
        //    والفرق (بالقيمة المطلقة) عددا أوليا
        boolean modification = motif != null && motif.contains("Modification");
        if (modification && estPremier(Math.abs(age))) {
            btnApprouverYb.setEnabled(false);
            btnApprouverYb.setAlpha(0.4f);
        }

        // 7) زر الموافقة: إرسال القرار true
        btnApprouverYb.setOnClickListener(v -> ouvrirVerdict(nom, true));

        // 8) زر الرفض: إرسال القرار false
        btnRefuserYb.setOnClickListener(v -> ouvrirVerdict(nom, false));
    }

    // 9) فتح شاشة القرار مع الاسم والقرار (قيمة منطقية)
    private void ouvrirVerdict(String nom, boolean approuve) {
        String m = IntentKeys.MATRICULE;
        Intent i = new Intent(VerificationActivity.this, ResultActivity.class);
        i.putExtra(IntentKeys.cleNom(m), nom);
        i.putExtra(IntentKeys.cleVerdict(m), approuve);
        startActivity(i);
    }

    // 10) اختبار العدد الأولي
    private boolean estPremier(int n) {
        if (n < 2) return false;
        for (int d = 2; d * d <= n; d++) {
            if (n % d == 0) return false;
        }
        return true;
    }
}