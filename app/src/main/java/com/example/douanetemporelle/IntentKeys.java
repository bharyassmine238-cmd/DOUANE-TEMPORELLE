package com.example.douanetemporelle;

// فئة مساعدة: تبني مفاتيح putExtra بدالة ثابتة انطلاقا من رقم بطاقة الطالب (شرط الأستاذ)
public class IntentKeys {

    // 1 : آخر 4 أرقام من بطاقة الطالب (غيّرها بالرقم الحقيقي، وتوجد في هذا المكان فقط)
    public static final String MATRICULE = "1234";

    // 2 : الدالة الثابتة الأساسية: ترجع مثلا "1234_NOM_VOYAGEUR"
    public static String cle(String matricule, String nom) {
        return matricule + "_" + nom;
    }

    // 3 : مفتاح اسم المسافر
    public static String cleNom(String matricule) {
        return cle(matricule, "NOM_VOYAGEUR");
    }

    // 4 : مفتاح سنة الميلاد
    public static String cleNaissance(String matricule) {
        return cle(matricule, "ANNEE_NAISSANCE");
    }

    // 5 : مفتاح سنة الوجهة
    public static String cleDestination(String matricule) {
        return cle(matricule, "ANNEE_DESTINATION");
    }

    // 6 : مفتاح سبب السفر
    public static String cleMotif(String matricule) {
        return cle(matricule, "MOTIF_VOYAGE");
    }

    // 7 : مفتاح القرار النهائي (true = موافقة، false = رفض)
    public static String cleVerdict(String matricule) {
        return cle(matricule, "VERDICT");
    }
}