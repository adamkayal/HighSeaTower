//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.*;

/**
 * Parametres principaux du jeu
 */
public class Jeu {

    public static final int WIDTH = 350, HEIGHT = 480;
    private double fenetreY = 0, vFenetreY = -50, hautNouvPlate = 380;

    private ArrayList<Plateforme> plateformes = new ArrayList<>();
    private Plateforme lastPlateforme = null, newPlateforme;
    private Bulles[] bulles;
    private Meduse meduse;
    private Trampoline trampoline;
    private Requin requin;
    private boolean update, debug, afficheRequin;

    /**
     * Constructeur pour les plateformes,bulles et meduse
     */
    public Jeu() {

        this.update = false;
        this.debug = false;
        this.afficheRequin = false;

        //on crée 5 plateformes car c'est le nombre maximal présent à l'écran
        for (int i = 0; i < 5; i++) {
            newPlateforme = newPlat(hautNouvPlate,lastPlateforme);
            plateformes.add(newPlateforme);
            lastPlateforme = newPlateforme;

            hautNouvPlate -= 100;
        }

        bulles = new Bulles[15];
        bulles = bullesTab();

        meduse = new Meduse(150, 430);

        trampoline = new Trampoline(-100*Math.floor(3*Math.random())-2070); //ajusté pour que la trampoline
                                                                            //soit à mi-chemin entre 2 plateformes
        requin = new Requin(0,-300*Math.random()-1000);
    }

    /**
     *Definir les differentes plateformes et la chance qu'elles soient affichees dans le jeu
     */
    public Plateforme newPlat(double hautNouvPlate, Plateforme lastPlateforme) {

        Random rand = new Random();
        double random = rand.nextDouble();

        if (random < 0.05 && !(lastPlateforme instanceof PlateformeSolide))
            return new PlateformeSolide(hautNouvPlate);

        else if (random < 0.7)
            return new Plateforme(hautNouvPlate);

        else if (random < 0.9)
            return new PlateformeRebondissante(hautNouvPlate);

        else
            return new PlateformeAccelerante(hautNouvPlate);
    }

    /**
     *Faire un paquet de bulles aleatoire pour rendre l'effet plus realistqiue
     */
    public Bulles[] bullesTab() {
        for (int i = 0; i < 3; i++) {
            double baseX = 350*Math.random();

            for (int j = 0; j < 5; j++)
                bulles[5*i + j] = new Bulles(baseX);
        }

        return bulles;
    }

