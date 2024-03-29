package models;

import javax.persistence.*;

/**
 * Fichier: <nom>
 * Auteur: Brybry
 * Date de création: 16.01.2017
 * But: <but>
 */
@Entity
@Table(name = "professeur")
public class Professeur implements Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String prenom;

    private String nom;

    private String sigle;

    public Professeur() {}

    public Professeur(String prenom, String nom, String sigle) {
        this.prenom = prenom;
        this.nom = nom;
        this.sigle = sigle;
    }

    public String toString() {
        return prenom + " " + nom + " (" + sigle + ")";
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

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }
}
