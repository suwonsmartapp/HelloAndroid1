
package com.suwonsmartapp.hello.parsing_xml;

import com.suwonsmartapp.hello.R;
import com.suwonsmartapp.hello.parsing_json.WebViewActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * API 정보 https://webservice.rakuten.co.jp/app/create
 */
public class ParsingActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = ParsingActivity.class.getSimpleName();
    private AndroidPubAdapter mAdapter;
    private ArrayList<AndroidPubInfo> mAndroidPubList;
    private ListView mListView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mListView.setAdapter(mAdapter);
        }
    };

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        mListView = (ListView) findViewById(R.id.lv_recipe);
        mListView.setOnItemClickListener(this);

        mAndroidPubList = new ArrayList<>();

        // 0. ProgressDialog 띄우기
        mProgressBar.setVisibility(View.VISIBLE);

        // 1. web에 데이터 요청. 무조건 Thread사용해야 함
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();

                String urlString = "http://feeds2.feedburner.com/androidpub";
                try {
                    URI url = new URI(urlString);

                    HttpGet httpGet = new HttpGet();
                    httpGet.setURI(url);

                    // 응답을 받는 객체
                    HttpResponse response = httpClient.execute(httpGet);
                    String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

                    // 2. 요청 받은 내용을 파싱
                    htmlParsing(responseString);

                    // final. ProgressDialog 를 dismiss()
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });

                } catch (URISyntaxException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                }

            }
        };

        thread.start();

    }

    private void htmlParsing(String responseString) {
        // html 파싱
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            boolean isItemTag = false;
            boolean isTitleTag = false;
            boolean isLinkTag = false;
            String title = "";
            String link = "";

            AndroidPubInfo androidPubInfo = null;

            // Log.d(TAG, responseString);
            xpp.setInput(new StringReader(responseString));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                } else if (eventType == XmlPullParser.START_TAG) {

                    // li 태그 내용 파싱
                    if (xpp.getName().equals("item")) {
                        isItemTag = true;
                        androidPubInfo = new AndroidPubInfo();
                    }
                    if (isItemTag && xpp.getName().equals("title")) {
                        isTitleTag = true;
                    }
                    if (isItemTag && xpp.getName().equals("link")) {
                        isLinkTag = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {

                    if (xpp.getName().equals("item")) {
                        mAndroidPubList.add(androidPubInfo);
                        isItemTag = false;
                    }
                    if (xpp.getName().equals("title")) {
                        isTitleTag = false;
                    }
                    if (xpp.getName().equals("link")) {
                        isLinkTag = false;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (isTitleTag) {
                        Log.d(TAG, "Text " + xpp.getText());
                        title = xpp.getText();
                        androidPubInfo.setTitle(title);
                    }
                    if (isLinkTag) {
                        Log.d(TAG, "Link " + xpp.getText());
                        link = xpp.getText();
                        androidPubInfo.setUrl(link);
                    }

                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {

        } catch (IOException e) {

        }

        // 3. 파싱한 데이터를 어댑터에 설정
        mAdapter = new AndroidPubAdapter(ParsingActivity.this, mAndroidPubList);

        // 4. 어댑터를 리스트 뷰에 설정 (UI 갱신)
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Toast.makeText(getApplicationContext(), "url : " +
        // mAndroidPubList.get(position).getUrl(),
        // Toast.LENGTH_SHORT).show();

        // url
        String url = mAndroidPubList.get(position).getUrl();

        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
        intent.putExtra("url", url); // url 을 intent 싫어서 activity를 호출
        startActivity(intent);
    }
}
