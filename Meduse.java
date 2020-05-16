//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Meduse extends Entity {

    private Image[] framesDroite, framesGauche,frames;
    private Image image;
    private double frameRate = 8; // 8 frames par seconde
    private double tempsTotal = 0;

    private boolean parterre, accelerante;

    /**
     *Constructeur Meduse
     */
    public Meduse(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = 50;
        this.hauteur = 50;
        this.ay = 1200;
        this.parterre = true;

        // Chargement des images de la meduse
        framesDroite = new Image[]{
                new Image("images/jellyfish1.png"),
                new Image("images/jellyfish2.png"),
                new Image("images/jellyfish3.png"),
                new Image("images/jellyfish4.png"),
                new Image("images/jellyfish5.png"),
                new Image("images/jellyfish6.png")
        };
        framesGauche = new Image[]{
                new Image("images/jellyfish1g.png"),
                new Image("images/jellyfish2g.png"),
                new Image("images/jellyfish3g.png"),
                new Image("images/jellyfish4g.png"),
                new Image("images/jellyfish5g.png"),
                new Image("images/jellyfish6g.png")
        };
        image = framesDroite[0];
        frames = framesDroite;
    }

    public double getY() {
        return this.y;
    }

    public double getX() {
        return this.x;
    }

    public double getVx() {
        return this.vx;
    }

    public double getVy() {
        return this.vy;
    }

    public double getAx() {
        return this.ax;
    }

    public double getAy() {
        return this.ay;
    }

    /**
     *Mettre a jour l'image affiche(frame/sec)
     */
    @Override
    public void update(double dt) {
        // Physique du personnage
        super.update(dt);

        // Mise à jour de l'image affichée
        tempsTotal += dt;
        int frame = (int) (tempsTotal * frameRate);

        image = frames[frame % frames.length];
    }

    /**
     *Methode qui change les caracteristiques de la meduse par rapport a la plateforme en collision
     */
    public void testCollision(Entity other) {

        if (intersects(other) && Math.abs(this.y + hauteur - other.y) < 10 && vy > 0) {
            pushHaut(other);
            other.setYellow(true);
            this.parterre = true;

            if (other instanceof PlateformeRebondissante) //Caracteristique pour plateforme rebondissante
                this.vy = Math.min(-100,-1.5*this.vy);

            else if (other instanceof Trampoline)
                this.vy = -1500;

            else {
                this.vy = 0;

                if (other instanceof PlateformeAccelerante)//Plateforme qui accelere le defilement de l'ecran
                    this.accelerante = true;
            }
        }

        else if (other instanceof PlateformeSolide && intersects(other) &&
                Math.abs(other.y + other.hauteur - this.y) < 10 && vy < 0) {
            pushBas(other);
            other.setYellow(true);
            this.vy *= -1;
        }

        else if (this.y == HighSeaTower.HEIGHT - hauteur)
            this.parterre = true;

        else
            other.setYellow(false);
    }

    /**
     * Bool: si Un des carrés est à gauche de l’autre ou
     *Un des carrés est en haut de l’autre
     */
    public boolean intersects(Entity other) {
        return !(x + largeur < other.x || other.x + other.largeur < this.x || y + hauteur < other.y
                || other.y + other.hauteur < this.y);
    }

    /**
     * Repousse la méduse vers le haut (sans déplacer la
     * plateforme)
     */
    public void pushHaut(Entity other) {
        double deltaY = this.y + this.hauteur - other.y;
        this.y -= deltaY;
    }

    public void pushBas (Entity other) {
        double deltaY = other.y + other.hauteur - this.y;
        this.y -= deltaY;
    }

    /**
     *Bool qui retourne true si la meduse touche le sol
     */
    public boolean isParterre() {
        return this.parterre;
    }

    /**
     *Set la valeur de la meduse si elle est a terre
     */
    public void setParterre(boolean parterre) {
        this.parterre = parterre;
    }

    /**
     *Bool qui retourne true si la meduse a une acceleration
     */
    public boolean isAccelerante() {
        return this.accelerante;
    }

    /**
     *Set la valeur d'acceleration de la meduse
     */
    public void setAccelerante(boolean bool) {
        this.accelerante = bool;
    }

    /**
     * la méduse peut seulement sauter si elle se trouve sur une
     * plateforme
     */
    public void jump() {
        vy = -600;
    }

    /**
     * Acceleration de la meduse vers la gauche
     */
    public void runGauche() {
        ax = -1200;
        frames = framesGauche;
    }

    /**
     * Acceleration de la meduse vers la droite
     */
    public void runDroite() {
        ax = 1200;
        frames = framesDroite;
    }

    /**
     * Acceleration de la meduse initier a 0 pour l'arreter
     */
    public void stopRunning() {
        ax = 0;
    }

    /**
     * Redefinir la methode draw
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY, boolean debug) {
        double yAffiche = y - fenetreY;
        context.drawImage(image, x, yAffiche, largeur, hauteur);

        if (debug) {
            context.setFill(Color.rgb(255,0,0,0.4));
            context.fillRect(x, yAffiche, largeur, hauteur);
        }
    }
}