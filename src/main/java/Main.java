import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.print(getClass().getResource("/"));
        Parent root         = FXMLLoader.load(getClass().getResource("main.fxml"));
        TextField ak        = (TextField)root.lookup("#ak");
        TextField sk        = (TextField)root.lookup("#sk");
        TextField spaceName = (TextField)root.lookup("#spaceName");
        TextField qiNiuUrl  = (TextField)root.lookup("#qiNiuUrl");
        TextField preFix    = (TextField)root.lookup("#preFix");
        initConfig(ak,sk,spaceName,qiNiuUrl,preFix);
        primaryStage.setTitle("JPicTool");
        primaryStage.setScene(new Scene(root, 380, 275));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void initConfig(TextField ak, TextField sk, TextField spaceName, TextField qiNiuUrl, TextField preFix) {
        Properties pro        = getProperties();
        Controller.AK         = pro.getProperty("AK");
        Controller.SK         = pro.getProperty("SK");
        Controller.SPACE_NAME = pro.getProperty("spaceName");
        Controller.QI_NIU_URL = pro.getProperty("qiNiuUrl");
        Controller.PRE_FIX    = pro.getProperty("preFix");
        ak.setText(Controller.AK);
        sk.setText(Controller.SK);
        spaceName.setText(Controller.SPACE_NAME);
        qiNiuUrl.setText(Controller.QI_NIU_URL);
        preFix.setText(Controller.PRE_FIX);
    }

    private Properties getProperties() {
        Properties pro = new Properties();
        try {
            FileInputStream in = new FileInputStream("config.ini");
            pro.load(in);
            in.close();
        } catch (Exception e) {}
        return pro;
    }
}