package ru.denivip.android.widgets;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

public class OpenXAdWebChromeClient extends WebChromeClient {
	@Override
	public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		Log.d("OpenxAd",
				consoleMessage.message() + " -- From line "
						+ consoleMessage.lineNumber() + " of "
						+ consoleMessage.sourceId());
	    return true;
	}
}
