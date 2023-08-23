module schedule.schedule_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens schedule.main to javafx.fxml;
    exports schedule.main;
    opens controller to javafx.fxml;
    exports controller;
    opens model to javafx.fxml;
    exports model;
    /*opens DAO to javafx.fxml;
    exports DAO;*/


}