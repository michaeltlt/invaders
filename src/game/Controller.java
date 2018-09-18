package game;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private Ship ship;

//    @FXML private void drawCanvas(ActionEvent event) {
//        gc.setFill(Color.AQUA);
//        gc.fillRect(10,10,100,100);
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
//        gc.setFill(Color.BLACK);
//        System.out.println("color set to black");
//        gc.fillRect(50, 50, 100, 100);
//        System.out.println("draw rectangle");
//        gc.setFill( Color.RED );
//        gc.setStroke( Color.BLACK );
//        gc.setLineWidth(2);
//        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
//        gc.setFont( theFont );
//        gc.fillText( "Hello, World!", 60, 50 );
//        gc.strokeText( "Hello, World!", 60, 50 );

//        System.out.println(getClass().getResource("../../assets/background.png"));
//        Image background = new Image("file:assets/background.png");
//        gc.drawImage(background,0, 0);

    }
}
