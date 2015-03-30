
package com.suwonsmartapp.hello.parsing_xml;

/**
 * 데이터 출처 view-source:http://feeds2.feedburner.com/androidpub
 */
public class AndroidPubInfo {
    String title;
    String url;

    public AndroidPubInfo() {
        title = "";
        url = "";
    }

    public AndroidPubInfo(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
