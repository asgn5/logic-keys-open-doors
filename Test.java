
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

    public class Test extends Application {

        private StackPane root;
        private Box b;
        public static void main(String[] args) {
            Application.launch(args);
        }
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("");
            root = new StackPane();
            Scene scene = new Scene(root, 300, 250, Color.WHITE);
            testBox();
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public String test(String str) {
            return "\u001B[33m"+"TEST: "+ str + "\u001B[0m";
        }

        private void testBox() {
            b = new Box("A", "1", 0, 0, Box.fillW);
            System.out.println(test("CURRENTCOLOR --> NEXTCOLOR"));
            System.out.printf("%-10s \t-->\t %10s\n", b.current(),b.getColorName(b.nextImage()));
            System.out.println(test("CURRENTCOLOR --> SETCOLOR"));
            System.out.printf("%-10s \t-->\t %10s\n", b.current(),b.getColorName(b.setImage(Box.fillX)));
            System.out.println(test("CONTENTS OF STACK"));
            System.out.println(b.getStack());
            System.out.println(test("REMOVE COLOR"));
            System.out.printf("%-10s \t-->\t %10s\n", b.current(),b.getColorName(b.removeImage()));
            System.out.printf("%-10s \t-->\t %10s\n", b.current(),b.getColorName(b.removeImage()));
            System.out.println(test("CONTENTS OF STACK"));
            System.out.println(b.getStack());
            System.out.println(test("NEXTCOLOR"));
            System.out.printf("%-10s \t>>>\t %10s\n", b.current(),b.getColorName(b.nextImage()));
            System.out.printf("%-10s \t>>>\t %10s\n", b.current(),b.getColorName(b.nextImage()));
            System.out.printf("%-10s \t>>>\t %10s\n", b.current(),b.getColorName(b.nextImage()));
            System.out.printf("%-10s \t>>>\t %10s\n", b.current(),b.getColorName(b.nextImage()));
            b.setOnMouseClicked(event);
            root.getChildren().add(b);
        }

        private EventHandler<MouseEvent> event = mouseEvent -> {
            if (b.getFill().equals(Box.fillW))
                b.nextImage();
            else if (b.getFill().equals(Box.fillX))
                b.nextImage();
            else if (b.getFill().equals(Box.fillC))
                b.clearImage();
            System.out.println(b.current());
        };
    }

