package ru.denivip.android.widgets;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class OpenxAdView extends ViewGroup {
	
	private static final String ATTRS_NS = "http://denivip.ru/schemas/android/openx/0.1";
	
	private static final String LOGTAG = "OpenXAd";
	
	private static final String HTML_DOCUMENT_TEMPLATE = "<html><head><style>* {padding: 0; margin: 0; background-color: transparent;}</style></head>\n"
		+ "<body>%s</pre></body></html>";
	
	private static final String JS_TAG = "" 
		+ "<script type='text/javascript' src='%1$s?zoneid=%2$d&amp;charset=UTF-8"
		+ "&amp;cb=%4$d&amp;charset=UTF-8&amp;source=%3$s'></script>";

	private WebView webView;
	
	private String deliveryURL;
	
	private String jsTagURL = "ajs.php";
	
	private Integer zoneID;
	
	private boolean hasHTTPS = false;
	
	private String source;
	
	private Random prng = new Random();
	
	public OpenxAdView(Context context) {
		super(context);
		this.webView = new WebView(context);
		initWebView();
	}

	public OpenxAdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttributes(attrs);
		this.webView = new WebView(context, attrs, defStyle);
		initWebView();
	}

	public OpenxAdView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttributes(attrs);
		this.webView = new WebView(context, attrs);
		initWebView();
	}
	
	private void initAttributes(AttributeSet attrs) {
		Resources res = getContext().getResources();
		
		int delivery_url = attrs.getAttributeResourceValue(ATTRS_NS, "delivery_url", -1);
		if (delivery_url != -1) {
			this.deliveryURL = res.getString(delivery_url);
		}
		else {
			this.deliveryURL = attrs.getAttributeValue(ATTRS_NS, "delivery_url");
		}

		int js_tag_url_id = attrs.getAttributeResourceValue(ATTRS_NS, "js_tag_url", -1);
		if (js_tag_url_id != -1) {
			this.jsTagURL = res.getString(js_tag_url_id);
		}
		else {
			String js_tag_url = attrs.getAttributeValue(ATTRS_NS, "js_tag_url");
			if (js_tag_url != null) {
				this.jsTagURL = js_tag_url;
			}
		}

		int zone_id_rs = attrs.getAttributeResourceValue(ATTRS_NS, "zone_id", -1);
		if (zone_id_rs != -1) {
			this.zoneID = new Integer(res.getInteger(zone_id_rs));
		}
		else {
			int zone_id = attrs.getAttributeIntValue(ATTRS_NS, "zone_id", -1);
			if (zone_id != -1) {
				this.zoneID = new Integer(zone_id);
			}
		}

		int has_https = attrs.getAttributeResourceValue(ATTRS_NS, "has_https", -1);
		if (has_https != -1) {
			this.hasHTTPS = res.getBoolean(has_https);
		}
		else {
			this.hasHTTPS = attrs.getAttributeBooleanValue(ATTRS_NS, "has_https", false);
		}

		int source_id = attrs.getAttributeResourceValue(ATTRS_NS, "source", -1);
		if (source_id != -1) {
			this.source = res.getString(source_id);
		}
		else {
			this.source = attrs.getAttributeValue(ATTRS_NS, "source");
		}
	}
	
	private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setPluginsEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        
        webView.setBackgroundColor(0x00000000); // transparent
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebChromeClient(new OpenXAdWebChromeClient());

        addView(webView);
	}
	
	protected String getZoneTemplate(int zoneID) {
		try {
			String zoneTag = String.format(JS_TAG, 
					(hasHTTPS ? "https://" : "http://") + deliveryURL + '/' + jsTagURL, 
					zoneID,
					source == null ? "" : URLEncoder.encode(source, "utf-8"),
					prng.nextLong());
			String raw = String.format(HTML_DOCUMENT_TEMPLATE, zoneTag);
			return raw;
		}
		catch (UnsupportedEncodingException e) {
			Log.wtf(LOGTAG, "UTF-8 not supported?!", e);
		}

		return null;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		webView.layout(left, top, right, bottom);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
        load();
	}

	public void load() {
		if (zoneID != null) {
			load(zoneID);
		}
		else {
			Log.w(LOGTAG, "zoneID is empty");
		}
	}
	
	public void load(int zoneID) {
		// check required parameters
		if (deliveryURL != null) {
	        webView.loadDataWithBaseURL(null, getZoneTemplate(zoneID), "text/html", "utf-8", null);
		}
		else {
			Log.w(LOGTAG, "deliveryURL is empty");
		}
	}

	public String getDeliveryURL() {
		return deliveryURL;
	}

	public void setDeliveryURL(String deliveryURL) {
		this.deliveryURL = deliveryURL;
	}

	public String getJsTagURL() {
		return jsTagURL;
	}

	public void setJsTagURL(String jsTagURL) {
		this.jsTagURL = jsTagURL;
	}

	public Integer getZoneID() {
		return zoneID;
	}

	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}

	public boolean hasHTTPS() {
		return hasHTTPS;
	}

	public void setHasHTTPS(boolean hasHTTPS) {
		this.hasHTTPS = hasHTTPS;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
