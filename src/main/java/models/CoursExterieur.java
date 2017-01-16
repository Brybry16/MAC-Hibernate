package models;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Fichier: <nom>
 * Auteur: Brybry
 * Date de création: 16.01.2017
 * But: <but>
 */
@Entity
public class CoursExterieur extends Cours {

    @Column
    private String ecole;

    public CoursExterieur(String titre, int credits, Enseignant enseignant, String ecole) {
        super(titre, credits, enseignant);
        this.ecole = ecole;
    }

    public String getEcole() {
        return ecole;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public String toString() {
        return "Ecole: " + ecole + " (cours extérieur)\n" + super.toString();
    }
}
