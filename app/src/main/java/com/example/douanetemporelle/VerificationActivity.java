package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VerificationActivity extends AppCompatActivity {

    // 1) El 3anaser mta3 el interface
    TextView tvNomYb, tvDestinationYb, tvAgeYb, tvMotifYb;
    Button btnApprouverYb, btnRefuserYb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verification);

        // إضافة جديدة: هامش حتى لا يختفي المحتوى تحت شريط الحالة (مع الحفاظ على هامش 12dp)
        View racine = findViewById(R.id.layout_verification_yb);
        final int marge = (int) (12 * getResources().getDisplayMetrics().density);
        ViewCompat.setOnApplyWindowInsetsListener(racine, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + marge, bars.top + marge, bars.right + marge, bars.bottom + marge);
            return insets;
        });

        // 2) Nrabtou el 3anaser bel XML
        tvNomYb = findViewById(R.id.tv_nom_value_yb);
        tvDestinationYb = findViewById(R.id.tv_destination_value_yb);
        tvAgeYb = findViewById(R.id.tv_age_value_yb);
        tvMotifYb = findViewById(R.id.tv_motif_value_yb);
        btnApprouverYb = findViewById(R.id.btn_approuver_yb);
        btnRefuserYb = findViewById(R.id.btn_refuser_yb);

        // 3) Nesta9blou el données bel clés elli matnaya bel fonction
        String m = IntentKeys.MATRICULE;
        Intent intent = getIntent();
        String nomRecu = intent.getStringExtra(IntentKeys.cleNom(m));
        String nom = (nomRecu == null || nomRecu.trim().isEmpty()) ? "Voyageur" : nomRecu;
        int naissance = intent.getIntExtra(IntentKeys.cleNaissance(m), 0);
        int destination = intent.getIntExtra(IntentKeys.cleDestination(m), 0);
        String motif = intent.getStringExtra(IntentKeys.cleMotif(m));

        // 4) nehsbou el 3mor ezzamani (el farq bin 3am el dest w 3am el wileda)
        int age = destination - naissance;

        // 5) nhotou resumé
        tvNomYb.setText(nom);
        tvDestinationYb.setText(String.valueOf(destination));
        tvAgeYb.setText(age >= 0 ? age + " ans" : "Pas encore né (" + Math.abs(age) + " ans avant)");
        tvMotifYb.setText(motif);

        // 6) N3attlou btn el mowafa9a ken el sabeb "Modification du passé"
        //    w el farq (bel 9ima el motlaka) 3adad awwali
        boolean modification = motif != null && motif.contains("Modification");
        if (modification && estPremier(Math.abs(age))) {
            btnApprouverYb.setEnabled(false);
            btnApprouverYb.setAlpha(0.4f);
        }

        // 7) bouton el mowafa9a: nab3thou el decision true
        btnApprouverYb.setOnClickListener(v -> ouvrirVerdict(nom, true));

        // 8) bouton el rafdh: nab3thou el decision false

        // c yasmine encore 1 fois hehe j'ai mis alerte bch nconfirmi kbal el refus taarech aliha tnajem tkoun amalt clic par erreur mch belaani
        btnRefuserYb.setOnClickListener(v ->
                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Refuser ce voyageur ?")
                        .setPositiveButton("Oui", (d, w) -> ouvrirVerdict(nom, false))
                        .setNegativeButton("Annuler", null)
                        .show());
    }

    // 9) Nfat7ou écran ta decision m3a el esm w el choix (valeur booléenne)
    private void ouvrirVerdict(String nom, boolean approuve) {
        String m = IntentKeys.MATRICULE;
        Intent i = new Intent(VerificationActivity.this, ResultActivity.class);
        i.putExtra(IntentKeys.cleNom(m), nom);
        i.putExtra(IntentKeys.cleVerdict(m), approuve);
        startActivity(i);
    }

    // 10) ntestiw el nombre premier
    private boolean estPremier(int n) {
        if (n < 2) return false;
        for (int d = 2; d * d <= n; d++) {
            if (n % d == 0) return false;
        }
        return true;
    }
}