    /**
     * getter pour le score en metre
     */
    public String getHauteurM() {
        return -(int)fenetreY+"m";
    }
    /**
     * Affichage de la position de la meduse(mode debug)
     */
    public String posMeduse() {
        return "("+(int)meduse.getX()+", "+-((int)meduse.getY()-430)+")";
    }
    /**
     * Affichage de la vitesse de la meduse(mode debug)
     */
    public String vitMeduse() {
        return "("+(int)meduse.getVx()+", "+-(int)meduse.getVy()+")";
    }
    /**
     * Affichage de l'acceleration de la meduse (mode debug)
     */
    public String accMeduse() {
        return "("+(int)meduse.getAx()+", "+-(int)meduse.getAy()+")";
    }
    /**
     * Affichage de la meduse si touche ou pas le sol
     */
    public String touche() {
        if (meduse.isParterre())
            return "oui";
        return "non";
    }
    /**
     * Methode sauter de la meduse
     */
    public void jump() {
        meduse.jump();
    }
    /**
     * Methode deplacer a gauche la meduse
     */
    public void runGauche() {
        meduse.runGauche();
    }
    /**
     * Methode deplacer a droite la meduse
     */
    public void runDroite() {
        meduse.runDroite();
    }
    /**
     * Methode pour arrete le deplacememtn de la meduse
     */
    public void stopRunning() {
        meduse.stopRunning();
    }
    /**
     * Methode detectant si la meduse est sur une plateforme pour executer d'autres fonction en consequant
     */
    public boolean isParterre() {
        return meduse.isParterre();
    }
    /**
     * Methode qui reinitialise et detecte l'etat actuelle de la meduse
     */
    public void readyToUpdate() {
        this.update = true;
    }
    /**
     * Methode pour le debuger
     */
    public void debug() {
        this.debug = !this.debug;
    }
    /**
     * Bool pour determiner si on est en mode debug
     */
    public boolean isDebug() {
        return this.debug;
    }
    /**
     * Methode determine la fin du jeu
     */
    public boolean isGameOver() {
        if (meduse.getY() > 480 + fenetreY || (meduse.intersects(requin) && afficheRequin)) {
            this.update = false;
            return true;
        }
        return false;
    }
    /**
     *Methode qui reinitialise une partie
     */
    public void update(double dt) {

        if (this.update) {

            if (!this.debug) {
                vFenetreY += dt * -2;

                if (meduse.isAccelerante())
                    fenetreY += dt * vFenetreY * 3;

                else
                    fenetreY += dt * vFenetreY;
            }

            while (meduse.getY() < 120 + fenetreY)
                fenetreY--;


            // À chaque tour, on recalcule si la méduse se trouve parterre ou non
            meduse.setParterre(false);
            meduse.setAccelerante(false);   //et si elle se trouve sur une plateforme accelerante

            if (plateformes.get(0).getY() - fenetreY > 490) {
                plateformes.remove(0);

                newPlateforme = newPlat(hautNouvPlate,lastPlateforme);
                plateformes.add(newPlateforme);
                lastPlateforme = newPlateforme;

                hautNouvPlate -= 100;
            }

            if (bulles[0].getY() < -300)
                bulles = bullesTab();

            for (Bulles b : bulles)
                b.update(dt);

            if (trampoline.getY() - fenetreY > 490)
                trampoline = new Trampoline(-100*Math.floor(3*Math.random())-2000+trampoline.getY());

            trampoline.update(dt);

            meduse.update(dt);
            meduse.testCollision(trampoline);

            for (Plateforme p : plateformes) {
                p.update(dt);
                // Si la méduse se trouve sur une plateforme, ça sera défini ici
                meduse.testCollision(p);
            }

            //quand le requin est prêt à attaquer
            if (requin.getY() - fenetreY > 490) {
                this.afficheRequin = true;
                requin.setX(meduse.getX());
                requin.setVy(-300);
            }

            //quand le requin dépasse le haut de l'écran
            if (fenetreY - requin.getY() > 160 && this.afficheRequin) {
                this.afficheRequin = false;
                requin = new Requin(0,-300*Math.random()-1000+requin.getY());
            }

            requin.update(dt);
        }
        //Met fin a la partie actuelle
        if (isGameOver()) {
            fenetreY = 0;
            vFenetreY = -50;

            plateformes.clear();
            lastPlateforme = null;
            hautNouvPlate = 380;

            //on crée 5 plateformes car c'est le nombre maximal présent à l'écran
            for (int i = 0; i < 5; i++) {
                newPlateforme = newPlat(hautNouvPlate,lastPlateforme);
                plateformes.add(newPlateforme);
                lastPlateforme = newPlateforme;

                hautNouvPlate -= 100;
            }

            bulles = bullesTab();

            meduse = new Meduse(150, 430);

            trampoline = new Trampoline(-100*Math.floor(3*Math.random())-2070);

            requin = new Requin(0,-300*Math.random()-1000);
        }
    }

    /**
     *Methode draw qui servira a dessiner les elements
     */
    public void draw(GraphicsContext context) {
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        for (Bulles b : bulles) {
            b.draw(context,0,true); //le 3e paramètre n'a pas d'incidence sur la fonction
        }

        trampoline.draw(context,fenetreY,this.debug);
        meduse.draw(context,fenetreY,this.debug);

        for (Plateforme p : plateformes) {
            p.draw(context,fenetreY,this.debug);
       }

        if (this.afficheRequin)
            requin.draw(context,fenetreY,this.debug);
    }
}
