package game;

import env3d.Env;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public abstract class Jeu {

    String PATH_PROFIL = "data/xml/profils/";
    String PATH = "data/xml/";
    String XML = ".xml";

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    final Env env;
    private Tux tux;
    private final Room mainRoom;
    private final Room menuRoom;
    private Letter letter;
    private Profil profil;
    private final Dico dico;
    protected EnvTextMap menuText;//text (affichage des texte du jeu)

    ArrayList<Letter> lettres;
    String mot;
    String nomJoueur;

    public Jeu() {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Dictionnaire
        dico = new Dico(PATH + "dico" + XML);
        dico.lireDictionnaireDOM(PATH + "dico" + XML);

        // Instancie une liste de lettres(vide)
        lettres = new ArrayList<Letter>();

        // instancie le menuText
        menuText = new EnvTextMap(env);

        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);

        //  joueur
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);

        // choisir un niveau 
        menuText.addText("Choisissez un niveau [1-5] : ", "Niveau", 200, 300);

        //mot à trouver 
        menuText.addText("Trouvez le mot : ", "AfficheMot", 200, 300);

        //choix de partie à charger
        menuText.addText("Quelle partie souhaitez vous charger ?", "ChoixPartieCharge", 200, 350);
        menuText.addText("Entrez votre anniversaire [DDxMMxAAAA]: ", "anniversaireJoueur", 200, 300);
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {

        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }

    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }

    // fourni, à compléter
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;

        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

            // restaure la room du jeu
            env.setRoom(mainRoom);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    // .......... dico.******
                    int niveau = getNiveau();
                    mot = dico.getMotDepuisListeNiveaux(niveau);
                    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    // crée un nouvelle partie
                    Partie partie = new Partie(date, mot, niveau);

                    afficheMot(mot);

                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil

                    profil.ajouterParties(partie);
                    profil.sauvegarder(PATH_PROFIL + profil.getNom() + XML);
                    profil.toXML(PATH_PROFIL + profil.getNom() + XML);
                    // .......... profil.******
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2:
                    afficherListeParties();
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private MENU_VAL menuPrincipal() {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();

                // charge le profil de ce joueur si possible
                profil = new Profil(PATH_PROFIL + nomJoueur + XML);
                if (profil.charge(PATH_PROFIL + nomJoueur + XML)) {
                    choix = menuJeu();
                } else {
                    choix = MENU_VAL.MENU_SORTIE;//CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                String dateNaissaince = getAnniversaire();

                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur, dateNaissaince);
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    public void joue(Partie partie) {

        // Instancie un Tux
        tux = new Tux(env, mainRoom);

        env.addObject(tux);

        // clear
        lettres.clear();

        // random
        for (char lettre : partie.getMot().toCharArray()) {
            double posX = Math.random() * 100;
            double posZ = Math.random() * 100;
            Letter letter = new Letter(lettre, posX, posZ);
            lettres.add(letter);
            env.addObject(letter);
        }

        env.addObject(tux);
        démarrePartie(partie);

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);

        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.déplace();

            // Ici, on applique les regles
            finished = appliqueRegles(partie);

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);

    }

    //Calcule de la collision entre le TUX et les Lesttres
    protected double distance(Letter letter) {
        double tuxX = tux.getX();
        double tuxY = tux.getY();
        double tuxZ = tux.getZ();

        double letterX = letter.getX();
        double letterY = letter.getY();
        double letterZ = letter.getZ();

        double distanceX = tuxX - letterX;
        double distanceY = tuxY - letterY;
        double distanceZ = tuxZ - letterZ;

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
    }

    //return vrais ssi TUX "touche la box"
    protected boolean collision(Letter letter) {
        return distance(letter) <= 15.0;
    }

    //methodes abstrait
    protected abstract void démarrePartie(Partie partie);

    protected abstract boolean appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);
    //

    /*
      demmnde du niveau par interface graphique
      si le niveau n'est pas choisit entre 1 et 5, alors par defaut
      est affecte niveai facille (1)
     */
    private int getNiveau() {
        int niveau;
        menuText.getText("Niveau").display();
        niveau = Integer.parseInt(menuText.getText("Niveau").lire(true));
        if (niveau > 5 || niveau < 1) {
            niveau = 1;
        }
        menuText.getText("Niveau").clean();
        env.advanceOneFrame();
        return niveau;
    }

    //On prends la date du anniversaire du player
    private String getAnniversaire() {
        menuText.getText("anniversaireJoueur").display();
        String anniversaire = "";
        anniversaire = menuText.getText("anniversaireJoueur").lire(true);
        return anniversaire.replace("x", "/");
    }

    // affiche le mot à trouver pendant 5 secondes
    private void afficheMot(String mot) {
        menuText.getText("AfficheMot").addTextAndDisplay("", mot);
        Chronometre chrono = new Chronometre(3);
        chrono.start();
        while (chrono.remainsTime()) {
            env.advanceOneFrame();
        }
        //efface l'affichage 
        chrono.stop();
        menuText.getText("AfficheMot").clean();

    }

    //methode d'affichage de la liste de partiers d'un player
    private void afficherListeParties() {
        // Liste les parties du profil
        ArrayList<Partie> parties = profil.getParties();
        // Affiche les options de parties disponibles
        menuText.addText("0 pour Retourner au menu principal ou choisir une partie par INDICE", "ChoixPartieCharge", 100, 300);
        menuText.getText("ChoixPartieCharge").display();

        for (int i = 0; i < parties.size(); i++) {
            Partie partie = parties.get(i);
            System.out.println(partie);
            menuText.addText("indice: " + (i + 1) + "| mot: " + partie.getMot() + "| Niveau: " + partie.getNiveau(), "Partie" + (i + 1), 200, 240 - i * 20);
            menuText.getText("Partie" + (i + 1)).display();
        }

        String choix = menuText.getText("ChoixPartieCharge").lire(true);
        System.out.println(choix);
        int choixPartie = Integer.parseInt(choix);
        menuText.getText("ChoixPartieCharge").clean();
        // Ajoute une option pour retourner au menu principal
        env.advanceOneFrame();

        //supprime la liste du display
        for (int i = 0; i < parties.size(); i++) {
            menuText.getText("Partie" + (i + 1)).clean();
        }

        if (choixPartie == 0) {
            // Retourne au menu principal
            return;
        }
        
        // Récupère la partie sélectionnée
        Partie partieChoisie = parties.get(choixPartie - 1);
        // Joue la partie choisie
        joue(partieChoisie);
        // Enregistre la partie dans le profil
        profil.ajouterParties(partieChoisie);
        profil.sauvegarder("data/xml/profils/" + profil.getNom() + ".xml");
        profil.toXML("data/xml/profils/" + profil.getNom() + ".xml");
    }
}
