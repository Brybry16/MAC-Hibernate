package models;

import controllers.EtudiantController;
import controllers.InscriptionController;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "etudiant")
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
    private Calendar dateInscription;

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    @OneToMany(targetEntity = Inscription.class, mappedBy = "etudiant")
    @Cascade(CascadeType.ALL)
    private Set<Inscription> inscriptions = new HashSet<>();

    public Etudiant() {}

    public Etudiant(String prenom, String nom, Calendar dateInscription) {
        this.prenom = prenom;
        this.nom = nom;
        this.dateInscription = dateInscription;
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

    public Calendar getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Calendar date_inscription) {
        this.dateInscription = date_inscription;
    }

    public void ajouterCours(Cours... cours) {
        for(Cours c : cours) {
            Inscription i = new Inscription(this, c);
            inscriptions.add(i);
            c.addInscription(i);
        }
    }

    private List<Cours> getCours() throws Exception {
        return InscriptionController.getCours(this);
        /*return this.inscriptions
                .stream()
                .map(Inscription::getCours)
                .collect(Collectors.toSet());*/
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

    @SuppressWarnings("All")
    public List<Enseignant> getEnseignants() throws Exception {
        return EtudiantController.getEnseignants(this);
    }

    @Override
    public String toString() {
        String s = "Etudiant: " + prenom + " " + nom + "\nDate d'inscription: " + dateInscription + "\nCours:";

        try {
            for(Cours c : getCours()) {
                s += "\n- " + c.getTitre();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
}
