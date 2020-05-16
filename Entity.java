//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Entity {

    protected double largeur, hauteur;
    protected double x, y;//coord(x,y)

    protected double vx, vy;//vitesse de la meduse en coord (x,y)
    protected double ax, ay;//acceleration de la meduse en coord(x,y)

    protected Color color;
    protected boolean yellow;

    /**
     * Met à jour la position et la vitesse de la meduse
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;

        // force la meduse a rester dans les bornes de l'ecran
        if (x + largeur > HighSeaTower.WIDTH || x < 0)
            vx *= -0.5;

        x = Math.min(x, HighSeaTower.WIDTH - largeur);
        x = Math.max(x, 0);
        y = Math.min(y, HighSeaTower.HEIGHT - hauteur);
    }

    public void setYellow(boolean bool) {
        this.yellow = bool;
    }

    public abstract void draw(GraphicsContext context, double fenetreY, boolean debug);

}
