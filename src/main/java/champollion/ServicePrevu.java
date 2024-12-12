package champollion;

// Classe ServicePrevu
public class ServicePrevu {
    private final UE ue;
    private int volumeCM;
    private int volumeTD;
    private int volumeTP;

    public ServicePrevu(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        this.ue = ue;
        this.volumeCM = volumeCM;
        this.volumeTD = volumeTD;
        this.volumeTP = volumeTP;
    }

    public UE getUe() {
        return ue;
    }

    public int getVolumeCM() {
        return volumeCM;
    }

    public int getVolumeTD() {
        return volumeTD;
    }

    public int getVolumeTP() {
        return volumeTP;
    }

    public void ajouterVolumes(int cm, int td, int tp) {
        this.volumeCM += cm;
        this.volumeTD += td;
        this.volumeTP += tp;
    }

}
