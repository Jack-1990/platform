package com.odchina.micro.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cfca.sm2.signature.SM2PrivateKey;
import cfca.sm2rsa.common.Mechanism;
import cfca.sm2rsa.common.PKIException;
import cfca.util.CertUtil;
import cfca.util.EnvelopeUtil;
import cfca.util.KeyUtil;
import cfca.util.SignatureUtil2;
import cfca.util.cipher.lib.JCrypto;
import cfca.util.cipher.lib.Session;
import cfca.x509.certificate.X509Cert;
import cfca.x509.certificate.X509CertHelper;
import cn.seocoo.platform.msbank.Config;

/**
 * 民生银行支付接口根据类
 *
 */
public class PayCoreUtil {

	private static Session session;
	
	static {
		try {
			JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
			session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
		} catch (PKIException e) {
			e.printStackTrace();
		}
	}
	
	public static String getSign(String context) {
		String priKeyAbsPath = Config.getProperty("merchantPrivateKey");
		String priKeyPWD = Config.getProperty("merchantPwd");
		String sign = "";
		try {
			JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
			Session session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
			SM2PrivateKey priKey = KeyUtil.getPrivateKeyFromSM2(priKeyAbsPath, priKeyPWD);
			sign = new String(
					new SignatureUtil2().p1SignMessage(Mechanism.SM3_SM2, context.getBytes("UTF8"), priKey, session));
		} catch (PKIException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sign;
	}
	
	
	/**
	 * 验证签名
	 * @param dncryptContext  需要验证签名的明文
	 * @return
	 */
	public static String signCheck(String dncryptContext) {
		String certAbsPath = Config.getProperty("bankPublicKey");
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String, Object> paraMap = gson.fromJson(dncryptContext, Map.class);
		String sign = paraMap.get("sign").toString();
		String body = paraMap.get("body").toString();
		boolean isSignOK = false;
		try {
			X509Cert cert = X509CertHelper.parse(certAbsPath);
			PublicKey pubKey = cert.getPublicKey();
			isSignOK = new SignatureUtil2().p1VerifyMessage(Mechanism.SM3_SM2, body.getBytes("UTF8"),
					sign.getBytes(), pubKey, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isSignOK) {
			return "验签通过";
		} else {
			return "验签不通过";
		}
	}
	

	/**
	 * 签名
	 * @param sign
	 * @param context
	 * @return
	 */
	public static String sign(String sign, String context) {
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		Gson gson = builder.create();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sign", sign);
		paramMap.put("body", context);
		String signInfo = gson.toJson(paramMap); // 待加密字符串
		return signInfo;
	}

	/**
	 * 加密
	 * @param signContext   需要加密的报文
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String encrypt(String signContext) {
		String certAbsPath = Config.getProperty("bankPublicKey");
		X509Cert cert = null;
		try {
			cert = X509CertHelper.parse(certAbsPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PKIException e) {
			e.printStackTrace();
		}
		X509Cert[] certs = { cert };
		byte[] encryptedData = null;
		try {
			encryptedData = EnvelopeUtil.envelopeMessage(signContext.getBytes("UTF8"), Mechanism.SM4_CBC, certs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (PKIException e) {
			e.printStackTrace();
		}
		String encodeText = null;
		try {
			encodeText = new String(encryptedData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeText;
	}

	/**
	 * 解密
	 * @param encryptContext   需要解密的报文
	 * @return
	 */
	public static String dncrypt(String encryptContext) {
		String priKeyAbsPath = Config.getProperty("merchantPrivateKey");
		String priKeyPWD = Config.getProperty("merchantPwd");
		String decodeText = null;
		try {
			PrivateKey priKey = KeyUtil.getPrivateKeyFromSM2(priKeyAbsPath, priKeyPWD);
			X509Cert cert = CertUtil.getCertFromSM2(priKeyAbsPath);
			byte[] sourceData = EnvelopeUtil.openEvelopedMessage(encryptContext.getBytes("UTF8"), priKey, cert, session);
			decodeText = new String(sourceData, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodeText;
	}

	
	
	
	
}
