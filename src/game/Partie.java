package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Partie {

    // Attributs de la classe Partie
    private String date;    // Date de la partie
    private String mot;     // Mot à deviner
    private int niveau;     // Niveau de la partie
    private int trouve;     // Nombre de lettres trouvées
    private int temps;      // Temps écoulé

    // Constructeur pour créer une nouvelle partie
    public Partie(String date, String mot, int niveau) {
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
        this.trouve = 0;
        this.temps = 0;
    }

    // Constructeur pour créer une partie à partir d'un élément XML
    public Partie(Element partieElt) {
        this.date = partieElt.getAttribute("date");
        this.trouve = Integer.parseInt(partieElt.getAttribute("trouvé").replace("%", ""));
        Element motElement = (Element) partieElt.getElementsByTagName("tux:mot").item(0);
        this.mot = motElement.getTextContent();
        this.niveau = Integer.parseInt(motElement.getAttribute("niveau"));
        Element tempsElement = (Element) partieElt.getElementsByTagName("tux:temps").item(0);
        if (tempsElement != null) {
            this.temps = Integer.parseInt(tempsElement.getTextContent());
        } else {
            this.temps = 0;
        }

        System.out.println(this.mot + " " + niveau + " " + date + " " + temps);
    }

    // Méthode pour obtenir l'élément XML représentant la partie
    public Element getPartie(Document document) {
        Element partieElement = document.createElement("tux:partie");
        partieElement.setAttribute("date", this.date);
        
        // Ajout de l'attribut "trouvé" avec le pourcentage de lettres trouvées
        partieElement.setAttribute("trouvé", Integer.toString(this.trouve) + "%");

        // Ajout de l'élément "tux:temps" si le temps est supérieur à zéro
        if (this.temps > 0) {
            Element tempsElement = document.createElement("tux:temps");
            tempsElement.setTextContent(Integer.toString(this.temps));
            partieElement.appendChild(tempsElement);
        }

        // Ajout de l'élément "tux:mot" avec l'attribut "niveau" et le contenu du mot
        Element motElement = document.createElement("tux:mot");
        motElement.setAttribute("niveau", Integer.toString(this.niveau));
        motElement.setTextContent(this.mot);
        partieElement.appendChild(motElement);

        return partieElement;
    }

    // Méthode pour définir le nombre de lettres trouvées
    public void setTrouve(int nbLettresRestantes) {
        this.trouve = ((getMot().length() - nbLettresRestantes) * 100) / getMot().length();
    }

    // Méthode pour définir le temps écoulé
    public void setTemps(int temps) {
        this.temps = temps;
    }
    
    // Méthode pour obtenir le pourcentage de lettres trouvées
    public int getTrouve(){
        return this.trouve;
    }

    // Méthode pour obtenir le niveau de la partie
    public int getNiveau() {
        return this.niveau;
    }

    // Méthode pour obtenir le mot à deviner
    public String getMot() {
        return this.mot;
    }
    
    // Méthode pour obtenir le temps écoulé
    public int getTemps(){
        return this.temps;
    }

    // Méthode pour obtenir une représentation sous forme de chaîne de la partie
    @Override
    public String toString() {
        return "Data: " + this.date + ", Mot: " + this.mot + ", Niveau: " + this.niveau + ", Lettres trouvées: " + this.trouve + ", Temps: " + this.temps;
    }
}
