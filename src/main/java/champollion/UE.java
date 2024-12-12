package champollion;

import lombok.Getter;

public class UE {
    private final String myIntitule;
    @Getter
    private int volumeCM;
    @Getter
    private int volumeTD;
    @Getter
    private int volumeTP;

    public UE(String intitule) {
        myIntitule = intitule;
    }

    public String getIntitule() {
        return myIntitule;
    }


}
