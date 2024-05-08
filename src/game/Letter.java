package game;

import env3d.advanced.EnvNode;

public class Letter extends EnvNode {
    // Chemin vers les modèles de lettres
    private String PATH = "models/letter/";
    
    // Lettre représentée par cet objet Letter
    private char letter;
    
    // Constructeur de la classe Letter
    public Letter(char letter, double x, double z){
        // Assignation de la lettre et configuration des propriétés graphiques
        this.letter = letter;
        setScale(5.0); //Tailler de la box
        //les escalas par rapport a produndite x largeur x auteur
        setX(x);
        setY(getScale() * 1.1);
        setZ(z);

        // Sélection de la texture en fonction de la lettre
        if(letter == ' '){
            setTexture(PATH+"cube.png");
        } else {
            setTexture(PATH+letter+".png");
        }

        // Chargement du modèle 3D en fonction de la lettre
        setModel(PATH+"cube.obj");
    }
}
