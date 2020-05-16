//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends Entity {

    /**
     *Constructeur pour les plateformes avec dimensions aleatoires
     */
    public Plateforme(double y) {
        this.largeur = 95*Math.random() + 80;
        this.hauteur = 10;
        this.x = (350-this.largeur)*Math.random();
        this.y = y;
        this.yellow = false;
        this.color = Color.rgb(230, 134, 58);
    }

    public double getY() {
        return this.y;
    }
    /**
     *Set la valeur de la meduse si elle est a terre
     */
    public void setYellow(boolean bool) {
        this.yellow = bool;
    }
    /**
     * Redefinir la methode draw
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY, boolean debug) {
        if (debug && this.yellow)
            context.setFill(Color.YELLOW);
        else
            context.setFill(color);

        double yAffiche = y - fenetreY;
        context.fillRect(x, yAffiche, largeur, hauteur);
    }
}
