package models;

import controllers.InscriptionController;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Fichier: <nom>
 * Auteur: Brybry
 * Date de création: 12.01.2017
 * But: <but>
 */
@Entity
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private char grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cours_id", nullable = true)
    protected Cours cours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = true)
    private Etudiant etudiant;

    public Inscription(Etudiant etudiant, Cours cours) {
        this.etudiant = etudiant;
        this.cours = cours;

        try {
            InscriptionController.create(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

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
