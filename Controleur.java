//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;

/**
 * Class qui indique au programme quelle methode utilisee au bon moment
 */
public class Controleur {

    static Jeu jeu;

    public Controleur() {//commencer nouvelle partie
        jeu = new Jeu();
    }

    void draw(GraphicsContext context) {//dessine les elements voulus en appelant la methode "draw"
        jeu.draw(context);
    }

    void update(double deltaTime) {//mettre a jour le temps du jeu
        jeu.update(deltaTime);
    }

    void jump() {//fait sauter la meduse
        jeu.jump();
    }

    void runGauche() {//fait deplacer la meduse vers la gauche
        jeu.runGauche();
    }

    void runDroite() {//fait deplacer la meduse vers la droite
        jeu.runDroite();
    }

    void stopRunning() {
        jeu.stopRunning();//fige la meduse
    }

    boolean isParterre() {
        return jeu.isParterre();//definit si la meduse touche le sol
    }

    void readyToUpdate() {
        jeu.readyToUpdate(); //met a jour l'etat actuelle de la meduse
    }

    String getHauteurM() {
        return jeu.getHauteurM();//hauteur de la meduse
    }

    void debug() {
        jeu.debug();//mode debug du jeu
    }

    Boolean isDebug() {
        return jeu.isDebug();//debug on/off
    }

    String posMeduse() {
        return jeu.posMeduse();//determine la position de la meduse(mode debug)
    }

    String vitMeduse() {
        return jeu.vitMeduse();//determine la vitesse de la meduse(mode debug)
    }

    String accMeduse() {
        return jeu.accMeduse();//l'acceleration de la meduse(mode debug)
    }
    String touche() {
        return jeu.touche();//meduse touche a terre(mode debug)
    }
}