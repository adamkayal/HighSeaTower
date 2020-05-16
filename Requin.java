//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Requin extends Entity {

    private Image image;
    /**
     *Constructeur Trampoline
     */
    public Requin(double x, double y) {
        this.x = x;
        this.y = y;
        this.vy = 0;
        this.largeur = 60;
        this.hauteur = 120;
        this.yellow = false;
        image = new Image ("images/requin.png");
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }
    /**
     * Redefinir la methode draw
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY, boolean debug) {
        if (debug && this.yellow)
            context.setFill(Color.YELLOW);

        double yAffiche = y - fenetreY;
        context.drawImage(image, x, yAffiche, largeur+20, hauteur+40); //pour que l'image soit plus grande, mais
    }                             //que le jeu ne s'arrête pas si la méduse touche l'aileron dorsal ou le bas du requin
}