package com.flickerpuzzle.core.network;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpMessage;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import android.util.Log;
import android.webkit.CookieManager;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */

class HttpMessageFactory {
    public static final String TAG = "[Aura]HttpMessageFactory";
    private final String mCookieDomain;

    /**
     * Creates an httpMessageFactory controlling the specified domain.
     *
     * @param cookieDomain
     */
    public HttpMessageFactory(String a_cookieDomain) {
        this.mCookieDomain = a_cookieDomain;

    }

    /**
     * Create a new http uri request with more standard datatypes.
     *
     * @param url
     *            The url to push the request to.
     * @param p_Params
     *            String parameters to pass in the post.
     * @throws URISyntaxException
     */
    public HttpMessage createHttpGetRequest(String a_url,
                                            Map<String, String> a_headers) throws URISyntaxException {
        HttpGet t_HttpMethod = new HttpGet(a_url);

        if (a_headers != null) {
            for (final String key : a_headers.keySet()) {
                t_HttpMethod.addHeader(key, a_headers.get(key));
            }

            if (mCookieDomain != null) {
                final CookieManager cookieManager = CookieManager.getInstance();
                t_HttpMethod.addHeader("Cookie",
                        cookieManager.getCookie(this.mCookieDomain));
            }
        }
        Log.d(TAG, t_HttpMethod.getRequestLine().toString());
        return t_HttpMethod;
    }

    /**
     * Create a new http uri request with more standard datatypes.
     *
     * @param url
     *            The url to push the request to.
     * @param headers
     *            String headers to pass.
     * @throws URISyntaxException
     */
    public HttpMessage createHttpDeleteRequest(String a_url,
                                               Map<String, String> a_headers) throws URISyntaxException {
        HttpDelete t_HttpMethod = new HttpDelete(a_url);
        if (a_headers != null) {
            for (final String key : a_headers.keySet()) {
                t_HttpMethod.addHeader(key, a_headers.get(key));
            }

            if (mCookieDomain != null) {
                final CookieManager cookieManager = CookieManager.getInstance();
                t_HttpMethod.addHeader("Cookie",
                        cookieManager.getCookie(this.mCookieDomain));
            }
        }

        return t_HttpMethod;
    }

    /**
     * Create a new http uri request and submit for execution. Promote this to
     * public when I need to actually instantiate an HttpMessage with these
     * parameters.
     *
     * @param url
     *            The url that this request will access.
     * @param data
     *            The data to be sent in the request
     * @return
     * @throws URISyntaxException
     */
    public HttpMessage createHttpPostRequest(String a_url,
                                             Map<String, String> a_headers, byte[] a_data)
            throws URISyntaxException {
        Log.d(TAG, "Using HttpPost");
        HttpPost t_HttpMethod = new HttpPost(a_url);
        t_HttpMethod.setEntity(new ByteArrayEntity(a_data));
        if (a_headers != null) {
            for (final String key : a_headers.keySet()) {
                t_HttpMethod.addHeader(key, a_headers.get(key));
            }
            if (mCookieDomain != null) {
                final CookieManager cookieManager = CookieManager.getInstance();
                t_HttpMethod.addHeader("Cookie",
                        cookieManager.getCookie(this.mCookieDomain));
            }
        }
        return t_HttpMethod;
    }

    /**
     * Create a new HttpPut uri request and submit for execution.
     *
     * @param url
     *            The url that this request will access.
     * @param headers
     *            The additional headers to send with the request.
     * @return
     * @throws URISyntaxException
     */

    public HttpMessage createHttpPutRequest(String a_url,
                                            Map<String, String> a_headers, String a_Data)
            throws URISyntaxException, UnsupportedEncodingException {
        HttpPut t_HttpMethod = new HttpPut(a_url);
        t_HttpMethod.setEntity(new StringEntity(a_Data));

        if (a_headers != null) {
            for (final String key : a_headers.keySet()) {
                t_HttpMethod.addHeader(key, a_headers.get(key));
            }

            if (mCookieDomain != null) {
                final CookieManager cookieManager = CookieManager.getInstance();
                t_HttpMethod.addHeader("Cookie",
                        cookieManager.getCookie(this.mCookieDomain));
            }
        }
        return t_HttpMethod;
    }

    /**
     * Create a new http uri request based on the method parameter and submit
     * for execution.
     *
     * @param a_url
     *            The url that this request will access.
     * @param a_headers
     *            The additional headers to send with the request.
     * @param method
     *            The HTTP method to be used
     * @return
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     * @throws MethodNotSupportedException
     */

    public HttpMessage createUsingMethod(String a_url,
                                         Map<String, String> a_headers, String a_method)
            throws URISyntaxException, UnsupportedEncodingException,
            MethodNotSupportedException {
        if (a_method.equalsIgnoreCase("get")) {
            return createHttpGetRequest(a_url, a_headers);
        } else if (a_method.equalsIgnoreCase("delete")) {
            return createHttpDeleteRequest(a_url, a_headers);
        }

        throw new MethodNotSupportedException("Method " + a_method
                + "not allowed");
    }

    public HttpMessage createUsingMethod(String a_url,
                                         Map<String, String> a_headers, String a_params, String a_method)
            throws URISyntaxException, UnsupportedEncodingException,
            MethodNotSupportedException {
        if (a_method.equalsIgnoreCase("post")) {
            return createHttpPostRequest(a_url, a_headers, a_params.getBytes());
        } else if (a_method.equalsIgnoreCase("put")) {
            return createHttpPutRequest(a_url, a_headers, a_params);
        } else if (a_method.equalsIgnoreCase("get")) {
            return createHttpGetRequest(a_url, a_headers);
        }

        throw new MethodNotSupportedException("Method " + a_method
                + "not allowed");
    }

    public HttpMessage createUsingMultiPartMethod(String a_url,
                                                  Map<String, String> a_headers, String a_params, String a_method)
            throws URISyntaxException, UnsupportedEncodingException,
            MethodNotSupportedException {
        if (a_method.equalsIgnoreCase("post")) {
            return createHttpPostRequest(a_url, a_headers, a_params.getBytes());
        } else if (a_method.equalsIgnoreCase("put")) {
            return createHttpPutRequest(a_url, a_headers, a_params);
        }
        throw new MethodNotSupportedException("Method " + a_method
                + "not allowed");
    }
}