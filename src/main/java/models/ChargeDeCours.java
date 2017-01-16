package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Fichier: <nom>
 * Auteur: Brybry
 * Date de création: 16.01.2017
 * But: <but>
 */
public class ChargeDeCours implements Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nomComplet;

    public ChargeDeCours(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String toString() {
        return nomComplet;
    }
}
