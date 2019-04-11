package com.example.prizedraw;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class CrawUtil {
    private static String defaultEncoding = "utf-8";

    /**
     * 获取url页面
     *
     * @param url
     * @return
     */
    public static String get(final String url) {
        if (StringUtils.isBlank(url)) {
            return StringUtils.EMPTY;
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, defaultEncoding) : null;
                    } else if (status >= 300 && status < 400) {
                        return null;
                    } else {
                        // runLog.info("get " + url + " Unexpected response
                        // status: " + status);
                        return null;
                    }
                }
            };

            String responseBody = "";
            try {
                responseBody = httpclient.execute(httpGet, responseHandler);
            } catch (SocketTimeoutException e) {
                // runLog.info(url + ", " + e.getMessage());
            } catch (ConnectTimeoutException e) {
                // runLog.info(url + ", " + e.getMessage());
            } catch (Exception e) {
                // runLog.error("get " + url, e);
            }

            return responseBody;
        } catch (Exception e) {
            // runLog.error("get " + url, e);
            return StringUtils.EMPTY;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                // runLog.error(e.getMessage());
            }
        }
    }


    /**
     * 获取url页面、get方法不允许跳转
     *
     * @param url
     * @return
     */
    public static String getMethodDisableRedirect(final String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).setRedirectsEnabled(false).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, defaultEncoding) : null;
                    } else if (status >= 300 && status < 400) {
                        return null;
                    } else {
                        // runLog.info("get " + url + " Unexpected response
                        // status: " + status);
                        return null;
                    }
                }
            };

            String responseBody = "";
            try {
                responseBody = httpclient.execute(httpGet, responseHandler);
                if (StringUtils.isBlank(responseBody)) {
                    return null;
                }
            } catch (SocketTimeoutException e) {
                // runLog.info("get " + url + " time out");
            } catch (Exception e) {
                // runLog.error("get " + url, e);
            }
            return responseBody;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                // runLog.error(e.getMessage());
            }
        }
    }


    /**
     * 将iso转码至utf8
     *
     * @param str
     * @return
     */
    public static String ISOToUtf8(String str) {
        byte[] b_iso = null;
        try {
            b_iso = str.getBytes("ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            // runLog.error("iso to utf8 ：" + str + " " + e.getMessage());
        }
        try {
            return new String(b_iso, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // runLog.error("iso to utf8 ：" + str + " " + e.getMessage());
        }
        return "";
    }

    /**
     * 检查dateString是否在过去24小时内
     *
     * @param dateString
     * @param endTime
     * @return
     */
    public static boolean isPostInLast24Hours(String dateString, long endTime) {
        long ts = Timestamp.valueOf(dateString).getTime();
        long startTime = endTime - 24 * 60 * 60 * 1000;

        return ts >= startTime && ts <= endTime;
    }

    /**
     * 从string中提取数字部分
     *
     * @param str
     * @return
     */
    public static long extractNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return Long.parseLong(matcher.group(0));
        }

        return 0;
    }

    /**
     * 该主机是否允许爬取
     *
     * @param allowedHost
     * @return
     */
    public static boolean isAllowedToCrawl(String allowedHost) {
        if (StringUtils.isBlank(allowedHost)) {
            return true;
        }

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String canonical = inetAddress.getCanonicalHostName(); //获取本地的主机域名
            return canonical.equals(allowedHost);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return false;

    }

}
