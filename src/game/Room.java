package game;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * La classe Room représente une salle dans le jeu avec ses dimensions et textures.
 */
public class Room {

    private int depth = 100;
    private int height = 60;
    private int width = 100;

    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureTop;
    private String textureSouth;
    
     /**
     * Constructeur par défaut de la classe Room qui charge les dimensions et les textures à partir d'un fichier XML.
     */
    public Room() {
        try {
            File xmlFile = new File("data/xml/plateau.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("dimension");
            Node node = nList.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                this.depth = Integer.parseInt(element.getElementsByTagName("depth").item(0).getTextContent());
                this.height = Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());
                this.width = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
            }

            nList = doc.getElementsByTagName("mapping");
            node = nList.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                this.textureBottom = element.getElementsByTagName("textureBottom").item(0).getTextContent();
                this.textureEast = element.getElementsByTagName("textureEast").item(0).getTextContent();
                this.textureWest = element.getElementsByTagName("textureWest").item(0).getTextContent();
                this.textureNorth = element.getElementsByTagName("textureNorth").item(0).getTextContent();
            }
        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Constructeur de la classe Room avec des textures spécifiées.
     *
     * @param textureBottom Texture du bas de la salle.
     * @param textureNorth  Texture du nord de la salle.
     * @param textureEast   Texture de l'est de la salle.
     * @param textureWest   Texture de l'ouest de la salle.
     */
    public Room(String textureBottom, String textureNorth, String textureEast, String textureWest) {
        this.textureBottom = textureBottom;
        this.textureNorth = textureNorth;
        this.textureEast = textureEast;
        this.textureWest = textureWest;
    }
    
    
    /*Les Getters et Setters*/
    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }
}
