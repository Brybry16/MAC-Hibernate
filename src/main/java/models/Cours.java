package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Cours {
    private int id;
    private String titre;
    private int credits;
    private Set<Inscription> inscriptions = new HashSet<>();

    public Cours() {}

    public Cours(String titre, int credits) {
        this.titre = titre;
        this.credits = credits;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
    }

    public Set<Etudiant> getEtudiants() {
        return this.inscriptions
                .stream()
                .map(Inscription::getEtudiant)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        String s = "Cours: " + titre + "\nEtudiants:";

        for(Etudiant e : getEtudiants()) {
            s.concat("\n- " + e.getPrenom() + " " + e.getNom());
        }

        return s;
    }
}
