module com.jamieleen.movieticketingsystem {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.jamieleen.movieticketingsystem to javafx.fxml;
    exports com.jamieleen.movieticketingsystem;
}