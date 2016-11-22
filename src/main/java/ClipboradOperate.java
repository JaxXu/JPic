import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;

import java.io.*;

import java.nio.channels.FileChannel;

import java.util.*;
import java.util.List;

/**
 * Created by Jax on 2016/11/20.
 */
import javax.imageio.ImageIO;

public class ClipboradOperate {
    Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();

    public void fileChannelCopy(File s, File t) {
        FileInputStream  fi  = null;
        FileOutputStream fo  = null;
        FileChannel      in  = null;
        FileChannel      out = null;

        try {
            fi  = new FileInputStream(s);
            fo  = new FileOutputStream(t);
            in  = fi.getChannel();               // 得到对应的文件通道
            out = fo.getChannel();               // 得到对应的文件通道
            in.transferTo(0, in.size(), out);    // 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存剪贴板图片到硬盘
     * @return 返回保存地址，""为不存在或非图片
     */
    public Map<String, String> saveClipboardImage() {
        Object obj = getImageFromClipboard();

        if (obj instanceof Image) {
            Image               image    = (Image) getImageFromClipboard();
            Map<String, String> fileInfo = null;

            if (image != null) {
                fileInfo = saveImage(image);
            }

            return fileInfo;
        } else if(obj instanceof List){
            List<File> files = (List<File>) obj;

            if (files.size() == 1) {
                File   file = files.get(0);
                String str  = file.getName().toLowerCase();

                // BMP、JPG、JPEG、PNG、GIF
                if (str.endsWith(".png")
                        || str.endsWith(".jpg")
                        || str.endsWith(".bmp")
                        || str.endsWith(".jpeg")
                        || str.endsWith(".gif")) {
                    System.out.println(file.getPath());

                    String path     = System.getProperty("user.dir") + File.separator + "images" + File.separator;
                    String fileName = new Date().getTime() + ".png";
                    File   newFile  = new File(path + fileName);

                    fileChannelCopy(file, newFile);

                    Map<String, String> result = new HashMap<String, String>();

                    result.put("path", path + fileName);
                    result.put("fileName", fileName);

                    return result;
                } else {
                    System.out.println("不是图片类型");
                }
            } else {
                System.out.println("不支持多文件");
            }
        }
        return null;
    }

    /**
     * 保存图片到硬盘
     * @param image 需要保存的Image对象
     * @return 保存的文件路径，返回空表示保存失败
     */
    private Map<String, String> saveImage(Image image) {
        System.out.println("saveImage() start..");

        Image         img    = image;
        int           width  = img.getWidth(null);
        int           height = img.getHeight(null);
        BufferedImage bi     = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics      g      = bi.getGraphics();

        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();

        String              path     = System.getProperty("user.dir") + File.separator + "images" + File.separator;
        String              fileName = new Date().getTime() + ".png";
        File                f        = new File(path + fileName);
        Map<String, String> result   = new HashMap<String, String>();

        result.put("path", path + fileName);
        result.put("fileName", fileName);

        try {
            ImageIO.write(bi, "png", f);
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        System.out.println("saveImage() return:" + result.toString());
        System.out.println("saveImage() stop..");

        return result;
    }

    // 往剪切板写文本数据
    public void setClipboardText(String writeMe) {
        Transferable tText = new StringSelection(writeMe);

        sysc.setContents(tText, null);
    }

    /**
     * 得到剪切板信息
     * @return 返回得到的Image对象，null为得不到或不为内容图片
     */
    private Object getImageFromClipboard() {
        System.out.println("getImageFromClipboard() start..");

        Transferable cc = sysc.getContents(null);

        if (cc == null) {
            System.out.println("剪贴板为空");
            System.out.println("getImageFromClipboard() stop..");

            return null;
        } else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            try {
                System.out.println("返回图片");
                System.out.println("getImageFromClipboard() stop..");

                return (Image) cc.getTransferData(DataFlavor.imageFlavor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (cc.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            try {
                return (List<File>) cc.getTransferData(DataFlavor.javaFileListFlavor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("剪切板内容非图片");
        System.out.println("getImageFromClipboard() stop..");

        return null;
    }
}