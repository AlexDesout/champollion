package champollion;

import java.util.Date;

public class Intervention {
    private final UE ue;
    //private final Salle salle;
    private final TypeIntervention type;
    private final Date debut;
    private final int duree;
    private final int heureDebut;
    private boolean annulee;

    public Intervention(UE ue, TypeIntervention type, Date debut, int duree, int heureDebut) {
        this.ue = ue;
        //this.salle = salle;
        this.type = type;
        this.debut = debut;
        this.duree = duree;
        this.heureDebut = heureDebut;
        this.annulee = false;
    }

    public UE getUe() {
        return ue;
    }

    public TypeIntervention getType() {
        return type;
    }

    public Date getDebut() {
        return debut;
    }

    public int getDuree() {
        return duree;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public boolean isAnnulee() {
        return annulee;
    }

    public void setAnnulee(boolean annulee) {
        this.annulee = annulee;
    }
}
