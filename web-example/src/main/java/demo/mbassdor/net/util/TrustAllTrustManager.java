/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.util;

import java.security.KeyManagementException; 
import java.security.NoSuchAlgorithmException; 
import java.security.SecureRandom; 
import java.security.cert.CertificateException; 
import java.security.cert.X509Certificate; 
 
import javax.net.ssl.KeyManager; 
import javax.net.ssl.SSLContext; 
import javax.net.ssl.SSLSocketFactory; 
import javax.net.ssl.TrustManager; 
import javax.net.ssl.X509TrustManager; 

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年5月14日 上午11:23:42
 * @version  v 0.1
 */
public class TrustAllTrustManager 
implements X509TrustManager 
{ 
private static SSLSocketFactory trustAllSocketFactory; 

public X509Certificate[] getAcceptedIssuers() 
{ 
    return new X509Certificate[0]; 
} 

@Override 
public void checkClientTrusted( X509Certificate[] cert, String authType ) 
    throws CertificateException 
{ 
} 

@Override 
public void checkServerTrusted( X509Certificate[] cert, String authType ) 
    throws CertificateException 
{ 
} 

/**
 * Get a SSLSocketFactory using this X509TrustManager. 
 */ 
public static synchronized SSLSocketFactory getSSLSocketFactory() 
{ 
    if ( trustAllSocketFactory == null ) 
    { 
        try 
        { 
            KeyManager[] km = new KeyManager[0]; 
            TrustManager[] tm = new TrustManager[] { new TrustAllTrustManager() }; 

            SSLContext context = SSLContext.getInstance( "SSL" ); 
            context.init( km, tm, new SecureRandom() ); 

            trustAllSocketFactory = (SSLSocketFactory) context.getSocketFactory(); 
        } 
        catch ( KeyManagementException | NoSuchAlgorithmException e ) 
        { 
            //Logger.error( e, "Error creating custom SSLSocketFactory" ); 
            throw new RuntimeException( "Error creating custom SSLSocketFactory", e ); 
        } 
    } 

    return trustAllSocketFactory; 
} 
}
