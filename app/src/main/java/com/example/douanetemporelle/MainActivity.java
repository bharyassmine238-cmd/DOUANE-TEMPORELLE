package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNom, etNaissance, etDestination;
    private RadioGroup rgMotif;
    private Button btnInspecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNom = findViewById(R.id.et_nom_yb);
        etNaissance = findViewById(R.id.et_naissance_yb);
        etDestination = findViewById(R.id.et_destination_yb);
        rgMotif = findViewById(R.id.rg_motif_yb);
        btnInspecter = findViewById(R.id.btn_inspecter_yb);

        btnInspecter.setOnClickListener(v -> inspecterProfil());
    }

    private void inspecterProfil() {
        String nom = etNom.getText().toString().trim();
        String naissanceTxt = etNaissance.getText().toString().trim();
        String destinationTxt = etDestination.getText().toString().trim();

        if (nom.isEmpty()) {
            etNom.setError("Nom obligatoire");
            return;
        }

        if (naissanceTxt.isEmpty() || destinationTxt.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir les deux années", Toast.LENGTH_SHORT).show();
            return;
        }

        int naissance, destination;
        try {
            naissance = Integer.parseInt(naissanceTxt);
            destination = Integer.parseInt(destinationTxt);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Années invalides", Toast.LENGTH_SHORT).show();
            return;
        }

        int idChoisi = rgMotif.getCheckedRadioButtonId();
        if (idChoisi == -1) {
            Toast.makeText(this, "Choisissez un motif de voyage", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rbChoisi = findViewById(idChoisi);
        String motif = rbChoisi.getText().toString();

        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);

        intent.putExtra(Cles.construireCle(Cles.MATRICULE, Cles.NOM), nom);
        intent.putExtra(Cles.construireCle(Cles.MATRICULE, Cles.NAISSANCE), naissance);
        intent.putExtra(Cles.construireCle(Cles.MATRICULE, Cles.DESTINATION), destination);
        intent.putExtra(Cles.construireCle(Cles.MATRICULE, Cles.MOTIF), motif);

        startActivity(intent);
    }
}