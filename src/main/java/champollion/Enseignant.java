package champollion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Un enseignant est caractérisé par les informations suivantes : son nom, son adresse email, et son service prévu,
 * et son emploi du temps.
 */
public class Enseignant extends Personne {
    private final HashMap<UE, ServicePrevu> servicesPrevus = new HashMap<>();
    private final List<Intervention> interventions = new ArrayList<>();

    // TODO : rajouter les autres méthodes présentes dans le diagramme UML

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     */
    public int heuresPrevues() {
        return servicesPrevus.values().stream()
            .mapToInt(service -> service.getVolumeCM() + service.getVolumeTD() + service.getVolumeTP())
            .sum();
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     */
    public int heuresPrevuesPourUE(UE ue) {
        if (!servicesPrevus.containsKey(ue)) {
            return 0;
        }

        ServicePrevu service = servicesPrevus.get(ue);
        return service.getVolumeCM() + service.getVolumeTD() + service.getVolumeTP();
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue       l'UE concernée
     * @param volumeCM le volume d'heures de cours magistral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        if (servicesPrevus.containsKey(ue)) {
            // Mise à jour des heures si l'UE est déjà présente
            ServicePrevu service = servicesPrevus.get(ue);
            service.ajouterVolumes(volumeCM, volumeTD, volumeTP);
        } else {
            // Ajout d'une nouvelle UE
            servicesPrevus.put(ue, new ServicePrevu(ue, volumeCM, volumeTD, volumeTP));
        }
    }

    public void ajouteIntervention(Intervention intervention) {
        // Vérifie que l'intervention correspond à une UE enseignée par cet enseignant
        if (!servicesPrevus.containsKey(intervention.getUe())) {
            throw new IllegalArgumentException("Cette UE n'est pas enseignée par cet enseignant.");
        }

        ServicePrevu service = servicesPrevus.get(intervention.getUe());
        int heuresRestantes;
        switch (intervention.getType()) {
            case CM:
                heuresRestantes = service.getVolumeCM();
                break;
            case TD:
                heuresRestantes = service.getVolumeTD();
                break;
            case TP:
                heuresRestantes = service.getVolumeTP();
                break;
            default:
                throw new IllegalArgumentException("Type d'intervention inconnu.");
        }

        if (intervention.getDuree() > heuresRestantes) {
            throw new IllegalArgumentException("Les heures prévues sont insuffisantes pour cette intervention.");
        }

        interventions.add(intervention);
    }

    public boolean enSousService() {
        return heuresPrevues() < 192;
    }

    public int resteAPlanifier(UE ue, TypeIntervention type) {
        // Vérifier que l'UE est enseignée par l'enseignant
        if (!servicesPrevus.containsKey(ue)) {
            throw new IllegalArgumentException("Cette UE n'est pas enseignée par cet enseignant.");
        }

        ServicePrevu service = servicesPrevus.get(ue);
        int heuresPrevues;
        switch (type) {
            case CM:
                heuresPrevues = service.getVolumeCM();
                break;
            case TD:
                heuresPrevues = service.getVolumeTD();
                break;
            case TP:
                heuresPrevues = service.getVolumeTP();
                break;
            default:
                throw new IllegalArgumentException("Type d'intervention inconnu.");
        }

        // Calculer les heures déjà planifiées pour ce type d'intervention et cette UE
        int heuresPlanifiees = interventions.stream()
            .filter(intervention -> intervention.getUe().equals(ue) && intervention.getType() == type)
            .mapToInt(Intervention::getDuree)
            .sum();

        return Math.max(0, heuresPrevues - heuresPlanifiees);
    }



}
