package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

// الفئة الرئيسية: نموذج تسجيل المسافر عبر الزمن (النشاط الأول)
public class MainActivity extends AppCompatActivity {

    // 1) تعريف عناصر الواجهة
    EditText etNom, etNaissance, etDestination;
    RadioGroup rgMotif;
    Button btnInspecter;

    // 1.1) ثوابت للحدود المسموح بها للسنوات
    private static final int ANNEE_NAISSANCE_MIN = 1900;
    private static final int ANNEE_DESTINATION_MIN = 1000;
    private static final int ANNEE_DESTINATION_MAX = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 2) إضافة هامش حتى لا يختفي المحتوى تحت شريط الحالة
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_yb), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // 3) ربط العناصر بالـ XML عن طريق الـ id
        etNom = findViewById(R.id.et_nom_yb);
        etNaissance = findViewById(R.id.et_naissance_yb);
        etDestination = findViewById(R.id.et_destination_yb);
        rgMotif = findViewById(R.id.rg_motif_yb);
        btnInspecter = findViewById(R.id.btn_inspecter_yb);

        // 4) عند الضغط على الزر نستدعي دالة الفحص
        btnInspecter.setOnClickListener(v -> inspecterProfil());
    }

    private void inspecterProfil() {
        // 5) قراءة القيم المدخلة
        String nom = etNom.getText().toString().trim();
        String naissanceTxt = etNaissance.getText().toString().trim();
        String destinationTxt = etDestination.getText().toString().trim();

        // 6) التحقق من الاسم
        if (nom.isEmpty()) {
            etNom.setError("Nom obligatoire");
            etNom.requestFocus();
            return;
        }

        // 7)vérifier si champs est vide sinon taawed tfocus al champs bl curseur bech yaamrha
        if (naissanceTxt.isEmpty()) {
            etNaissance.setError("Obligatoire");
            etNaissance.requestFocus();
            return;
        }
        if (destinationTxt.isEmpty()) {
            etDestination.setError("Obligatoire");
            etDestination.requestFocus();
            return;
        }

        // 8) تحويل النصوص إلى أعداد مع معالجة الخطأ
        int naissance, destination;
        try {
            naissance = Integer.parseInt(naissanceTxt);
            destination = Integer.parseInt(destinationTxt);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Années invalides", Toast.LENGTH_SHORT).show();
            return;
        }

        // 8.1) حساب السنة الحالية (هذا السطر كان ناقصا وكان يمنع التشغيل)
        int anneeActuelle = Calendar.getInstance().get(Calendar.YEAR);

        // 9) سنة الميلاد بين الحد الأدنى والسنة الحالية
        if (naissance < ANNEE_NAISSANCE_MIN || naissance > anneeActuelle) {
            etNaissance.setError("Année de naissance entre " + ANNEE_NAISSANCE_MIN + " et " + anneeActuelle);
            etNaissance.requestFocus();
            return;
        }

        // 10) سنة الوجهة من 4 أرقام
        if (destination < ANNEE_DESTINATION_MIN || destination > ANNEE_DESTINATION_MAX) {
            etDestination.setError("Année de destination invalide (4 chiffres)");
            etDestination.requestFocus();
            return;
        }

        // 11) التحقق من اختيار سبب السفر
        int idMotif = rgMotif.getCheckedRadioButtonId();
        if (idMotif == -1) {
            Toast.makeText(this, "Choisissez un motif de voyage", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rbMotif = findViewById(idMotif);
        String motif = rbMotif.getText().toString();

        // 12) إنشاء Intent صريح وإرسال البيانات بمفاتيح مبنية بدالة ثابتة
        String m = IntentKeys.MATRICULE;
        Intent i = new Intent(MainActivity.this, VerificationActivity.class);
        i.putExtra(IntentKeys.cleNom(m), nom);
        i.putExtra(IntentKeys.cleNaissance(m), naissance);
        i.putExtra(IntentKeys.cleDestination(m), destination);
        i.putExtra(IntentKeys.cleMotif(m), motif);
        startActivity(i);
    }
}