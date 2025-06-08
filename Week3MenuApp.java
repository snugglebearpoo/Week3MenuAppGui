// NOTE: To run this JavaFX app in VS Code, ensure your launch.json includes:
// "vmArgs": "--module-path C:/javafx-sdk-24.0.1/lib --add-modules javafx.controls,javafx.fxml"

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

public class Week3MenuApp extends Application {

    private TextArea textArea;
    private BorderPane root;

    // Required to launch the JavaFX application from VS Code
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create root pane and text area
        root = new BorderPane();
        textArea = new TextArea();
        textArea.setFont(Font.font("Consolas", 14));
        root.setCenter(textArea);

        // Create menu bar and options
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");

        MenuItem showDateTime = new MenuItem("Show Date & Time");
        MenuItem writeToFile = new MenuItem("Write to File");
        MenuItem changeBackground = new MenuItem("Random Green Background");
        MenuItem exitApp = new MenuItem("Exit");

        showDateTime.setOnAction(e -> {
            String dateTime = LocalDateTime.now().toString();
            textArea.appendText("Current Date & Time: " + dateTime + "\n");
        });

        writeToFile.setOnAction(e -> {
            try (FileWriter writer = new FileWriter("log.txt", true)) {
                writer.write(textArea.getText());
                textArea.appendText("Contents written to log.txt\n");
            } catch (IOException ex) {
                textArea.appendText("Error writing to file.\n");
            }
        });

        changeBackground.setOnAction(e -> {
            Color randomGreen = getRandomGreenHue();
            BackgroundFill fill = new BackgroundFill(randomGreen, CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(fill);
            root.setBackground(background);
            textArea.appendText("Background changed to: " + toHexString(randomGreen) + "\n");
        });

        exitApp.setOnAction(e -> primaryStage.close());

        menu.getItems().addAll(showDateTime, writeToFile, changeBackground, exitApp);
        menuBar.getMenus().add(menu);
        root.setTop(menuBar);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Week 3 Menu Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Generate a random green hue
    private Color getRandomGreenHue() {
        Random rand = new Random();
        int green = 128 + rand.nextInt(128);
        int red = rand.nextInt(100);
        int blue = rand.nextInt(100);
        return Color.rgb(red, green, blue);
    }

    // Convert Color to HEX string
    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
    }
}
