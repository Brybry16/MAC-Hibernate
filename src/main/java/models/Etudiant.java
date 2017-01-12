package models;

import controllers.InscriptionController;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Etudiant {
    private int id;
    private String prenom;
    private String nom;
    private Date date_inscription;
    private Set<Inscription> inscriptions = new HashSet<>();

    public Etudiant() {}

    public Etudiant(String prenom, String nom, Date date_inscription) {
        this.prenom = prenom;
        this.nom = nom;
        this.date_inscription = date_inscription;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    public void ajouterCours(Cours... cours) {
        for(Cours c : cours) {
            Inscription i = new Inscription(this, c);
            inscriptions.add(i);
            c.addInscription(i);
        }
    }

    public Set<Cours> getCours() {
        return this.inscriptions
                .stream()
                .map(Inscription::getCours)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        String s = "Etudiant: " + prenom + " " + nom + "\nDate d'inscription: " + date_inscription + "\nCours:";

        for(Cours c : getCours()) {
            s.concat("\n- " + c.getTitre());
        }

        return s;
    }
}
