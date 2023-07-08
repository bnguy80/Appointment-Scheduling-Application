package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Appointments;
import schedule.main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates the Main Menu Controller
 */
public class MainMenuController implements Initializable {

    /**
     * Load Customer-view, CustomersController
     * @param actionEvent Customer screen action
     * @throws IOException for FXMLLoader
     */
    public void onActionCustomer(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("Customer-view.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Load Appointment-view, AppointmentsController
     * @param actionEvent Appointment screen action
     * @throws IOException for FXMLLoader
     */
    public void onActionAppointment(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("Appointment-view.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Load Reports-view, ReportsController
     * @param actionEvent Report screen action
     * @throws IOException for FXMLLoader
     */
    public void onActionReports(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource("Reports-view.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Load Login-view, LoginController
     * @param actionEvent cancel view MainMenu-view action
     * @throws IOException for FXMLLoader
     */
    public void onActionLogOut(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login-view.fxml"));
        //FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getClassLoader()).getResource(""));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     *Display confirm dialog
     * @param header confirm dialog header
     * @param content confirm dialog content
     * @return return true if button press ok, return false if button press to confirm not cancel
     */
    public static boolean confirmDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Display error dialog
     * @param header error dialog header
     * @param content error dialog content
     * @return return true if button press ok, return true if button press cancel
     */
    public static boolean errorDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error!");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
