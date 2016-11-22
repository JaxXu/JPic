import java.util.Map;

public class Util implements Runnable{
    boolean flags = false;
    public void run() {
        while(flags){
            ClipboradOperate    ci       = new ClipboradOperate();
            Map<String, String> fileInfo = ci.saveClipboardImage();

            if (fileInfo != null) {
                try {
                    QiNiuUpload qiNiuUpload = new QiNiuUpload(Controller.AK, Controller.SK, Controller.SPACE_NAME);

                    qiNiuUpload.upload(fileInfo.get("fileName"), fileInfo.get("path"), Controller.PRE_FIX);
                } catch (Exception e) {}
                ci.setClipboardText("![mark](" + Controller.QI_NIU_URL + "/" + Controller.PRE_FIX + "/" + fileInfo.get("fileName") + ")");
                System.out.println("复制咯~~~~~~~~~~");
            }
        }
    }

    public void start(){
        flags = true;
        new Thread(this).start();
    }
    public void stop(){
        flags = false;
    }
}