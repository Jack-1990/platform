package cn.seocoo.platform.common.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "png";
    // 二维码尺寸
    private static int QRCODE_SIZE = 300;
    // LOGO宽度
    private static int WIDTH = 80;
    // LOGO高度
    private static int HEIGHT = 80;

    public static BufferedImage createImage(String content, String imgPath,
            boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

   
    public static void insertImage(BufferedImage source, String imgPath,
            boolean needCompress) throws Exception {
        URL url = null; 
        HttpURLConnection httpUrl = null;  
        try {
             url = new URL(imgPath);  
             httpUrl = (HttpURLConnection) url.openConnection();  
        } catch (Exception e) {
            System.out.println(""+imgPath+"打开logo连接失败");
        }
        Image src = ImageIO.read(httpUrl.getInputStream());
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 1, 1, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
//        Shape shape = new RoundRectangle2D.Float(x, y, width, height, 6, 6);//光圈去掉
        graph.setStroke(new BasicStroke(3f));
//        graph.draw(shape);
        graph.dispose();
    }

   /**
    * 
    * @param content  url
    * @param imgPath  logo图片的路径
    * @param userid  用户id
    * @param map  二维码大小参数
    * @param needCompress  是否压缩
    * @return
    * @throws Exception
    */
    public static String  encode(String content, String imgPath,Map<String,Integer> map,boolean needCompress) throws Exception {

        String path = "http://115.28.94.228/resources/manage";

        String uploadPath = "E:\\";
        
        String fileName = "qrCode" + ".png";
        
        File file = new File(uploadPath + fileName);
        
        if (file.exists()) {
            return path  + fileName;
        }
        
        if (!map.isEmpty()){
            if (map.containsKey("size")){
                QRCODE_SIZE = map.get("size");
            }
            if (map.containsKey("width")){
                WIDTH = map.get("width");
            }
            if (map.containsKey("size")){
                HEIGHT = map.get("height");
            }
        }
        try {
           BufferedImage image = QRCodeUtil.createImage(content, imgPath,needCompress);
           mkdirs(uploadPath);
           ImageIO.write(image, FORMAT_NAME, new File(""+file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
        return path  + fileName;
    }

   
    public static void mkdirs(String destPath) {
        File file =new File(destPath);   
        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }
   
    public static void encode(String content, String imgPath,
            OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

   
    public static void encode(String content, OutputStream output)
            throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }

   
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

   
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
    }
   
    
    /**
     * 直接调用，生成图片二维码，带logo
     * @param content
     * @param logoPath
     * @param imgSavePath
     * @param needCompress
     * @throws Exception
     */
    public static void createQrcode(String content,String logoPath, String imgSavePath,boolean needCompress) throws Exception{
        BufferedImage image = QRCodeUtil.createImage(content,logoPath,needCompress);
        QRCodeUtil.mkdirs(imgSavePath); 
        ImageIO.write(image, FORMAT_NAME, new File(imgSavePath));
}
   
    public static void main(String[] args) throws Exception {
        String text = "http://www.baidu.com";
        BufferedImage image = QRCodeUtil.createImage(text,"http://115.28.94.228/resources/manage/images/paylogo.png",true);
        //QRCodeUtil.mkdirs("E://code/paychannl/code/resources/manage/images/payQrcode");
        ImageIO.write(image, FORMAT_NAME, new File("E://code/paychannl/code/resources/manage/images/payQrcode"));
        //QRCodeUtil.encode(text, "d:/MyWorkDoc/my180.jpg", "d:/MyWorkDoc", true);
    }
}