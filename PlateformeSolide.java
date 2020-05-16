//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.paint.Color;

public class PlateformeSolide extends Plateforme {
    /**
     *Redefinir la couleur de la plateforme
     */
    public PlateformeSolide(double y) {

        super(y);
        this.color = Color.rgb(184, 15, 36);
    }
}