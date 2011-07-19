package ru.denivip.android.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class OpenxAdView extends ViewGroup {
	
	private static final String HTML_DOCUMENT_TEMPLATE = "<html><head><style>* {padding: 0; margin: 0; background-color: transparent;}</style></head>\n"
		+ "<body>%s</pre></body></html>";
	
	private static final String JS_TAG = "" 
		+ "<!--/* OpenX Javascript Tag v2.8.8-rc6 */-->\n"
		+ "<script type='text/javascript'><!--//<![CDATA[\n"
		+ "   var m3_u = (location.protocol=='https:'?'https://openx.local/openx-now/www/delivery/ajs.php':'http://openx.local/openx-now/www/delivery/ajs.php');\n"
		+ "   var m3_r = Math.floor(Math.random()*99999999999);\n"
		+ "   if (!document.MAX_used) document.MAX_used = ',';\n"
		+ "   document.write (\"<scr\"+\"ipt type='text/javascript' src='\"+m3_u);\n"
		+ "   document.write (\"?zoneid=%d&amp;charset=UTF-8\");\n"
		+ "   document.write ('&amp;cb=' + m3_r);\n"
		+ "   if (document.MAX_used != ',') document.write (\"&amp;exclude=\" + document.MAX_used);\n"
		+ "   document.write ('&amp;charset=UTF-8');\n"
		+ "   document.write (\"&amp;loc=\" + escape(window.location));\n"
		+ "   if (document.referrer) document.write (\"&amp;referer=\" + escape(document.referrer));\n"
		+ "   if (document.context) document.write (\"&context=\" + escape(document.context));\n"
		+ "   if (document.mmm_fo) document.write (\"&amp;mmm_fo=1\");\n"
		+ "   document.write (\"'><\\/scr\"+\"ipt>\");\n"
		+ "//]]>--></script>\n";

	private WebView webView;
	
	public OpenxAdView(Context context) {
		super(context);
		this.webView = new WebView(context);
		initWebView();
	}

	public OpenxAdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.webView = new WebView(context, attrs, defStyle);
		initWebView();
	}

	public OpenxAdView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.webView = new WebView(context, attrs);
		initWebView();
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
        
        webView.loadDataWithBaseURL(null, getZoneTemplate(3), "text/html", "utf-8", null);
        
        addView(webView);
	}
	
	protected String getZoneTemplate(int zone) {
		String zoneTag = String.format(JS_TAG, zone);
		String raw = String.format(HTML_DOCUMENT_TEMPLATE, zoneTag);
		return raw;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		webView.layout(left, top, right, bottom);
	}
}
