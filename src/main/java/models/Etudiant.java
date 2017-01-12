package models;

import controllers.InscriptionController;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

;

@Entity
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String prenom;

    @Column
    private String nom;


    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_inscription;

    @OneToMany(targetEntity = Inscription.class, mappedBy = "etudiant")
    @Cascade(CascadeType.ALL)
    private Set<Inscription> inscriptions = new HashSet<>();

    public Etudiant() {}

    public Etudiant(String prenom, String nom, Date date_inscription) {
        this.prenom = prenom;
        this.nom = nom;
        this.date_inscription = date_inscription;
    }

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

    private Set<Cours> getCours() {
        return this.inscriptions
                .stream()
                .map(Inscription::getCours)
                .collect(Collectors.toSet());
    }

    public void attribuerGrade(Cours cours, char grade) throws Exception {
        for(Inscription i : inscriptions) {
            if(i.getCours() == cours) {
                i.setGrade(grade);
                InscriptionController.update(i);
                return;
            }
        }

        throw new Exception("L'étudiant n'est pas inscrit au cours");
    }

    public List<Cours> coursNonCredites() throws Exception {
        return InscriptionController.getNonCredites(this);
    }

    @Override
    public String toString() {
        String s = "Etudiant: " + prenom + " " + nom + "\nDate d'inscription: " + date_inscription + "\nCours:";

        for(Cours c : getCours()) {
            s += "\n- " + c.getTitre();
        }

        return s;
    }
}
