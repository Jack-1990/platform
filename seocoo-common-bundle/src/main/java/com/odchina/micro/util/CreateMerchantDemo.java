package com.odchina.micro.util;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.model.Merchant;
import cn.seocoo.platform.msbank.CreateMerchant;
import cn.seocoo.platform.msbank.PublicRequestMsg;
import cn.seocoo.platform.msbank.OrderResultMsg;
import cn.seocoo.platform.msbank.ResponseMsg;
 

public class CreateMerchantDemo {

	

	public static void main(String[] args) throws UnsupportedEncodingException {
		CreateMerchant msg = new CreateMerchant();
		
		 msg.setTxnSeq("100860001111111000");
		 msg.setPlatformId("A00002016120000000294");
		 msg.setOperId("10010A0001");
		 msg.setDataSrc("2");
		 msg.setOutMchntId("O427T20170110200501966");
		 msg.setMchntName("Demo进件测试商户");
		 msg.setMchntFullName("中国移动");
		 msg.setDevType("1");//第三方
		 msg.setAcdCode("350203");
		 msg.setProvince("河北省");
		 msg.setCity("石家庄市");
		 msg.setAddress("新华区华西路53号");
		 msg.setIsCert("0");//0-非持证商户，1-持证商户
		 msg.setLicId("35020320160831");
		 msg.setLicValidity("20301231");
		 msg.setCorpName("唐门");
		 msg.setIdtCard("130105187808235612");
		 msg.setContactName("唐三角");
		 msg.setTelephone("13880880808");
		 msg.setServTel("13839795841");
		 msg.setAutoSettle("1");//1-自动结算，2-手工提现
		 msg.setRemark("");
		 msg.setMessage("");
		 msg.setIdentification("");
		 msg.setParentMchntId("");
		 String context = JSONObject.toJSONString(msg);
		 
		 
		 
		
		System.out.println("context========="+context);
		
		String sign = PayCoreUtil.getSign(context);
		System.out.println("--------------------------------------");
		System.out.println("签名：");
		System.out.println(sign);

		String signContext = PayCoreUtil.sign(sign, context);
		System.out.println("--------------------------------------");
		System.out.println("加密前：");
		System.out.println(signContext);

		String encryptContext = PayCoreUtil.encrypt(signContext);
		System.out.println("--------------------------------------");
		System.out.println("加密后：");
		System.out.println(encryptContext);
		
/*		String sss=dncrypt(encryptContext);
		System.out.println("请求报文解密="+sss);*/
		
//	  encryptContext="MIIEGAYKKoEcz1UGAQQCA6CCBAgwggQEAgECMYGdMIGaAgECgBS1x/e/puEJbLQnTtwm2y/+fP1b2jANBgkqgRzPVQGCLQMFAARw+O36L5+VWN17SKsQPhU+o2xCp4ETp4C6z266579IeQbfTMS6dAsj0iQNUr3JJdXnlABZz+mQhSU9oNM6Pmyn4SMsGvgVhqMec4b0SLhYue7g0IHr55I6/b8vgisbHcsrcSW+76k+bF5VHANZaDOyvzCCA10GCiqBHM9VBgEEAgEwGwYHKoEcz1UBaAQQuCjLfKQl49ATlzRUkw6eFYCCAzBSM/nburQXt+ZdATQkD3nbbu2vDeAqi04EOj0H2JZqhrJmWLo0is8b7fICO1e8aaF0UH0O56QlJ2eK5E4E/6NZRjyIf/aKWEEZx++e6l/dBi9tCkjVGSmQdcezakUJS+gIT8bjTy8YibB8KqEv0YB9zT4x4UIQVuiplEHtwbAtnLFR+slwJSGs6QOgd+VjcCuQI3UzRLqMe8xn8zdmLhNVzG0Xz3OUly2+HRDqJf/mmdzbgb4joq7lw+tJ3s72kBw4Xx3p5wuyLXdiev3rwHRwm8tRAdD9sB8T+XOkZHvqPGLnGjrj+qSjqPx1MloiAzLODlh9AdxKLCT68Av2aKzYgxuYSHMXjEkx6CcsQY+/pOsjuQurEKwsyUAJqZS+iw9xYURbXZpizXYQaz84Sk4MQb7EoeXhTp1IY+aTQoKhcJOp3gTHhK9qQuTh7caLrRrmVQB9uov1/Y+29z7y2Rbjgob2R/e0HFby66VClJMVZyf3vPu2WVikWkJu/dbuYj0sUW17jIv6eDyLBU2xKWDl/vHM/7prc1VOsWo6Nb7KNd2WvFxpJVoEX1s43b9TxpEkDnPD1AJESuZhRVkFgh+d/LTU3mXux/lom4x3cjAlteSV4VN20B/VOrm/ia9s30NQ8nI205ufWJdN1KNtB5D1UrrAMjZtXoKxV2HKCZoEp95PYMGxabVvGv81ucvtitldDjOT8b46TI8fwviBLEENKTj6gYfmEJn6Iba14Vhomfpq7nMpxX9hQBFHKMUFqJgcsHTl4O7CiKAicI/aJuPdPDmxV5aGnFuzojZOsr/ELeQlmOVrLPaUHa6Yp90NyOyIXgWhXh70bd0r3YuxS4uAuMFePd99ZV46dRAF06P59um2QWM7AedQfgPbuh3GakF2slpr3dPW6q7sVPeNYrhNhZvcz//a2/0hEmdJW9LY5FhVE35wE6cu5WpZW0/h55LfhUVhXzKBBeAaI67P45CoPdDzUisXcd9h0EEsUOCBARtE1kD+UL1grwp6GBWmvzhfZjLxdQ63ylfkSKMZykRh335x8EaJ4f4nhhWlwJtwi/y0uNzpU+sQvi3hxO7WF8M=";
		
		PublicRequestMsg merhant=new PublicRequestMsg();
		merhant.setBusinessContext(encryptContext);
		
		
		//String dncryptContext ="{\"businessContext\":\"MIIEGAYKKoEcz1UGAQQCA6CCBAgwggQEAgECMYGdMIGaAgECgBS1x/e/puEJbLQnTtwm2y/+fP1b2jANBgkqgRzPVQGCLQMFAARwDuY7Cv6ZcQU8zLpliqk+tnv4CDcE4vkEHfHduNw7httCwq7kglFmh+c1atQFsdcIwcvHBQ0+1KUwnWczLxp4bFgz1AlzlCUjicgW98hAiKRB30Se22YcspjLjqODzCz9++uEn5vyZcLLWR59xsljejCCA10GCiqBHM9VBgEEAgEwGwYHKoEcz1UBaAQQArlcJ8q0PuAwGvTri5tgFoCCAzA4c6ueCCpF4Y1k+2t/dJPjkm4cKPX+pp9rJWnFutB4BWa2ve1xLA0y3wGnOdWsu1/Br8FTynL/kqsPfDbep/6ry215TEiwzafKfbmPDUfAhCvXnJZ77MjG5ZFTH6YAKKh1GvGL3ozRqcIx5g/hHSHwK9ALkJzZ0ZYp8eG7q3TPP6jD1YLbuwXfOH7abLcOZtWChlG+gHCuONgn328fO3JCu31NOoK7ilFluGfH+aQnvVExoOzJAZzdjCyZAgaNvoKVT9Pi+DFq0fFYEEwgg3VrVJJCyzjy7+2rCvOvYbrL7FPkAmEkGoXEWHRRWqtLb72QK+QzQhjCQZjWbJ9a3aRGlGw17V54KyxWRRM86wtJeSNltmvEutrzdDrYRpOu5GU/cupe5x8O6c9WToiH03Fp6vFwMZY1WhdDG6m3gNDYLIOZHOUzlyJ7VNamhJRamMXHQOFySusffpegzSzBrTj4XS7za9lPY8bVEFVtdrBBLr/l7YNKXxWXcOPSpFKOKFC3AgsbKtMowY8iObtyHnwXahnhQisWy9+9oeUGggTDrnj+3Iz/B0w3WOAz0gqqjClpJ+vmRPlQ2LF1cUHxyq581eWjKBKKdFNGYHlWgmx/ODdBLqtTqadotbYA6h6wb2eqhW5akXLaxqoIgd811QqW24UEsNlwqAZ0482rw301Lp5ifErmeC1PZ6oN/gLddJ3rtgXvYLqn2Rmvp5Up2mcCVhPBYsFQek3+MkGpMfRSBxuFZt660xlIUwDum8vF+N2t3PFKopNr833BcH6UnsZROIBS2m+7EldDRlhMWAU3uiADplfHaRjePQtbWV8rGh2L0HXCTFQcmruIse1iHhVvXgThLZXzlKZLX+cOrPyljktpT/KLahtVANylBQGR9IPdyibxLscknZOcPcscaUuPdiU5L3hu0W9UxtEnwcRSOxaQy+CaE0G6zm9yUvg9olSE+FVc8EXowE9ulc/wyNQ+ZWMM+h5kQWp4BNfBnuRl0TYUVNWoRTt+jaDpOrQ2tXE4s6gQqW9lKAop7xpwRnVW9dv9/YThpsjRRHHf77UNBQAo5QmLw7WItJufof+pNt0=\",\"merchantNo\":\"\",\"merchantSeq\":\"\",\"reserve1\":\"\",\"reserve2\":\"\",\"reserve3\":\"\",\"reserve4\":\"\",\"reserve5\":\"\",\"reserveJson\":\"\",\"securityType\":\"\",\"sessionId\":\"\",\"source\":\"\",\"transCode\":\"\",\"transDate\":\"\",\"transTime\":\"\",\"version\":\"\"}";
		
		//dncryptContext=java.net.URLEncoder.encode(dncryptContext.replace("+", " "), "utf-8"); 
		 
		 System.out.println("报文信息======"+JSONObject.toJSONString(merhant)+"\n");
		
		 String str=HttpUtils.sendPost("http://wxpay.cmbc.com.cn/mobilePlatform/lcbpService/mchntAdd.do", JSONObject.toJSONString(merhant));
		
		 System.out.println("银行返回="+str);

		  
		PublicRequestMsg m=JSONObject.parseObject(str, PublicRequestMsg.class);
	 
		
		String dncryptContext = PayCoreUtil.dncrypt(m.getBusinessContext());
		System.out.println("--------------------------------------");
		System.out.println("解密后：");
		System.out.println(dncryptContext);
		
		
	 	String signChkResult = PayCoreUtil.signCheck(dncryptContext);
		System.out.println("--------------------------------------");
		System.out.println("验证签名结果：");
		System.out.println(signChkResult); 
		
		//String msg11="MIIEWAYKKoEcz1UGAQQCA6CCBEgwggREAgECMYGdMIGaAgECgBRZlNziHI2cFrW5Ep1ym13ckOMoGTANBgkqgRzPVQGCLQMFAARwJA31R1rrzrqps+v4kxoIobjpJB8phV5qZlla+HLYJXd5TfOXFT7wEaYiJh2CBE7sqsxaFALvgK1/LMvDtVvF122frjwAo3EmGCsqdsl3Y+24Hd2GT+ftdkRYa1CD7q/YdNKeiDTXH27L+07cS9w2MzCCA50GCiqBHM9VBgEEAgEwGwYHKoEcz1UBaAQQmHduB8IKFtS2nYArVae4nICCA3BukLnSkJ+2o+N6CjeNItKqzag0AfrGcbxBEVls4WobOgDgWCZSIFoc0Zt2/GOWXLxlxOZQrtQujsYzrP2Myyi21CK/icw4/bRy3OJiTWb2ikVmA2A+/Q2tGFf9rpYG3k+WKtajplHnEvk6pQg34z/HOSVir46q4vX2JRMXHhSWDtXgMTeQnkVHCtcJkJRtMqaGIqDIT3EwNoGCC9L75TKYhN4JmasxMIUZ01BbHjUBtzb8z79G9esYs64jxF0k8XRTU0OptORpumKxFdBYIZ/ssZewW+y3mWPd6XfEzSS5TnPuccNz53UEP7m5vMmACNM38mVvCYu5zzi1hUg3Q1Bmtgt1Zgy8Bx67NMVssU9HDSlIQYAZ3GXyikbDW2eSNeQmWzt79woY49oIPyLMRPwEWtR5ciOdtLJvRqgr4U3JfJkLrXUQ9OfwwWMmaW+aGv4AtESx8TkfexwNeMNDKzCEc1v2Wt6ZMyU6FoOXujs17z/YSXEw6xTLshy8UGMDvaqWbunCPCN8lV4FluQ85/X2xQC+V4vc56bTSJk0yMk1NE7yrqMT6APFWQBgh6w+7RgH0F7WuK3qdAfZY4rWEoFkQRm8UXyMolXWf9hjuLWD8qnxcb+8t3znNX54VFXRkTEb+3vV8Ue9AGGEIhpw+FfmzrXvglD4yNGANM2RD3BYGji0+y3Wr0Za+D9Hzsz1n+hRDd4yODwJ2ZnlST4orNKuf2ZTJ4FolLwT/cn8PiW2aO4B3267zeCfkOoO8yPH9KRf8h/ZaVcRiOT627B44wyDTU2v1Cze5TcHD+o37DPaded3CW+bpdfch72uGxKv5+V4OG2jl4b2Mil/wdYoYbySeqeGWU4/B/669tQYsrIyBXbIKdBwPTIyH8c9+X8jY93MkuWXrDWP2xXuo7jQlYmI9TEQlcI1cmRAYUQcPoKJ/uE41FY7EvKA7CiAAmWMSnvNjn2l7cqjQrSK9oTU5pPE+AZHgH8FDuIOoscBYbRKXbnxuETcKPg5SCB4QkOd99Djg3/JNlWJLzwG1vdHQNzznx8atKAl8PM1kbKyGvfhVgeRrn76KDMslo7zj/GDhXXp6OrbTUzShyU0k7bjs6AHIKXP+DeLCQsAVljMLJdMITWpke/Pvz/rApt4bWvHKI+0l/XpgRmp0AkChD0m735T";
		//String msg11="MIIESAYKKoEcz1UGAQQCA6CCBDgwggQ0AgECMYGdMIGaAgECgBRZlNziHI2cFrW5Ep1ym13ckOMoGTANBgkqgRzPVQGCLQMFAARw5G7txJmGTQE9FdSmjfjOXbO0jafrvgLXENd8nCoMzlndKd25Xd66Frqn85/YEcZ4NgKSjecJSrnhcRoIdDHVglXpkwWitxKDQRW/6IiyoDvt/8ctui0suJIZD3yK2YLa0jb8lrkWqi7k7tT4aJpiVzCCA40GCiqBHM9VBgEEAgEwGwYHKoEcz1UBaAQQt12IJkFOw6nRX5kRckiY34CCA2BIol48sMZKXa6caPdZRzRLRR7gktrJlnZsZevoIG5wB0hNSsAAWg7Bx4AZujhyrkWEt/+otkNixVugnSLerCN9+OdBUXfXA79L7ZqBl/eM6tDSTObnSMtcIVQgtTG3pgCrK8Nblg43kHrscNXVFcQQlcUoZJASdPyjydl9Ganibl6RrViWMo3OmUzEC6NgHuq0YdpAAtbp7GWYRJ5ujXP+2W3Z+w658aDGVCmKEikGd74FtNZKLH1ZQNr0tEUkknCS6x1eM7tnzoBCC7tqkqp6/yPxEzWh2urhDqUiw5cIFl98m8wu99mGIIM9dgYjk5/Sd9kJztUxIrXwqIh9bJtuoAt853Xids079r9dNnmJhoXtle5FcC2X4Hx67ec4TiWjyulT2BmmSlc/GWyZzQwyTqV9VCBSmKu0XsLRAGThdqsOcaAS1+f6+ndKDic8UAYcRCCFkZ/CwLZH9mQsKEaJkqRn2v812VOKPXOjqgNAlNvO+zVIK8FEvnKIyh2r7ASFaNrqHDieavOaXkeCV2TGS5mqfkCxAsCYvmziHMXDLKSM9jK61OXHx+ltVphCH6vgxf/TEDdf/TNDe37OZx4ynWaBBY/FbCoXbpG7JVbbN57n6oZWPcHt/RxbGztOx0IM6yYiRDZzqc6bQa25dUy6uyHwXT8/XnJXVayKKq4x1z4l0CjYBLgrctK47oUO8FsI7aNC/yLK4Bn4MB/u7FROG+aOOO8MkVEF4Z8Kdao0phfXIG4w0ufVlmWzXk+w5pwuP8G40VxUckvnpKBKSI7VX/EAJmRSkgWGrXm4/xjsFrQFCBNN+kjE2V89JV2sbu/qAge79/H90IJpH8ADQ3vlBgogF0YS35TcmdwBdJqI/YSFbhQpRSY8fpTw6nWtAoJKg7L7Gulwy6+YdjsmREMfxKmcpGH8HhjIkbBNvxsnWeMCuAh6cBwfGTPBtUwhbhzS05A08qzCBK4ROmhJz9Dmj4YIc+xhBL8wI6vCj5vxoKJ2EUoBQOfyUOnIGjvHDYt/NHJg0Ljy1KDmzGTA0Wm4BnceiwS2AS7gNmHxFYbG1m9MDmgbY2G7NaaWEDi/ZTHDI5aOwskSi1rx4eF0TGdKAlazfwlx1/zudirl9OvC59jZstEOYtaqNS9IzWz0O8Q=";
		
		String msg11="MIIEWAYKKoEcz1UGAQQCA6CCBEgwggREAgECMYGdMIGaAgECgBRZlNziHI2cFrW5Ep1ym13ckOMoGTANBgkqgRzPVQGCLQMFAARwiE+H+J20u02bGDAGW+TZ71yLume7pijjA0OTYooMYRHrjOBgP5eYIcRHlubRrbzrxJTz/CrIn2IIe42rXGh1x6LGa6sS3dqVi/ENYPhMsXvxF9QpAGE2WGms8ur2U5v2ep49cu0v+rCzte6B9urIIDCCA50GCiqBHM9VBgEEAgEwGwYHKoEcz1UBaAQQLzienY4MJ0R1ntmZ4yDgc4CCA3DvKf+e1QFnL9G2MHwjbtV3UzkUmZPpm/uU8UIVpp0BogCz3Q3xBZ/SwHeaBZv/sJ3TzaLMQs/k9Ec5XXCguw+iZ3eCJL7jqufth6DlA73sfxnR0lTLd5wTmuUFWX0EkM1uLGsz9OVlhOJO2oCHtuHfKg3XJPl/pFvDQt4hph7O4NUPcTuaNXLUXngnNJ//BcUjlk8cIozqbulXSv/GzHuVzcJkGHWuHWZhbZkYzR/cZgC3QzylNe/0QLgQT+KDY2rIHfLqkcoDqDUj89k4mP++sTMaa3y/bb1cpIawbGMkf2bqjBr8mWEa23PGxiYFxmz9gK2Bow0x4ACgMe3YKTKQqEU23zaL0qmzDy5K3EZTTP2hrvMw5TrxIB9z56i/Y3pYhL63ighSmtEEt5fYBLD/dTxS2aVKXhoVX7pzngqcE3Gq+RJY5FDikiBe8xjlIAgYrBneD7I/UFSkWaG8FtOhTvV0zj4TqeAX9ftEegVSckAlBL5YuJUgdEFBM5sCkdj97gq02C87Ez8PZrnAfhyi7Voj73TbWZRRTiw0/0LLZksx2z9PuyV+gJRae6XRLXTDRcQwFRRCG+aQzFNK6k8XTkqeH9cVQmO6h4oaEHjJm8arI/CNfq+MTrzeNSNabGo4/rCFLKP8ONlMpEHLz4kqpb9B0tHxVShwfZH+WwnUJi2+S//rsyx9l5nP2WxGdqcb+C2Aq0E1QB1w+ToFAUGTqmYjkbeoy0oWpgzwazKTGwfNwL6dehLHZHM0C3afiMk2OEw7QYgiAbYJaP5dx+Bcyal6hmeVvXhsNvAJ8u8uFfFE6rWGXBxPG5h0JCwzWJA220BseYFKxRGcvz1/TYMPFLlDQbqxXEV6hT/pucLQSx15FSgs/XXoy+U+eFF5pETWpPFoh9E3fyLdVAqqMydg78ZOLsCEgJYe0w3l6+b2k7F1+Mwd4MduiyJ6nzu5TaWqNb3RtclZXRMJlVfVKwJDP09wkQyhbNcKe7iJNcCfH7zwulumkCvpLKAhI31mHPvsz/OZi8ObdEfzq7kRD2X8bkocVwl7eiXVBm2XxyTqUwbMnUVEJJoW0tHFr3Tre2EZ9A2/dWdt3pSzMBbLVpP5f1mzLU8Y88k1HQW8h7lAeB6rw5XwcQspGS4uGVmlnVHeOofccNPjbRx1KpaETlWn";
		 
		String dncryptContext1 = PayCoreUtil.dncrypt(msg11);
		System.out.println("--------------------------------------");
		System.out.println("解密后：");
		System.out.println(dncryptContext1);
		
		 ResponseMsg rm=JSONObject.parseObject(dncryptContext1, ResponseMsg.class);
			String body = rm.getBody();
			System.out.println("最终结果：");
			System.out.println(body);
			
			OrderResultMsg orm=JSONObject.parseObject(body, OrderResultMsg.class);
			
		 	String signChkResult1 = PayCoreUtil.signCheck(dncryptContext);
			System.out.println("--------------------------------------");
			System.out.println("验证签名结果：");
			System.out.println(signChkResult1);
		
	}

	

	
}
