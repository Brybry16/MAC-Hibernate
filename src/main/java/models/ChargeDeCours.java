package models;

import javax.persistence.*;

/**
 * Fichier: <nom>
 * Auteur: Brybry
 * Date de création: 16.01.2017
 * But: <but>
 */
@Entity
@Table(name = "chargedecours")
public class ChargeDeCours implements Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomComplet;

    public ChargeDeCours() {}

    public ChargeDeCours(String nomComplet) {
        this.nomComplet = nomComplet;
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
