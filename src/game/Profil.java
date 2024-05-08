package game;

import java.util.ArrayList;
import java.util.logging.Level;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;

public class Profil {

    private String nom;
    private String dataNaissance;
    private String avatar;
    private ArrayList<Partie> parties;
    public Document document;

    /**
     * Constructeur de la classe Profil pour créer un nouveau profil avec nom et
     * date de naissance.
     *
     * @param nome Le nom du joueur.
     * @param dataNaissance La date de naissance du joueur.
     */
    public Profil(String nome, String dataNaissance) {
        this.nom = nome;
        this.dataNaissance = dataNaissance;
        this.parties = new ArrayList<Partie>();
        //l'avatar pour tous est egaux
        avatar = "";

    }

    /**
     * Constructeur de la classe Profil pour créer un profil à partir d'un
     * fichier XML existant. Charge les informations du profil, y compris le
     * nom, la date de naissance, l'avatar, et les parties jouées, à partir d'un
     * fichier XML.
     *
     * @param nomFichier Le chemin du fichier XML contenant les informations du
     * profil.
     */
    Profil(String nomFichier) {
        this.parties = new ArrayList<Partie>();
        document = fromXML(nomFichier);
        if (document != null) {
            NodeList nodeList = document.getElementsByTagName("tux:profil");
            if (nodeList.getLength() > 0) {
                Element profilElement = (Element) nodeList.item(0);
                this.nom = profilElement.getElementsByTagName("tux:nom").item(0).getTextContent();
                this.dataNaissance = profilElement.getElementsByTagName("tux:anniversaire").item(0).getTextContent();
                if (profilElement.getElementsByTagName("tux:avatar").getLength() > 0) {
                    this.avatar = profilElement.getElementsByTagName("tux:avatar").item(0).getTextContent();
                }
                NodeList partieNodeList = profilElement.getElementsByTagName("tux:partie");
                for (int i = 0; i < partieNodeList.getLength(); i++) {
                    Element partieElement = (Element) partieNodeList.item(i);
                    Partie partie = new Partie(partieElement);
                    ajouterParties(partie);
                }
            }
        } else {
            System.out.println("Erreur: Document XML non charge.");
        }
    }

    /* Méthode pour ajouter une partie à la liste de parties */
    public void ajouterParties(Partie partie) {
        this.parties.add(partie);
    }

    /* Méthode pour récupérer la liste de parties */
    public ArrayList<Partie> getParties() {
        return this.parties;
    }

    /* Méthode pour récupérer le nom du profil */
    public String getNom() {
        return this.nom;
    }

    /* Méthode pour récupérer la date de naissance du profil */
    public String getAnniversaire() {
        return this.dataNaissance;
    }

    public void toXML(String nomeFicheiro) {
        try {
            XMLUtil.DocumentTransform.writeDoc(document, nomeFicheiro);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Document fromXML(String fileName) {
        try {
            return XMLUtil.DocumentFactory.fromFile(fileName);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }

    public static String profileDateToXmlDate(String profileDate) {
        String date = "";

        if (profileDate != null && profileDate.contains("/")) {
            date = profileDate.substring(profileDate.lastIndexOf("/") + 1) + "-";
            date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/")) + "-";
            date += profileDate.substring(0, profileDate.indexOf("/"));
        }
        System.out.println(date + "dans profile data to xml");
        return date;
    }

    boolean charge(String nomJoueur) {
        try {
            return (XMLUtil.DocumentFactory.fromFile(nomJoueur) != null);
        } catch (Exception e) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("erreur : profil non trouvé");
        }
        return false;
    }

    public void sauvegarder(String fileName) {
        try {
            avatar = "conan.png";
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            Element profilElement = document.createElementNS("http://myGame/tux", "tux:profil");
            profilElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            profilElement.setAttribute("xsi:schemaLocation", "http://myGame/tux ../../xsd/profil.xsd");

            Element nomElement = document.createElement("tux:nom");
            nomElement.setTextContent(getNom());
            profilElement.appendChild(document.createTextNode("\n    "));
            profilElement.appendChild(nomElement);

            Element avatarElement = document.createElement("tux:avatar");
            avatarElement.setTextContent(avatar);
            profilElement.appendChild(document.createTextNode("\n    "));
            profilElement.appendChild(avatarElement);

            Element anniversaireElement = document.createElement("tux:anniversaire");
            anniversaireElement.setTextContent(profileDateToXmlDate(getAnniversaire()));
            profilElement.appendChild(document.createTextNode("\n    "));
            profilElement.appendChild(anniversaireElement);

            Element partiesElement = document.createElement("tux:parties");

            profilElement.appendChild(document.createTextNode("\n    "));
            profilElement.appendChild(partiesElement);

            for (Partie partie : this.parties) {
                Element partieElement = partie.getPartie(document);
                partiesElement.appendChild(document.createTextNode("\n        "));
                partiesElement.appendChild(partieElement);
            }

            profilElement.appendChild(document.createTextNode("\n"));
            document.appendChild(profilElement);
        } catch (ParserConfigurationException | DOMException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AjoutePartieFichier(String date, String mot, String niveau) {
        // creer une 'partie'
        if (document == null) {
            try {
                // creer doc
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.newDocument();

                Element profilElement = document.createElementNS("http://myGame/tux", "tux:profil");
                profilElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                profilElement.setAttribute("xsi:schemaLocation", "http://myGame/tux ../xsd/profil.xsd");
                document.appendChild(profilElement);

                Element nomElement = document.createElement("tux:nom");
                nomElement.setTextContent(nom);
                profilElement.appendChild(nomElement);

                Element avatarElement = document.createElement("tux:avatar");
                avatarElement.setTextContent(avatar);
                profilElement.appendChild(avatarElement);

                Element anniversaireElement = document.createElement("tux:anniversaire");
                anniversaireElement.setTextContent(dataNaissance);
                profilElement.appendChild(anniversaireElement);

                Element partiesElement = document.createElement("tux:parties");
                profilElement.appendChild(partiesElement);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        } else {
            Element partie = document.createElement("tux:partie");

            Attr attr = document.createAttribute("date");
            attr.setValue(date);
            partie.setAttributeNode(attr);

            Element motElement = document.createElement("tux:mot");

            attr = document.createAttribute("niveau");
            attr.setValue(niveau);
            motElement.setAttributeNode(attr);

            motElement.appendChild(document.createTextNode(mot));

            partie.appendChild(motElement);

            NodeList nodeList = document.getElementsByTagName("tux:parties");
            if (nodeList.getLength() > 0) {
                nodeList.item(0).appendChild(partie);
            }

            Partie nouvellePartie = new Partie(partie);
            ajouterParties(nouvellePartie);
        }
    }

}
