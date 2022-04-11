module com.thombreugelmans {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.thombreugelmans to javafx.fxml;
    exports com.thombreugelmans;
}
