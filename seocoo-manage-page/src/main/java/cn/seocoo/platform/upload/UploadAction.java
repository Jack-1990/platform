package cn.seocoo.platform.upload;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.CompressPicUtils;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.common.util.WordsToImageUtils;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;



public class UploadAction extends BaseAction{

	private File imageSelfFile;
	private String isPress;//是否压缩 0不压缩，1压缩
	public void uploadImage() throws IOException{
		if("1".equals(isPress)){
			//这里按比例压缩图片
			uploadImageCompress();
		}else if ("2".equals(isPress)){
			//这里按比例压缩图片(自定义模板上传图片)
			uploadImageCompress_CustomStle();
		}else{
			//这里不压缩图片
			uploadImageUncompress();
		}
	}
	//不压缩图片
	public void uploadImageUncompress() throws IOException{
		System.out.println(isPress);
		String fileNameAtr=this.request.getParameter("filename_atr");
		FileOutputStream fos = null;
		FileInputStream fis= null;
		//保存文件名称
		String fileName="image_"+StringUtil.random6()+"."+fileNameAtr;
		String uploadPath=SystemConfigUtil.getSingleProperty("upload_save_path").getPropertyValue();
		String path=uploadPath+"/"+fileName;
		
		String suc;
		try {
			fis=new FileInputStream(getImageSelfFile());
			fos = new FileOutputStream(new File(path));
			byte[] strByte  =new byte[(int) getImageSelfFile().length()];
			//生成文件
			while(fis.read(strByte)!=-1){
				String str1=new String(strByte);
				fos.write(strByte);
			}
			fos.flush();
			suc="1";
			//访问路劲
		    path=SystemConfigUtil.getSingleProperty("upload_visit_path").getPropertyValue()+"/"+fileName;
		} catch (Exception e) {
			suc="0";
			e.printStackTrace();
		} finally {
			if (fos != null) {
	            try { fos.close(); } catch (IOException e) {}
	        }
			if(fis!=null){
				fis.close();				
			}
		}
		//返回保存结果
        JSONObject json=new JSONObject();
        json.put("result", suc);
        json.put("url", path);
        this.sendMessages(json.toString());
	}
	//按比例压缩图片
	public void uploadImageCompress() throws IOException{
		String fileNameAtr=this.request.getParameter("filename_atr");
		FileOutputStream fos = null;
		FileInputStream fis= null;
		//保存文件名称
		String fileName="image_"+StringUtil.random6()+"."+fileNameAtr;
		String uploadPath=SystemConfigUtil.getSingleProperty("upload_save_path").getPropertyValue();
		//uploadPath="D:/Workspaces_wss/new/codes/resources/manage/images/upload";
		String path=uploadPath+"/"+fileName;
		//生成图片压缩以后的新的访问地址
		String newPath="";
		String suc;
		try {
			fis=new FileInputStream(getImageSelfFile());
			fos = new FileOutputStream(new File(path));
			byte[] strByte  =new byte[(int) getImageSelfFile().length()];
			//生成文件
			while(fis.read(strByte)!=-1){
				String str1=new String(strByte);
				fos.write(strByte);
			}
			fos.flush();
			suc="1";
			
			//调用压缩实例  按照比例压缩  取上传到服务器上的图片地址 相对路径的地址   
		    File inFile = new File(path);
		    //图片压缩完成以后 重新生成预览地址 区别于上传的那张图片 重新生成图片地址 相当于重新上传 所以必须是相对路径
		    String newFileName="image_"+StringUtil.random6()+"."+fileNameAtr;
		    newPath=SystemConfigUtil.getSingleProperty("upload_save_path").getPropertyValue()+"/"+newFileName;
		    //测试本地上传
		    //newPath = "D:/Workspaces_wss/new/codes/resources/manage/images/upload" + "/" + newFileName;
			File outFile = new File(newPath);
			int width = 900;
			int height = 570;
			CompressPicUtils util = new CompressPicUtils();
			util.compressPic(inFile, outFile, width, height, true);
			
			//访问路劲  访问的是压缩以后的新图片的地址
			//通过servletContext获取资源路径
		    path= SystemConfigUtil.getSingleProperty("upload_visit_path").getPropertyValue()+"/"+newFileName;
		   // path="http://127.0.0.1/manage/images/upload"+"/"+newFileName;
		    
		} catch (Exception e) {
			suc="0";
			e.printStackTrace();
		} finally {
			if (fos != null) {
	            try { fos.close(); } catch (IOException e) {}
	        }
			if(fis!=null){
				fis.close();				
			}
		}
		//返回保存结果
        JSONObject json=new JSONObject();
        json.put("result", suc);
        json.put("url", path);
        response.setStatus(200);
        response.getWriter().print(json.toString());
	}
	
