//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Trampoline extends Entity {

    private Image image;
    /**
     *Constructeur Trampoline
     */
    public Trampoline(double y) {
        this.x = 300*Math.random();
        this.y = y;
        this.largeur = 50;
        this.hauteur = 25;
        this.yellow = false;
        image = new Image ("images/trampoline.png");
    }

    public double getY() {
        return this.y;
    }
    /**
     * Redefinir la methode draw
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY, boolean debug) {
        if (debug && this.yellow)
            context.setFill(Color.YELLOW);

        double yAffiche = y - fenetreY;
        context.drawImage(image, x, yAffiche, largeur, hauteur);
    }
}