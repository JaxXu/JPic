import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Controller {

    public static String AK;
    public static String SK;
    public static String SPACE_NAME;
    public static String QI_NIU_URL;
    public static String PRE_FIX;

    @FXML
    private TextField ak;
    @FXML
    private TextField sk;
    @FXML
    private TextField spaceName;
    @FXML
    private TextField qiNiuUrl;
    @FXML
    private TextField preFix;
    @FXML
    private Button run;

    private Util util = new Util();

    public void start(){
        if (run.getText().equals("Start")){
            util.start();
            run.setText("Stop");
        }else {
            util.stop();
            run.setText("Start");
        }
    }
    public void saveConfig(){
        Properties pro = getProperties();
        pro.setProperty("AK",ak.getText());
        pro.setProperty("SK",sk.getText());
        pro.setProperty("spaceName",spaceName.getText());
        pro.setProperty("qiNiuUrl", qiNiuUrl.getText());
        pro.setProperty("preFix",preFix.getText());
        try {
            FileOutputStream out = new FileOutputStream("config.ini");
            pro.store(out,"");
            out.close();
        } catch (Exception e){}
        AK         = pro.getProperty("AK");
        SK         = pro.getProperty("SK");
        SPACE_NAME = pro.getProperty("spaceName");
        QI_NIU_URL = pro.getProperty("qiNiuUrl");
        PRE_FIX = pro.getProperty("preFix");
    }

    public void reSet() {
        Properties pro = getProperties();
        AK         = pro.getProperty("AK");
        SK         = pro.getProperty("SK");
        SPACE_NAME = pro.getProperty("spaceName");
        QI_NIU_URL = pro.getProperty("qiNiuUrl");
        PRE_FIX = pro.getProperty("preFix");
        ak.setText(AK);
        sk.setText(SK);
        spaceName.setText(SPACE_NAME);
        qiNiuUrl.setText(QI_NIU_URL);
        preFix.setText(PRE_FIX);
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