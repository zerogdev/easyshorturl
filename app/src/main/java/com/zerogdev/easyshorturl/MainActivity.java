package com.zerogdev.easyshorturl;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerogdev.easyshorturl.data.ShortUrlData;
import com.zerogdev.easyshorturl.listener.ShortUrlCallBack;
import com.zerogdev.easyshorturl.util.DataManager;

public class MainActivity extends AppCompatActivity {

    private DataManager mDataManager;
    private EditText mEnterUrlEditText;
    private Button mShortenBtn;
    private ImageView mTextClearBtn;
    private TextView mVersionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mDataManager = new DataManager();

        mEnterUrlEditText = findViewById(R.id.enter_url);
        mShortenBtn = findViewById(R.id.shorten_btn);
        mShortenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enterUrl = mEnterUrlEditText.getText().toString();

                if (!TextUtils.isEmpty(enterUrl)) {
                    mDataManager.loadShortUrl(enterUrl, new ShortUrlCallBack() {
                        @Override
                        public void onShortUrlSuccess(ShortUrlData shortUrlData) {
                            share(shortUrlData.getUrl());
                        }

                        @Override
                        public void onShortUrlFail() {

                        }
                    });
                }
            }
        });
        mTextClearBtn = findViewById(R.id.text_clear_btn);
        mTextClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnterUrlEditText.getText().clear();
            }
        });
        mVersionTextView = findViewById(R.id.version_text);
        mVersionTextView.setText("ver "+BuildConfig.VERSION_NAME);

    }


    private void share(String url) {
        // 공유 인텐트
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);

        // "Copy link to clipboard" Intent
        Intent clipboardIntent = CopyToClipboardActivity.createCopyToClipboardIntent(this, url);

        // Create Chooser Intent with "Copy link to clipboard" option
        String title = getString(R.string.share) + " " + url;
        Intent chooserIntent = Intent.createChooser(shareIntent, title);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { clipboardIntent });

        startActivity(chooserIntent);
    }
}
