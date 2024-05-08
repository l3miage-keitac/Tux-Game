package game;

public class JeuDevineLeMotOrdre extends Jeu {

    // Nombre de lettres restantes à trouver dans le mot
    private int nbLettresRestantes;

    // Chronomètre pour mesurer le temps écoulé
    private Chronometre chrono;

    // Constructeur de la classe JeuDevineLeMotOrdre
    public JeuDevineLeMotOrdre() {
        super();
        // Initialisation du chronomètre avec une durée de 15 secondes
        this.chrono = new Chronometre(15);
    }

    // Méthode pour démarrer une nouvelle partie
    @Override
    protected void démarrePartie(Partie partie) {
        // Initialisation du nombre de lettres restantes à trouver dans le mot
        this.nbLettresRestantes = partie.getMot().length();
        
        // Démarrage du chronomètre
        this.chrono.start();
        
        // Affichage du mot à deviner
        exibeMot(partie.getMot());
        
        // Affichage du timing dans l'environnement de jeu
        env.setDisplayStr("Temps: " + chrono.getTime() + "S", 30, 30);
    }

    // Méthode pour appliquer les règles du jeu
    @Override
    protected boolean appliqueRegles(Partie partie) {
        boolean finish = false;

        // Vérification si une lettre a été trouvée
        if (tuxTrouveLettre()) {
            this.nbLettresRestantes--; // Soustraction du nombre de lettres restantes
        }
        
        // Vérification si toutes les lettres ont été trouvées ou si le temps est écoulé
        if (this.nbLettresRestantes == 0 || !this.chrono.remainsTime()) {
            finish = true;
        }

        return finish;
    }

    // regles de terminus d'une partie
    @Override
    protected void terminePartie(Partie partie) {        
        // Arrêt du chronomètre
        this.chrono.stop();
        
        // Enregistrement du temps et du nombre de lettres trouvées dans la partie
        partie.setTemps(this.chrono.getSeconds());
        partie.setTrouve(nbLettresRestantes);
    }

    // Métho pour vérifier si Tux a trouvé la prochaine lettre dans l'ordre correct
    private boolean tuxTrouveLettre() {
        if (lettres.size() > 0 && collision(lettres.get(0))) {
            // Suppression de la lettre trouvée de l'environnement de jeu
            env.removeObject(lettres.get(0));
            lettres.remove(0);
            return true;
        }
        return false;
    }

    // get nombre de lettres restantes
    private int getNbLettresRestantes() {
        return nbLettresRestantes;
    }

    // get le temps écoulé
    private int getTemps() {
        return this.chrono.getSeconds(); 
    }

    // get afficher le mot à deviner
    private void exibeMot(String mot) {
        System.out.println("Trouve ce mot : " + mot);
        try {
            Thread.sleep(5000); // Attente de 5 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}