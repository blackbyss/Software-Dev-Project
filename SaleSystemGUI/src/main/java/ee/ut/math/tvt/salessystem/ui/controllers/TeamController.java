package ee.ut.math.tvt.salessystem.ui.controllers;

import ee.ut.math.tvt.salessystem.dao.SalesSystemDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Encapsulates everything that has to do with the team tab (the tab
 * labelled "Team" in the menu).
 */
public class TeamController implements Initializable {

    @FXML
    private Label teamName;

    @FXML
    private Label teamLeader;

    @FXML
    private Label teamLeaderEmail;

    @FXML
    private Label teamMembers;

    @FXML
    private ImageView imageLoc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String dir = System.getProperty("user.dir");
        dir = dir.replace("SaleSystemGUI", "src/main/resources/application.properties");
        try (InputStream input = new FileInputStream(dir)){
            Properties prop = new Properties();
            prop.load(input);

            teamName.setText(prop.getProperty("teamName"));
            teamLeader.setText(prop.getProperty("teamLeader"));
            teamLeaderEmail.setText(prop.getProperty("teamLeaderEmail"));
            teamMembers.setText(prop.getProperty("teamMembers"));
            imageLoc.setImage(new Image(prop.getProperty("imageLoc")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TeamController() {
    }

}
