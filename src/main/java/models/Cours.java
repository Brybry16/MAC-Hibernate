package models;

import controllers.InscriptionController;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "cours")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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

    @Any(metaColumn = @Column(name = "enseignant_type"), fetch = FetchType.EAGER)
    @AnyMetaDef(idType = "int", metaType = "string",
        metaValues = {
            @MetaValue(targetEntity = Professeur.class, value = "PROFESSEUR"),
            @MetaValue(targetEntity = ChargeDeCours.class, value = "CHARGE_DE_COURS")
        })
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    public Cours() {}

    public Cours(String titre, int credits, Enseignant enseignant) {
        this.titre = titre;
        this.credits = credits;
        this.enseignant = enseignant;
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
        String s = "Cours: " + titre +
                "\nEnseignant: " + enseignant +
                "\nEtudiants:";

        for(Etudiant e : getEtudiants()) {
            s += "\n- " + e.getPrenom() + " " + e.getNom();
        }

        return s;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
}
