package com.example.douanetemporelle;

public class IntentKeys {

    // 1) Akher 4 arqam men el carte etudiant (7atina ra9em hedha 1658)
    public static final String MATRICULE = "1658";
    // 2) Fonction statique tebni el clé: raqm el tasjil + esm el champ
    public static String construireCle(String matricule, String champ) {
        return matricule + "_" + champ;
    }

    // 3) Clé l kol ma3louma
    public static String cleNom(String m)         { return construireCle(m, "NOM_VOYAGEUR"); }
    public static String cleNaissance(String m)   { return construireCle(m, "ANNEE_NAISSANCE"); }
    public static String cleDestination(String m) { return construireCle(m, "ANNEE_DESTINATION"); }
    public static String cleMotif(String m)       { return construireCle(m, "MOTIF"); }
    public static String cleVerdict(String m)     { return construireCle(m, "VERDICT"); }
}