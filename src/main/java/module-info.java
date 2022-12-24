module Main.votingui {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mybatis;
    requires ZKFingerReader;
    requires java.sql;
    opens Main.votingui to javafx.fxml;
    exports Main.votingui;
}