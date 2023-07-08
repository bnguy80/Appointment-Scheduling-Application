package schedule.main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Gui-based scheduling desktop application with filtering options to maintain database of customers and appointments
 *
 * JAVADOC located in --- JavaDocs
 *
 * Lambdas found in line 150 and 264 of LoginController
 *
 * @author Brandon Nguyen
 */
public class Main extends Application {

    /**
     *Starts method creates FXML stage and loads scene
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 939, 556);
            stage.setTitle(resourceBundle.getString("Hello"));
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Error Main");
        }
    }

    /**
     * Local of system
     */
    Locale locale = Locale.getDefault();
    ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle.lang", locale);

    /**
     *Main method entry point of application, launches connection and establishes Database connection
     * @param args
     */
    public static void main(String[] args) {

        //ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
        //ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("America")).forEach(System.out::println);

        DBConnection.connect();
        launch();
        DBConnection.closeConnection();


    }
}