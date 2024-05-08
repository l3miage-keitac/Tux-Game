package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 * La classe Tux représente le personnage principal du jeu.
 */
public class Tux extends EnvNode {

    private Env env;
    private Room room;

    /**
     * Constructeur de la classe Tux.
     *
     * @param env  L'environnement 3D dans lequel Tux évolue.
     * @param room La salle dans laquelle Tux se déplace.
     */
    Tux(Env env, Room room) {
        this.env = env;
        this.room = room;
        setScale(10.0);
        setX(room.getWidth() / 2);
        setY(getScale() * 1.1);
        setZ(room.getDepth() / 2);
        setTexture("models/tux/tux.png");
        setModel("models/tux/tux.obj");
    }

    /**
     * Méthode pour déplacer Tux en fonction des touches du clavier.
     */
    public void déplace() {
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) {
            this.setRotateY(180);
            if (roomCollision(getZ() - 1.0, 9)) {
                this.setZ(10.0);
            } else {
                this.setZ(this.getZ() - 1.0);
            }
        }

        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) {
            this.setRotateY(360);
            if (roomCollision(getZ() + 1, 98)) {
                this.setZ(97.0);
            } else {
                this.setZ(this.getZ() + 1.0);
            }
        }

        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) {
            this.setRotateY(90);
            if (roomCollision(getX() + 1, 92)) {
                this.setX(91.0);
            } else {
                this.setX(this.getX() + 1.0);
            }
        }

        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) {
            this.setRotateY(-90);
            if (roomCollision(this.getX() - 1.0, 8.0)) {
                this.setX(9.0);
            } else {
                this.setX(this.getX() - 1.0);
            }
        }
    }

    /**
     * Vérifie si Tux entre en collision avec les limites de la salle.
     *
     * @param deplacement La position de déplacement de Tux.
     * @param valeurColl  La valeur de collision correspondant aux limites de la salle.
     * @return true si une collision est détectée, sinon false.
     */
    boolean roomCollision(double deplacement, double valeurColl) {
        return deplacement == valeurColl;
    }
}