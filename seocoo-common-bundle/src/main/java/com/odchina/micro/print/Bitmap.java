package com.odchina.micro.print;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
 
public final class Bitmap {
	private BufferedImage bufferedImage;
	
	public Bitmap(int width, int height, int imageType) {
		bufferedImage=new BufferedImage(width, height, imageType);
	}
	
	public Bitmap(ColorModel cm, WritableRaster raster, boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
		bufferedImage=new BufferedImage(cm, raster, isRasterPremultiplied, properties);
	}

	public Bitmap(int width, int height, int imageType, IndexColorModel cm) {
		bufferedImage=new BufferedImage(width, height, imageType, cm);
	}
	
	public Bitmap(ImageInputStream paramImageInputStream) throws IOException{
		bufferedImage= ImageIO.read(paramImageInputStream);
	}
	public Bitmap(File paramFile) throws IOException{
		bufferedImage= ImageIO.read(paramFile);
	}
	
	public Bitmap(URL paramURL) throws IOException{
		bufferedImage= ImageIO.read(paramURL);
	}
	public Bitmap(InputStream paramInputStream) throws IOException{
		bufferedImage= ImageIO.read(paramInputStream);
	}
	
	public Bitmap()
	{
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public int getWidth() {
		return bufferedImage.getWidth();
	}
	
	public int getHeight() {
		return bufferedImage.getHeight();
	}
    public int getPixel(int x, int y) {
    	return bufferedImage.getRGB(x, y);
    }
}