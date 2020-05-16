//Travail fait par Joseph El-Sayegh (20110482) et Adam Kayal (20071224)

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

public class HighSeaTower extends Application {

    public static final int WIDTH = 350, HEIGHT = 480;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * configuer l'affichage de la fenetre du jeu
     */
    @Override
    public void start(Stage primaryStage) {

        Controleur controleur = new Controleur();

        primaryStage.setTitle("High Sea Tower"); //saisir le titre de la fenetre
        primaryStage.getIcons().add(new Image("images/jellyfish1.png"));   //afficher le logo de l'application
        primaryStage.setResizable(false);   //taille de la fenetre ne peut pas etre redimensionne
        primaryStage.sizeToScene();

        StackPane root = new StackPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        Text hauteur = new Text("0m");
        Text debug = new Text();

        StackPane.setAlignment(hauteur,Pos.TOP_CENTER);
        StackPane.setAlignment(debug,Pos.TOP_LEFT);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().addAll(canvas,hauteur,debug);

        hauteur.setFont(Font.font(28));
        hauteur.setFill(Color.WHITE);
        debug.setFont(Font.font(14));
        debug.setFill(Color.WHITE);

        GraphicsContext context = canvas.getGraphicsContext2D();


        //Animation du timer(Score du jeu:combien de metre parcourus)

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;

                controleur.update(deltaTime);
                controleur.draw(context);

                lastTime = now;

                hauteur.setText(controleur.getHauteurM());


                 //Mode du debuger

                  if (controleur.isDebug()) {
                    debug.setText("Position = "+controleur.posMeduse()+"\n v = "+controleur.vitMeduse()
                            +"\n a = "+controleur.accMeduse()+"\n Touche le sol: "+controleur.touche());
                    debug.setVisible(true);
                }

                else
                    debug.setVisible(false);
            }
        };


        // Associe les touches du clavier lorsqu'on clique sur la touche

        scene.setOnKeyPressed((value) -> {
            if ((value.getCode() == KeyCode.SPACE || value.getCode() == KeyCode.UP) && controleur.isParterre()) {
                controleur.readyToUpdate();
                controleur.jump();  //pour faire sauter la meduse
            }

            if (value.getCode() == KeyCode.LEFT) {  //deplacer la meduse vers la gauche
                controleur.readyToUpdate();
                controleur.runGauche();
            }

            if (value.getCode() == KeyCode.RIGHT) { //deplacer la meduser vers la droite
                controleur.readyToUpdate();
                controleur.runDroite();
            }

            if (value.getCode() == KeyCode.ESCAPE)  //pour fermer l'application
                Platform.exit();

            if (value.getCode() == KeyCode.T) { //pour run le debug mode
                controleur.debug();
            }
        });


         //Associe les touches du clavier lorsqu'on relache la touche

        scene.setOnKeyReleased((value) -> {
            if (value.getCode() == KeyCode.LEFT || value.getCode() == KeyCode.RIGHT) {
                controleur.stopRunning();   //arrete le deplacement de la meduse
            }
        });

        timer.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}