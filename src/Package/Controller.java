package Package;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class Controller {
@FXML
private Canvas drawinCanvas;
    @FXML
    private ImageView imageView;

    @FXML
    private Button openButton;

    // Call this method from your Main class to pass the stage
    private Stage primaryStage;

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void handleOpenImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        // Filter: only allow image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }

    }

    private double rotation_angle = 0;

    @FXML
    private void rotateImageClockwise() {
        //imageView.setRotate(imageView.getRotate() + 90);
        rotation_angle+=90;
        RotateTransition rotate  = new RotateTransition();
        rotate.setNode(imageView);
        rotate.setInterpolator(Interpolator.EASE_IN);
        rotate.setToAngle(rotation_angle);
        rotate.play();
    }

    @FXML
    private void rotateImageCounterClockwise() {
        //imageView.setRotate(imageView.getRotate() + 90);
        rotation_angle-=90;
        RotateTransition rotate  = new RotateTransition();
        rotate.setNode(imageView);
        rotate.setInterpolator(Interpolator.EASE_IN);
        rotate.setToAngle(rotation_angle);
        rotate.play();
    }

    @FXML
    public void initialize() {
        imageView.setOnScroll(event -> {
            if (event.isControlDown()) {  // Only zoom if Ctrl is held
                double deltaY = event.getDeltaY();
                if (deltaY > 0) {
                    zoomIn();
                } else {
                    zoomOut();
                }
                imageView.setScaleX(zoomFactor);
                imageView.setScaleY(zoomFactor);
                event.consume(); // Prevent default scroll behavior
            }
        });
    }

    @FXML
    public void rotate_shortcuts() {
        if (primaryStage != null && primaryStage.getScene() != null) {
            primaryStage.getScene().setOnKeyPressed(event -> {
                if (event.isControlDown() && event.getCode() == KeyCode.R && !event.isShiftDown()) {
                    rotateImageClockwise();
                }
                if (event.isControlDown() && event.getCode() == KeyCode.R && event.isShiftDown()) {
                    rotateImageCounterClockwise();
                }
                event.consume();
            });
        }
    }


    private double zoomFactor = 1.0;
    private final double zoomStep = 0.1;
    private final double maxZoom = 3.0;
    private final double minZoom = 0.5;

    @FXML
    private void zoomIn() {
        if (zoomFactor < maxZoom) {
            zoomFactor += zoomStep;
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);
        }
    }

    @FXML
    private void zoomOut() {
        if (zoomFactor > minZoom) {
            zoomFactor -= zoomStep;
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);
        }
    }
/*
    @FXML
    private void saveImage() {
        Node drawingCanvas ;
        WritableImage snapshot = new WritableImage(
                //(int) drawingCanvas.getWidth(),
               // (int) drawingCanvas.getHeight()
        );

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        Group group = new Group();
        group.getChildren().addAll(imageView, drawingCanvas);
        group.snapshot(params, snapshot);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image", "*.png")
        );
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
                ImageIO.write(bufferedImage, "png", file);
                System.out.println("Image saved: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

}

