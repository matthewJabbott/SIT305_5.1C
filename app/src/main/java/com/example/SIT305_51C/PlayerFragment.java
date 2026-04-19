package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PlayerFragment extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        webView = v.findViewById(R.id.webViewFullScreen);

        // Configure WebView Settings
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        // Critical: Set WebChromeClient to enable the Fullscreen button functionality
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // Safe argument retrieval
        Bundle args = getArguments();
        if (args != null && args.containsKey("video_url")) {
            String url = args.getString("video_url");
            if (url != null) {
                playVideo(url);
            }
        } else {
            Toast.makeText(getContext(), "No video URL provided", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void playVideo(String url) {
        String videoId = "";

        // Parsing for standard and shortened URLs
        try {
            if (url.contains("v=")) {
                videoId = url.split("v=")[1].split("&")[0];
            } else if (url.contains("youtu.be/")) {
                videoId = url.split("youtu.be/")[1].split("\\?")[0];
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error parsing YouTube link", Toast.LENGTH_SHORT).show();
            return;
        }

        if (videoId.isEmpty()) {
            Toast.makeText(getContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // HTML wrapper with black background to prevent white flashes
        String html = "<html><body style='margin:0;padding:0;background-color:black;'>" +
                "<iframe width=\"100%\" height=\"100%\" " +
                "src=\"https://www.youtube.com/embed/" + videoId + "\" " +
                "frameborder=\"0\" allowfullscreen></iframe></body></html>";

        webView.loadData(html, "text/html", "utf-8");
    }

    // Lifecycle management to prevent "ghost audio"
    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webView != null) {
            webView.destroy();
        }
    }
}