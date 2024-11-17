module com.cse213.fproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cse213.fproject to javafx.fxml;
    exports com.cse213.fproject;
}