package models;

import controllers.InscriptionController;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String titre;

    @Column
    private int credits;

    @OneToMany(targetEntity = Inscription.class, mappedBy = "cours")
    @Cascade(CascadeType.ALL)
    private Set<Inscription> inscriptions = new HashSet<>();

    public Cours() {}

    public Cours(String titre, int credits) {
        this.titre = titre;
        this.credits = credits;
    }

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

    void addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
    }

    private Set<Etudiant> getEtudiants() {
        return this.inscriptions
                .stream()
                .map(Inscription::getEtudiant)
                .collect(Collectors.toSet());
    }

    public List<Etudiant> etudiantsEnAttente() throws Exception {
        return InscriptionController.getEnAttente(this);
    }

    @Override
    public String toString() {
        String s = "Cours: " + titre + "\nEtudiants:";

        for(Etudiant e : getEtudiants()) {
            s += "\n- " + e.getPrenom() + " " + e.getNom();
        }

        return s;
    }
}
