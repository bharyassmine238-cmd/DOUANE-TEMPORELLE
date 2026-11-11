package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;


//El classe el ra2isiya: formulaire mta3 tasjil el mousefer 3abr ezzaman (el activité el loula)
public class MainActivity extends AppCompatActivity {
    private VideoView videoFond;
    private int position = 0;

    // 1)hedhom les donnés matloubin fl formulaire
    EditText etNom, etNaissance, etDestination;
    RadioGroup rgMotif;
    Button btnInspecter;
    // 1.1)3malt des limite lil a3wem (faza)
    private static final int ANNEE_NAISSANCE_MIN = 1900;
    private static final int ANNEE_DESTINATION_MIN = 1000;
    private static final int ANNEE_DESTINATION_MAX = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        videoFond = findViewById(R.id.video_portail_yb);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.portail_temporel);
        videoFond.setVideoURI(uri);

        videoFond.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0f, 0f);
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            }
        });

        // 2) zedt margin lil contenu
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_yb), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // 3)Rbatna les element bl XML a travers les IDs
        etNom = findViewById(R.id.et_nom_yb);
        etNaissance = findViewById(R.id.et_naissance_yb);
        etDestination = findViewById(R.id.et_destination_yb);
        rgMotif = findViewById(R.id.rg_motif_yb);
        btnInspecter = findViewById(R.id.btn_inspecter_yb);

        // 4)Ki nenzlou 3al bouton n3ayetou lel fonction mta3 el vérification
        btnInspecter.setOnClickListener(v -> inspecterProfil());
    }
    @Override
    protected void onResume() {
        super.onResume();
        videoFond.seekTo(position);
        videoFond.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        position = videoFond.getCurrentPosition();
        videoFond.pause();
    }

    private void inspecterProfil() {
        // 5) ya9ra les valeurs eli da5lnehom
        String nom = etNom.getText().toString().trim();
        String naissanceTxt = etNaissance.getText().toString().trim();
        String destinationTxt = etDestination.getText().toString().trim();

        // 6)Nthabtou ml esm (mathaln tnjm tkoun chaine vide so lezm nthabtou)
        if (nom.isEmpty()) {
            etNom.setError("Nom obligatoire");
            etNom.requestFocus();
            return;
        }

        // 7) KIFKIF nthabtou ml a3wem
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

        // 8)Nbaddlou les textes l ar9am ghalta (+ error handling)
        int naissance, destination;
        try {
            naissance = Integer.parseInt(naissanceTxt);
            destination = Integer.parseInt(destinationTxt);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Années invalides", Toast.LENGTH_SHORT).show();
            return;
        }

// 9)variable feha 3am sne
        int anneeActuelle = Calendar.getInstance().get(Calendar.YEAR);

// 10) Sanet el milad lazem tkoun bin el had el adna w essna el hadhra
        if (naissance < ANNEE_NAISSANCE_MIN || naissance > anneeActuelle) {
            etNaissance.setError("Année de naissance entre " + ANNEE_NAISSANCE_MIN + " et " + anneeActuelle);
            etNaissance.requestFocus();
            return;
        }
        // c yasmine zedet'ha bch nverifi enou el date de dest mehich kbal el date de naissance
        if (destination < ANNEE_DESTINATION_MIN || destination > ANNEE_DESTINATION_MAX) {
            etDestination.setError("Année entre " + ANNEE_DESTINATION_MIN + " et " + ANNEE_DESTINATION_MAX);
            etDestination.requestFocus();
            return;
        }
        if (destination <= naissance) {
            etDestination.setError("La destination doit être après la naissance");
            etDestination.requestFocus();
            return;
        }//bon kont najem naamel alerte zeda mais seterror plus simple hasit'ha sinon bch njarabha fl motif



        // 11)nthabtou ml choix ta sbab esfar
        int idMotif = rgMotif.getCheckedRadioButtonId();
        if (idMotif == -1) {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Motif manquant")
                    .setMessage("Choisissez un motif de voyage.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }
        RadioButton rbMotif = findViewById(idMotif);
        String motif = rbMotif.getText().toString();

        // 12)Nasn3ou Intent (explicit) w nab3thou el données bel clés ta fonction
        String m = IntentKeys.MATRICULE;
        Intent i = new Intent(MainActivity.this, VerificationActivity.class);
        i.putExtra(IntentKeys.cleNom(m), nom);
        i.putExtra(IntentKeys.cleNaissance(m), naissance);
        i.putExtra(IntentKeys.cleDestination(m), destination);
        i.putExtra(IntentKeys.cleMotif(m), motif);
        startActivity(i);
    }
}