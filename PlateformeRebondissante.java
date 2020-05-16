//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)
import javafx.scene.paint.Color;

public class PlateformeRebondissante extends Plateforme {
    /**
     *Redefinir la couleur de la plateforme
     */
    public PlateformeRebondissante(double y) {

        super(y);
        this.color = Color.LIGHTGREEN;
    }
}