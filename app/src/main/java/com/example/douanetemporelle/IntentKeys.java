package com.example.douanetemporelle;

public class IntentKeys {

    // 1) آخر 4 أرقام من بطاقة الطالب (غيّرها برقمك الحقيقي)
    public static final String MATRICULE = "1658";

    // 2) دالة ثابتة تبني المفتاح: رقم التسجيل + اسم الحقل
    public static String construireCle(String matricule, String champ) {
        return matricule + "_" + champ;
    }

    // 3) مفتاح لكل معلومة
    public static String cleNom(String m)         { return construireCle(m, "NOM_VOYAGEUR"); }
    public static String cleNaissance(String m)   { return construireCle(m, "ANNEE_NAISSANCE"); }
    public static String cleDestination(String m) { return construireCle(m, "ANNEE_DESTINATION"); }
    public static String cleMotif(String m)       { return construireCle(m, "MOTIF"); }
    public static String cleVerdict(String m)     { return construireCle(m, "VERDICT"); }
}