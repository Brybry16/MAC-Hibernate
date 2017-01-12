package models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Fichier: <nom>
 * Auteur: Brybry
 * Date de création: 12.01.2017
 * But: <but>
 */
@Entity
public class Inscription {
    private int id;
    private char grade;
    private Cours cours;
    private Etudiant etudiant;

    public Inscription(Etudiant etudiant, Cours cours) {
        this.etudiant = etudiant;
        this.cours = cours;
    }


    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
