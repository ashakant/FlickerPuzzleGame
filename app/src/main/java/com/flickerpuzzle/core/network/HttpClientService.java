package com.flickerpuzzle.core.network;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.flickerpuzzle.component.api.ASDKError;
import com.flickerpuzzle.component.api.ResponseObject;
import com.flickerpuzzle.component.cache.cacheverifier.ImageData;
import com.flickerpuzzle.component.cache.cacheverifier.ImageFileCacheManager;

public class HttpClientService implements Runnable
{
    /**
     * Main Http client that execute/submit the request
     */
    private HttpClient mHttpClient;
    /**
     * The factory used to generate Http requests for this service
     */
    private final HttpMessageFactory mHttpMessageFactory;
    /**
     * Http message object
     */
    private HttpMessage mHttpMessage ;
    /**
     * A callback to listen for response from server
     */
    private IHttpResponseListener  mIHttpResponseListener;
    /**
     *  The hash keys holder
     */
    //	public static ArrayList<Integer> hashkeys = new ArrayList<Integer>();
    /**
     * HttpResponse response listener
     */
    private HttpResponse mResponse ;
    /**
     * Construct the HttpClientService
     *
     * @param poolSize
     *            The number of threads allowed to exist at once.
     * @param cookieDomain
     *            I'm not quite sure what I'm doing with this. figure it out.
     */
    protected Context mContext;
    /**
     * Construct the HttpClientService
     *
     * @param poolSize
     *            The number of threads allowed to exist at once.
     * @param cookieDomain
     *            I'm not quite sure what I'm doing with this. figure it out.
     */
    protected String  mUrl;
    /**
     * Construct the HttpClientService
     *
     * @param poolSize
     *            The number of threads allowed to exist at once.
     * @param cookieDomain
     *            I'm not quite sure what I'm doing with this. figure it out.
     */

    public HttpClientService(Context a_context,String a_cookieDomain) {
        this.mContext=a_context ;

        HttpParams httpParameters = new BasicHttpParams();
        /**
         * Set the timeout in milliseconds until a connection is established.
         */
        HttpConnectionParams.setConnectionTimeout(httpParameters, 100000);
        /**
         * Set the default socket timeout (SO_TIMEOUT) in milliseconds which is
         * the timeout for waiting for data.
         */
        HttpConnectionParams.setSoTimeout(httpParameters, 100000);
        /**
         * Thread Safe Client Connection Manager to be added to the
         * DefaultHttpClient to enable MultiThreaded functioning of the Client.
         */
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        /**
         * Use the client defaults and create a client. this.mHttpClient =
         * createHttpClient();//new DefaultHttpClient() ; Client made using the
         * Connection Manager. Safe across threads
         */
        //this.mHttpClient = new DefaultHttpClient(cm, httpParameters);
        this.mHttpClient = new DefaultHttpClient();
        /**
         * Handles generating HttpRequests.
         */
        this.mHttpMessageFactory = new HttpMessageFactory(a_cookieDomain);
    }

    /**
     * Execute an http request SYNCHRONOUSLY, calling the runnable after. Right
     * now this is limited in that you can only pass one value per key in the
     * http post. (The Map is the limiter).
     *
     * @param url
     *            The url to access
     * @param data
     *            The POST parameters to pass to this request.
     * @throws URISyntaxException
     */
    @Override
    public void finalize() {
        Log.e("HttpClientService", "finalize -- shutdownNow()");
        /**SDKExecutorService.getInstance().shutdownNow();*/
    }
    /**
     * Put an http request on the queue to be executed ASYNCHRONOUSLY. Upon
     * completion of the http request, a runnable will be called. The Http
     * request is created based on parameter method
     *
     * @param url
     *            The url to access.
     * @param The
     *            additional header parameters to pass to this request.
     * @param The
     *            data in String format to pass to this request.
     * @param responseRunnable
     *            The runnable to execute up on http request completion.
     * @param method
     *            The Http request is created using method specified
     * @throws URISyntaxException
     */
    public void submitRequest(String a_url, Map<String, String> a_params,
                              String data,String a_method,IHttpResponseListener a_responseRunnable)
            throws URISyntaxException,
            MethodNotSupportedException {
        try {
            this.mUrl=a_url;
            this.mHttpMessage = this.mHttpMessageFactory.createUsingMethod(this.mUrl,
                    a_params, data, a_method);
            this.mIHttpResponseListener=a_responseRunnable ;
        } catch (Exception e) {
        }
    }
    /**
     *  get called from OS HTTP layer as callback
     *  @param void
     *  @return - HttpResponse from HTTP layer
     */
    @Override
    public void run() {
        try{
            /**
             * Submit request to OS HTTP Layer
             */
            mResponse= this.mHttpClient
                    .execute((HttpUriRequest) this.mHttpMessage) ;
            /**
             * Get Http status code
             */
            final int statusCode = mResponse.getStatusLine().getStatusCode() ;
            if (this.mIHttpResponseListener != null && statusCode==HttpStatus.SC_OK){
                /**
                 * Get all header here
                 */
                final HeaderIterator t_HeaderIterator = mResponse.headerIterator();
                final HashMap<String, String> t_HeaderMap = new HashMap<String, String>() ;
                while (t_HeaderIterator.hasNext()) {
                    Header t_ResponneHeader = (Header) t_HeaderIterator.next() ;
                    t_HeaderMap.put(t_ResponneHeader.getName(),
                            t_ResponneHeader.getValue()) ;
                }
                /**
                 * Handle two content types text/html and image/png and audio/mpeg
                 */
                if(t_HeaderMap.get("Content-Type").equalsIgnoreCase("image/jpeg")){
                    HttpEntity entity = mResponse.getEntity();
                    InputStream in=entity.getContent() ;
                    ImageFileCacheManager fileCache=new ImageFileCacheManager(this.mContext) ;
                    File f=fileCache.getFile(this.mUrl);
                    OutputStream os = new FileOutputStream(f);
                    fileCache.CopyStream(in, os);
                    os.close();
                    final Bitmap bitmap=fileCache.decodeFile(f) ;
                    ImageData imgData=new ImageData(bitmap) ;
                    ResponseObject<ImageData>  resObject=new ResponseObject<ImageData>(imgData) ;
                    mIHttpResponseListener.onReceiveResponse(resObject);
                }
                if(t_HeaderMap.get("Content-Type").equalsIgnoreCase("text/javascript")){
                    final String resposneString=EntityUtils.toString(HttpClientService.this.mResponse.getEntity()) ;
                    ResponseObject<String>  resObject=new ResponseObject<String>(resposneString) ;
                    mIHttpResponseListener.onReceiveResponse(resObject);
                }
                else {
                    final String resposneString=EntityUtils.toString(HttpClientService.this.mResponse.getEntity()) ;
                    ResponseObject<String>  resObject=new ResponseObject<String>(resposneString) ;
                    mIHttpResponseListener.onReceiveResponse(resObject);
                }
                return ;
            }
        }
        catch (IOException e) {
            HttpClientService.this.mIHttpResponseListener.onHttpRequestError(new ASDKError(0,e.getMessage()));
        }
        catch (final Exception e) {
            HttpClientService.this.mIHttpResponseListener.onHttpRequestError(new ASDKError(0,e.getMessage()));
        }
        catch (Throwable ex){
            if(ex instanceof OutOfMemoryError)
            /**memoryCache.clear();
             callback to ui for releasing memory*/
                ;
        }
        return ;
    }
}