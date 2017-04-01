package cn.seocoo.platform.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
  
  
public class WordsToImageUtils {  
  
    private Font font = new Font("黑体", Font.BOLD, 140);// 添加字体的属性设置  
  
    private Graphics2D g = null;  
    private Graphics2D t = null;
    private int fontsize = 0;  
  
    private int x = 0;  
  
    private int y = 0;  
    private static final Logger logger = Logger.getLogger(WordsToImageUtils.class);
  
    /** 
     * 导入本地图片到缓冲区 
     */  
    public BufferedImage loadImageLocal(String imgName) {  
        try {  
            return ImageIO.read(new File(imgName));  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        return null;  
    }  
  
    /** 
     * 导入网络图片到缓冲区 
     */  
    public BufferedImage loadImageUrl(String imgName) {  
        try {  
            URL url = new URL(imgName);  
            return ImageIO.read(url);  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        return null;  
    }  
  
    /** 
     * 生成新图片到本地 
     */  
    public void writeImageLocal(String newImage, BufferedImage img) {  
        if (newImage != null && img != null) {  
            try {  
                File outputfile = new File(newImage);  
                ImageIO.write(img, "jpg", outputfile);  
            } catch (IOException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
    }  
  
    /** 
     * 设定文字的字体等 
     */  
    public void setFont(String fontStyle, int fontSize) {  
        this.fontsize = fontSize;  
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);  
    }  
  
    /** 
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本） 
     */  
    public BufferedImage modifyImage(BufferedImage img, Object content, int x,int y) {  
        try {  
            int w = img.getWidth();  
            int h = img.getHeight();  
            g = img.createGraphics();  
            g.setBackground(Color.WHITE);  
            g.setColor(Color.WHITE);//设置字体颜色  
            if (this.font != null)  
                g.setFont(this.font);  
            // 验证输出位置的纵坐标和横坐标  
            if (x >= h || y >= w) {  
                this.x = h - this.fontsize + 2;  
                this.y = w;  
            } else {  
                this.x = x;  
                this.y = y;  
            }  
            logger.info("#############log for modifyImage#############"+content);
            if (content != null) {
            	int strWidth = g.getFontMetrics().stringWidth(content.toString());
                g.drawString(content.toString(), this.x+w/2-strWidth/2, this.y);  
            }  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
        return img;  
    }  
  
    /** 
     * 修改图片,返回修改后的图片缓冲区（最多输出俩行文本，一行最多五个字） 
     */  
    public BufferedImage modifyImages(BufferedImage img, Object content, int x,int y) {  
        try {  
            int w = img.getWidth();  
            int h = img.getHeight();  
            g = img.createGraphics();  
//            g.setBackground(Color.WHITE);  
            g.setColor(Color.WHITE);//设置字体颜色  
      
            // 验证输出位置的纵坐标和横坐标  
            if (x >= h || y >= w) {  
                this.x = h - this.fontsize + 2;  
                this.y = w;  
            } else {  
                this.x = x;  
                this.y = y;  
            }  
            logger.info("#############log for modifyImage#############"+content);
            int len = (content.toString()).length();
            if(len<=5){
            	//一行最多五个字
	            if(len<=3){
	            	 g.setFont(new Font("黑体",Font.PLAIN,600)); //字体、字型、字号  600
	            }else if(len==4||len==5){
	            	 g.setFont(new Font("黑体",Font.PLAIN,370)); //字体、字型、字号  
	            }
	            if (content != null) {
	             	int strWidth = g.getFontMetrics().stringWidth(content.toString());
	             	//桌号测试机上乱码
	             	 String contents=content.toString();
//	             	 new String(contents.getBytes("utf8"),"gbk");
	                 g.drawString(contents, this.x+w/2-strWidth/2, this.y); 
	            }
	            g.dispose();  
            }else{
            	//超过五个字的，分两行打印
            	 g.setFont(new Font("黑体",Font.PLAIN,370)); //字体、字型、字号  
            	 String tableName=content.toString().substring(0,5);
            	 String tableName2=content.toString().substring(5, len);
//            	 new String(tableName.getBytes("utf8"),"gbk");
//            	 new String(tableName2.getBytes("utf8"),"gbk");
            	 if(tableName!=null){
            	 int strWidth = g.getFontMetrics().stringWidth(tableName);
	             g.drawString(tableName, this.x+w/2-strWidth/2, this.y/2+100); 
            	   }
            	 g.dispose();
	             //第二次打印开始
            	t = img.createGraphics();  
//                t.setBackground(Color.WHITE);  
                t.setColor(Color.WHITE);//设置字体颜色  
                t.setFont(new Font("黑体",Font.PLAIN,370)); //字体、字型、字号
                if (tableName2 != null) {
                	int strWidthT = t.getFontMetrics().stringWidth(tableName2);
                    t.drawString(tableName2, this.x+w/2-strWidthT/2, this.y+200);  
                }  
                t.dispose();  
          }
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
        return img;  
    } 
    
    /** 
     * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出 
     */  
    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr,  
            int x, int y, boolean xory) {  
        try {  
            int w = img.getWidth();  
            int h = img.getHeight();  
            g = img.createGraphics();  
            g.setBackground(Color.WHITE);  
            g.setColor(Color.RED);  
            if (this.font != null)  
                g.setFont(this.font);  
            // 验证输出位置的纵坐标和横坐标  
            if (x >= h || y >= w) {  
                this.x = h - this.fontsize + 2;  
                this.y = w;  
            } else {  
                this.x = x;  
                this.y = y;  
            }  
            if (contentArr != null) {  
                int arrlen = contentArr.length;  
                if (xory) {  
                    for (int i = 0; i < arrlen; i++) {  
                        g.drawString(contentArr[i].toString(), this.x, this.y);  
                        this.x += contentArr[i].toString().length()  
                                * this.fontsize / 2 + 5;// 重新计算文本输出位置  
                    }  
                } else {  
                    for (int i = 0; i < arrlen; i++) {  
                        g.drawString(contentArr[i].toString(), this.x, this.y);  
                        this.y += this.fontsize + 2;// 重新计算文本输出位置  
                    }  
                }  
            }  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return img;  
    }  
  
    /** 
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本） 
     *  
     * 时间:2007-10-8 
     *  
     * @param img 
     * @return 
     */  
    public BufferedImage modifyImageYe(BufferedImage img) {  
  
        try {  
            int w = img.getWidth();  
            int h = img.getHeight();  
            g = img.createGraphics();  
            g.setBackground(Color.WHITE);  
            g.setColor(Color.blue);//设置字体颜色  
            if (this.font != null)  
                g.setFont(this.font);  
            g.drawString("reyo.cn", w - 85, h - 5);  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return img;  
    }  
  
    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {  
  
        try {  
            int w = b.getWidth();  
            int h = b.getHeight();  
              
  
            g = d.createGraphics();  
            g.drawImage(b, w/2, h/2, null);  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return d;  
    }  
    
    //从服务器上下载文件到本地
    public static void downloadFile(URL theURL, String filePath) throws IOException {  
         File dirFile = new File(filePath);
           if(!dirFile.exists()){//文件路径不存在时，自动创建目录
             dirFile.mkdir();
           }
         //从服务器上获取图片并保存
         URLConnection  connection = theURL.openConnection();
         InputStream in = connection.getInputStream();  
         FileOutputStream os = new FileOutputStream(filePath); 
         byte[] buffer = new byte[4 * 1024];  
         int read;  
         while ((read = in.read(buffer)) > 0) {  
             os.write(buffer, 0, read);  
              }  
           os.close();  
           in.close();
      }   
    
    public static void main(String[] args) throws MalformedURLException {  
  
    	WordsToImageUtils tt = new WordsToImageUtils();  
    	String flagPath="F:\\123.jpg";
        BufferedImage d = tt.loadImageLocal(flagPath);  
//        BufferedImage d = tt.loadImageUrl(mm); 
        tt.writeImageLocal("F:\\cc.png",tt.modifyImages(d,"",0,70) ); 
        //往图片上写文件  
        //tt.writeImageLocal("D:\\cc.jpg", tt.modifyImagetogeter(b, d));  
        //将多张图片合在一起  
        
        //tt.writeImageLocal("F:\\cc.png",tt.modifyImagetogeter(d,d));
    
    }
}  
