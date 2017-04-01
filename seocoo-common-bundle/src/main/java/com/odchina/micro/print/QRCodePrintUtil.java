package com.odchina.micro.print;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


/**
 * 根据内容生成二维码，并将二维码生成打印机识别的命令信息集
 */
public class QRCodePrintUtil 
 {
	
	private static final String CODE = "utf-8";  
	private static final int BLACK = 0xff000000;  
	private static final int WHITE = 0xFFFFFFFF; 
	
	/**
	 * 根据路径生成二维码，并将二维码生成热敏打印机的所能识别的命令集
	 * @param path
	 * @param height
	 * @return
	 */
	public static byte[] getQrcodePrintInfo(String path)
	{
		 //生成二维码
		 BufferedImage bi=getQRCode(path,240);
	     
		 //获取打印机打印二维码的命令byte数组
		 Bitmap bitmap = new Bitmap();
		 bitmap.setBufferedImage(bi);
		 byte[] qrbyte=draw2PxPoint(bitmap);
		 
		 return qrbyte;
	}
	
	
	/**
	 * 根据二维码的图片的地址，将二维码生成热敏打印机的所能识别的命令集
	 * @param path 已经生成好的压缩（240*240）过后的二维码图片地址
	 * @return
	 */
	public static byte[] getQrcodePrintInfo2(String path)
	{
		 //生成二维码
		 BufferedImage bi=getBinaryImage(path);
	     
		 //获取打印机打印二维码的命令byte数组
		 Bitmap bitmap = new Bitmap();
		 bitmap.setBufferedImage(bi);
		 byte[] qrbyte=draw2PxPoint(bitmap);
		 
		 return qrbyte;
	}
	
	
	/**
	 * 将图片进行二值化
	 * @param path 已经生成好的压缩过后的二维码图片地址
	 * @return
	 */
	public static BufferedImage getBinaryImage(String path)
	{
		try
		{
		BufferedImage bi=ImageIO.read(new File(path));//通过imageio将图像载入
		int h=bi.getHeight();//获取图像的高
		int w=bi.getWidth();//获取图像的宽
		int[][] gray=new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				gray[x][y]=getGray(bi.getRGB(x, y));
			}
		}
		
		BufferedImage nbi=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_BINARY);
		int SW=160;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if(getAverageColor(gray, x, y, w, h)>SW){
					int max=new Color(255,255,255).getRGB();
					nbi.setRGB(x, y, max);
				}else{
					int min=new Color(0,0,0).getRGB();
					nbi.setRGB(x, y, min);
				}
			}
		  }
		    return nbi;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getGray(int rgb){
		String str=Integer.toHexString(rgb);
		int r=Integer.parseInt(str.substring(2,4),16);
		int g=Integer.parseInt(str.substring(4,6),16);
		int b=Integer.parseInt(str.substring(6,8),16);
		//or 直接new个color对象
		Color c=new Color(rgb);
		r=c.getRed();
	    	g=c.getGreen();
		b=c.getBlue();
		int top=(r+g+b)/3;
		return (int)(top);
	}
	
	/**
	 * 自己加周围8个灰度值再除以9，算出其相对灰度值
	 * @param gray
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public static int  getAverageColor(int[][] gray, int x, int y, int w, int h)
    {
        int rs = gray[x][y]
                      	+ (x == 0 ? 255 : gray[x - 1][y])
			            + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
			            + (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1])
			            + (y == 0 ? 255 : gray[x][y - 1])
			            + (y == h - 1 ? 255 : gray[x][y + 1])
			            + (x == w - 1 ? 255 : gray[x + 1][ y])
			            + (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
			            + (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
        return rs / 9;
    }

	
	
	
    
	/**
	* 264*264的图片，8个字节（8个像素点）是一个二进制，将二进制转化为十进制数值
	* y轴：24个像素点为一组，即264就是10组（0-11）
	* x轴：240个像素点（0-264）
	* 里面的每一组（24*264），每8个像素点为一个二进制，（每组有3个，3*8=24）
	* 把一张Bitmap图片转化为打印机可以打印的bit(将图片压缩为264*264)
	* @param bit
	* @return
	*/
	public static byte[] draw2PxPoint(Bitmap bit) {  
		
	      byte[] data = new byte[8820];
	      int k = 0;
          //居中	      
	      byte[] center = new byte[] { 0x1b, 0x61, 1 };
	      data[k++] = center[0];
	      data[k++] = center[1];
	      data[k++] = center[2];
	      //设置行高为0 
	      byte[] datas = new byte[] { 0x1B, 0x33, 0x00 };
	      data[k++] = datas[0];
	      data[k++] = datas[1];
	      data[k++] = datas[2];
	      
	      for (int j = 0; j < 11; j++) {
	              data[k++] = 0x1B;
	              data[k++] = 0x2A;
	              data[k++] = 33; // m=33时，选择24点双密度打印，分辨率达到200DPI。
	              data[k++] = (byte)(bit.getWidth() % 256);
	              data[k++] =  (byte)(bit.getWidth() / 256); 
	              for (int i = 0; i < 264; i++) {
	                      for (int m = 0; m < 3; m++) {
	                              for (int n = 0; n < 8; n++) {
	                                      byte b = px2Byte(i, j * 24 + m * 8 + n, bit);
	                                      data[k] += data[k] + b;
	                              }
	                              k++;
	                      }
	              }
	              data[k++] = 10;
	      }
	      //居左	      
	      byte[] left = new byte[] { 0x1b, 0x61, 0 };
	      data[k++] = left[0];
	      data[k++] = left[1];
	      data[k++] = left[2];
	      
	      return data;
	}
	
	/**
	* 图片二值化，黑色是1，白色是0
	* @param x  横坐标
	* @param y  纵坐标                        
	* @param bit 位图
	* @return
	*/
	public static byte px2Byte(int x, int y, Bitmap bit) {
	      byte b;
	      int pixel = bit.getPixel(x, y);
	      int red = (pixel & 0x00ff0000) >> 16; // 取高两位
	      int green = (pixel & 0x0000ff00) >> 8; // 取中两位
	      int blue = pixel & 0x000000ff; // 取低两位
	      int gray = RGB2Gray(red, green, blue);
	      if ( gray < 128 ){
	              b = 1;
	      } else {
	              b = 0;
	      }
	      return b;
	}
	
	/**
	* 图片灰度的转化
	* @param r  
	* @param g
	* @param b
	* @return
	*/
	private static int RGB2Gray(int r, int g, int b){
	      int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b);  //灰度转化公式
	      return  gray;
	}
	
	/** 
     * 生成二维码 
     * @param str 内容 
     * @param height 高度（px） 
     */  
    public static BufferedImage getQRCode(String str, Integer height)  
    {  
        if (height == null || height < 240)  
        {  
            height = 240;  
        }  
        try  
        {  
            // 文字编码  
            Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object>();  
            ht.put(EncodeHintType.CHARACTER_SET, CODE);  
            ht.put(EncodeHintType.MARGIN, 0);//去除图片白色边框
            BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, height, height, ht); 
            return toBufferedImage(bitMatrix);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
	   
	   
    /** 
     * 转换成图片 
     * @param matrix 
     * @return 
     */  
    private static BufferedImage toBufferedImage(BitMatrix matrix)  
    {  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);  
        for (int x = 0; x < width; x++)  
        {  
            for (int y = 0; y < height; y++)  
            {  
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
            }  
        }  
        return image;  
    }  
	 
    /** 
     * 生成二维码，写到文件中 
     * @param str 
     * @param height 
     * @param file 
     * @throws IOException 
     */  
    public static void getRQWriteFile(String str, Integer height, File file)  throws IOException  
    {  
        BufferedImage image = getQRCode(str, height);  
        ImageIO.write(image, "png", file);  
    } 
}
