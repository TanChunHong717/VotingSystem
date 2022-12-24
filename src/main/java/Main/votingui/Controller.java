package Main.votingui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    /*****************************************************************************************************************
     *                                              login page
     ***************************************************************************************************************** **/
    @FXML
    private TextField nameField;
    @FXML
    private TextField ICField;

    public String getNameField() {
        return this.nameField.getText();
    }

    public int getICNumber() {
        return Integer.parseInt(this.ICField.getText());
    }

    public void switchToHomePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

    }

    /*****************************************************************************************************************
     home page
     ***************************************************************************************************************** **/
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ICTextField;
    @FXML
    private TextField parliamentTextField;
    @FXML
    private TextField dunTextField;
    @FXML
    private TextField turnNumberTextField;

    public void switchToVotingPage(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("voting.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

    }

    /*****************************************************************************************************************
     *                                              voting page
     ***************************************************************************************************************** **/

    @FXML
    private ImageView parliamentCandidate1;
    @FXML
    private ImageView parliamentCandidate2;
    @FXML
    private ImageView parliamentCandidate3;
    @FXML
    private ImageView dunCandidate1;
    @FXML
    private ImageView dunCandidate2;
    @FXML
    private ImageView dunCandidate3;
    @FXML
    public static Label usernameTop;
    @FXML
    private Button submitButton;
    @FXML
    private AnchorPane mAnchorPane;

    private void promptName() {

    }


    public void changePartyImageOnHover(MouseEvent event) {

        if (parliamentCandidate1.isHover()) {
            parliamentCandidate1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/eagle.png"))));

        } else if (parliamentCandidate2.isHover()) {
            parliamentCandidate2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/horse.png"))));

        } else if (parliamentCandidate3.isHover()) {
            parliamentCandidate3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/peacock.png"))));

        } else if (dunCandidate1.isHover()) {
            dunCandidate1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/eagle.png"))));

        } else if (dunCandidate2.isHover()) {
            dunCandidate2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/horse.png"))));

        } else if (dunCandidate3.isHover()) {
            dunCandidate3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/peacock.png"))));

        }
    }

    public void changeToCandidateImage(MouseEvent event) {
        if (!parliamentCandidate1.isHover()) {
            parliamentCandidate1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/c1.png"))));

        }
        if (!parliamentCandidate2.isHover()) {
            parliamentCandidate2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/c2.png"))));

        }
        if (!parliamentCandidate3.isHover()) {
            parliamentCandidate3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/c3.png"))));

        }
        if (!dunCandidate1.isHover()) {
            dunCandidate1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/c1.png"))));

        }
        if (!dunCandidate2.isHover()) {
            dunCandidate2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/c2.png"))));

        }
        if (!dunCandidate3.isHover()) {
            dunCandidate3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/c3.png"))));

        }

    }


    public void switchToFingerprintPage(ActionEvent event) throws IOException {

        //popop message
        Stage stage = (Stage) mAnchorPane.getScene().getWindow();
        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setHeaderText("Are you sure you want to submit your vote? Click OK");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scanFinger.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } else if (result.get() == ButtonType.CANCEL) {
            System.out.println("cancel button pressed");
        }


    }
    /*****************************************************************************************************************
     *                                              fingerprint page
     ***************************************************************************************************************** **/

    Image imageFingerprint = new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/fingerprint.png")));
    Image imageAccept = new Image("C:\\Users\\asus gaming\\OneDrive\\Desktop\\Pr\\votingUI\\src\\main\\resources\\com\\example\\votingui\\image\\accept.png");
    @FXML
    private ImageView fingerprintImageView;

    @FXML
    private void changeFingerprintImageOnClick(MouseEvent event) {


        fingerprintImageView.setOnMouseClicked(event1 -> {
            try {
                fingerprintImageView.setImage(imageAccept);
            } catch (Exception e) {
                System.out.println("not changing");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("closing.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

    }

    private void changeImage(MouseEvent event) {
        fingerprintImageView.setImage(imageAccept);
    }




}