	//按比例压缩图片(自定义模板上传图片)
	public void uploadImageCompress_CustomStle() throws IOException{
		String fileNameAtr=this.request.getParameter("filename_atr");
		FileOutputStream fos = null;
		FileInputStream fis= null;
		//保存文件名称
		String fileName="image_"+StringUtil.random6()+"."+fileNameAtr;
		String uploadPath=SystemConfigUtil.getSingleProperty("upload_save_customStylePath").getPropertyValue();
		  //uploadPath="D:/dianhashop/seocoo/resources/manage/images/uploadCustomStyle";
		String path=uploadPath+"/"+fileName;
		//生成图片压缩以后的新的访问地址
		String newPath="";
		String suc;
		int imgWidth = 0;// 压缩后的图片宽度
		int imgHeight = 0;//压缩后的图片高度
		try {
			fis=new FileInputStream(getImageSelfFile());
			fos = new FileOutputStream(new File(path));
			byte[] strByte  =new byte[(int) getImageSelfFile().length()];
			//生成文件
			while(fis.read(strByte)!=-1){
				String str1=new String(strByte);
				fos.write(strByte);
			}
			fos.flush();
			suc="1";
			
			//调用压缩实例  按照比例压缩  取上传到服务器上的图片地址 相对路径的地址   
		    File inFile = new File(path);
		    //图片压缩完成以后 重新生成预览地址 区别于上传的那张图片 重新生成图片地址 相当于重新上传 所以必须是相对路径
		    String newFileName="image_"+StringUtil.random6()+"."+fileNameAtr;
		    newPath=SystemConfigUtil.getSingleProperty("upload_save_customStylePath").getPropertyValue()+"/"+newFileName;
 
		    //测试本地上传
 	        //newPath = "D:/dianhashop/seocoo/resources/manage/images/uploadCustomStyle" + "/" + newFileName;
 
			File outFile = new File(newPath);
			int width = 900;
			int height = 570;
			CompressPicUtils util = new CompressPicUtils();
			util.compressPic(inFile, outFile, width, height, true);
			
			//访问路劲  访问的是压缩以后的新图片的地址
			//通过servletContext获取资源路径
		    path= SystemConfigUtil.getSingleProperty("upload_visit_customStylePath").getPropertyValue()+"/"+newFileName;
 		    //path="http://127.0.0.1/manage/images/uploadCustomStyle"+"/"+newFileName;
		    // 获取压缩后的图片宽度 和高度
		    BufferedImage bi = ImageIO.read(inFile);
		    imgWidth = bi.getWidth();
		    imgHeight = bi.getHeight();
		    
		} catch (Exception e) {
			suc="0";
			e.printStackTrace();
		} finally {
			if (fos != null) {
	            try { fos.close(); } catch (IOException e) {}
	        }
			if(fis!=null){
				fis.close();				
			}
		}
		//返回保存结果
        JSONObject json=new JSONObject();
        json.put("result", suc);
        json.put("url", path);
        json.put("imgWidth",imgWidth);
        json.put("imgHeight", imgHeight);
        response.setStatus(200);
        response.getWriter().print(json.toString());
	}
	
	
	/**
	 * 二维码增加logo
	 * @throws IOException
	 */
	public void qrCodeLogo() throws IOException{
		 //request.setCharacterEncoding("utf-8"); 
		 //获取中间图片的地址
        String urlPath = SystemConfigUtil.getSingleProperty("Path_wechatpic").getPropertyValue(); 
   	    WordsToImageUtils tt = new WordsToImageUtils();      
//      BufferedImage d = tt.loadImageLocal(flagPath); 
   	    //开始装载图片   画文字
        BufferedImage d = tt.loadImageUrl(urlPath);
        BufferedImage image = tt.modifyImage(d,"",0,690); //0   170
//      tt.writeImageLocal("E:\\cc.jpg",image); 
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        int i = is.available(); // 得到文件大小
        byte data[] = new byte[i];
        is.read(data); // 读数据
        is.close();
        response.setContentType("image/*;charset=utf-8"); // 设置返回的文件类型
        OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
        toClient.write(data); // 输出数据
        toClient.close();
	}
	
	
	
	public File getImageSelfFile() {
		return imageSelfFile;
	}
	public void setImageSelfFile(File imageSelfFile) {
		this.imageSelfFile = imageSelfFile;
	}
	public String getIsPress() {
		return isPress;
	}
	public void setIsPress(String isPress) {
		this.isPress = isPress;
	}
	
}
