package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

// الواجهة 3: شاشة النتيجة (ResultActivity)
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 1 : ربط عناصر الواجهة (المعرفات تنتهي باللاحقة _yb كما في ملف التخطيط)
        View racine = findViewById(R.id.main);
        TextView tvTitre = findViewById(R.id.tv_titre_verdict_yb);
        TextView tvIcone = findViewById(R.id.tv_icone_yb);
        TextView tvMessage = findViewById(R.id.tv_message_yb);
        TextView tvDetail = findViewById(R.id.tv_detail_yb);
        Button btnNouveau = findViewById(R.id.btn_nouveau_yb);

        // 2 : قراءة البيانات القادمة من النشاط 2 بمفاتيح مبنية بدالة ثابتة (الاسم + القرار النهائي)
        String m = IntentKeys.MATRICULE;
        Intent intent = getIntent();
        String nom = intent.getStringExtra(IntentKeys.cleNom(m));
        boolean approuve = intent.getBooleanExtra(IntentKeys.cleVerdict(m), false);

        // 3 : إذا كان الاسم فارغا نستعمل اسما افتراضيا
        if (nom == null || nom.trim().isEmpty()) {
            nom = "Voyageur";
        }

        // 4 : تغيير الخلفية والرمز والرسائل حسب القرار
        if (approuve) {
            // 5 : موافقة: خلفية خضراء مع علامة ✓
            racine.setBackgroundColor(ContextCompat.getColor(this, R.color.vert_approuve));
            tvIcone.setText("✓");
            tvTitre.setText("ACCÈS AUTORISÉ");
            tvMessage.setText("Bon voyage " + nom);
            tvDetail.setText("Le saut temporel est approuvé.");
        } else {
            // 6 : رفض: خلفية حمراء مع علامة ✕
            racine.setBackgroundColor(ContextCompat.getColor(this, R.color.rouge_refuse));
            tvIcone.setText("✕");
            tvTitre.setText("ACCÈS REFUSÉ");
            tvMessage.setText(nom + " est en état d'arrestation temporelle");
            tvDetail.setText("Risque de paradoxe détecté.");
        }

        // 7 : زر مسافر جديد: العودة إلى MainActivity مع إغلاق كل الأنشطة السابقة
        btnNouveau.setOnClickListener(v -> {
            Intent retour = new Intent(ResultActivity.this, MainActivity.class);
            // 8 : CLEAR_TASK يغلق كل الأنشطة و NEW_TASK يبدأ مهمة جديدة (الاستمارة تظهر فارغة)
            retour.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(retour);
            finish();
        });
    }
}