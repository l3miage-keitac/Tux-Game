package game;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Dico extends DefaultHandler {

    // Listes de mots pour chaque niveau
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;

    // Chemin du fichier du dictionnaire
    private String cheminFichierDico;

    // Constructeur de la classe Dico
    public Dico(String cheminFichierDico) {
        super();

        this.cheminFichierDico = cheminFichierDico;
        listeNiveau1 = new ArrayList<>();
        listeNiveau2 = new ArrayList<>();
        listeNiveau3 = new ArrayList<>();
        listeNiveau4 = new ArrayList<>();
        listeNiveau5 = new ArrayList<>();
    }

    // Méthode pour obtenir un mot depuis la liste correspondant au niveau
    public String getMotDepuisListeNiveaux(int niveau) {
        String mot = null;
        switch (verifieNiveau(niveau)) {
            case 1:
                mot = getMotDepuisListe(listeNiveau1);
                break;
            case 2:
                mot = getMotDepuisListe(listeNiveau2);
                break;
            case 3:
                mot = getMotDepuisListe(listeNiveau3);
                break;
            case 4:
                mot = getMotDepuisListe(listeNiveau4);
                break;
            case 5:
                mot = getMotDepuisListe(listeNiveau5);
                break;
            default:
                break;
        }

        return mot;
    }

    // Méthode pour ajouter un mot au dictionnaire
    public void ajouteMotADico(int niveau, String mot) {

        switch (niveau) {
            case 1:
                listeNiveau1.add(mot);
                break;
            case 2:
                listeNiveau2.add(mot);
                break;
            case 3:
                listeNiveau3.add(mot);
                break;
            case 4:
                listeNiveau4.add(mot);
                break;
            case 5:
                listeNiveau5.add(mot);
                break;
            default:
                break;
        }
    }

    // Méthode pour obtenir le chemin du fichier du dictionnaire
    public String getCheminFichierDico() {
        return cheminFichierDico;
    }

    // Méthode pour vérifier et renvoyer le niveau vérifié
    private int verifieNiveau(int niveau) {
        int niveauVerifie;
        switch (niveau) {
            case 1:
                niveauVerifie = 1;
                break;
            case 2:
                niveauVerifie = 2;
                break;
            case 3:
                niveauVerifie = 3;
                break;
            case 4:
                niveauVerifie = 4;
                break;
            case 5:
                niveauVerifie = 5;
                break;
            default:
                niveauVerifie = (int) ((Math.random()) * 5 + 1);
                break;
        }
        return niveauVerifie;
    }

    // Méthode pour obtenir un mot depuis une liste
    private String getMotDepuisListe(ArrayList<String> list) {
        String mot;
        int indice;

        if (list.isEmpty()) {
            mot = "liste vide";
        } else {
            Random random = new Random();
            indice = random.nextInt(list.size());
            mot = list.get(indice);
        }
        return mot;
    }

    // Méthode pour lire le dictionnaire en utilisant DOM
    public void lireDictionnaireDOM(String path) {
        try {
            // Création d'une fabrique de documents
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Création d'un constructeur de documents
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parsing du fichier XML et création d'un document
            Document document = builder.parse(path);

            // Récupération de la liste des éléments dans le document
            NodeList nodeList = document.getElementsByTagName("mot");

            // Parcours de la liste des éléments mot
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // Conversion du nœud en élément
                    Element element = (Element) node;

                    // Récupération de l'attribut "niveau" de l'élément "mot"
                    String niveau = element.getAttribute("niveau");

                    // Récupération du contenu textuel de l'élément "mot"
                    String mot = element.getTextContent();

                    // Ajout du mot au dictionnaire avec son niveau converti en entier
                    ajouteMotADico(Integer.parseInt(niveau), mot);
                }
            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            // Gestion des exceptions en cas d'erreur lors de la lecture du fichier XML
            e.printStackTrace();
        }
    }

    private StringBuffer buffer;
    private int nivMot;
    private boolean dansDico, dansNivx, dansNiv, dansMot;

    /*
     Variables pour le parsing SAX, on utilise le DOM pour le dico. parsing SAX
    ATTENTION aux erreurs dans la methode characters
    
     */
    // Méthode appelée au début du parsing
    @Override
    public void startDocument() throws SAXException {
        Logger.getLogger(Dico.class.getName()).log(Level.INFO, "Début du parsing SAX");
    }

    // Méthode appelée à la fin du parsing
    @Override
    public void endDocument() throws SAXException {
        Logger.getLogger(Dico.class.getName()).log(Level.INFO, "Fin du parsing SAX");
    }

    // Méthode appelée lorsqu'un élément XML commence
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("dictionnaire")) {
            dansDico = true;
        } else if (qName.equals("mots")) {
            dansNivx = true;
        } else if (qName.equals("mot")) {
            dansNiv = true;
            nivMot = Integer.parseInt(attributes.getValue("niveau"));
            buffer = new StringBuffer();
        }
    }

    // Méthode appelée lorsqu'un élément XML se termine
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("dictionnaire")) {
            dansDico = false;
        } else if (qName.equals("mots")) {
            dansNivx = false;
        } else if (qName.equals("mot")) {
            dansNiv = false;
            ajouteMotADico(nivMot, buffer.toString());
            buffer = null;
        }
    }

    // Méthode appelée lorsqu'il y a des caractères à traiter
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (buffer != null) {
            String word = new String(ch, start, length);
            buffer.append(word);
        }
    }
}
