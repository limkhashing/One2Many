package com.kslimweb.one2many.client;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kslimweb.one2many.BuildConfig;
import com.kslimweb.one2many.R;
import com.kslimweb.one2many.Utils;

import static com.kslimweb.one2many.client.ScanQRCodeActivity.SUBSCRIBE_TOPIC;

public class ClientTranslationActivity extends AppCompatActivity implements MaterialSpinner.OnItemSelectedListener, MaterialSpinner.OnTouchListener {

    private static final String TAG = ClientTranslationActivity.class.getSimpleName();
    private boolean isSpinnerTouched = false;
    TextView outputText;
    MaterialSpinner languageSpinner;
    ReceiveText receiveText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_translation);

        outputText = findViewById(R.id.output_text);
        languageSpinner = findViewById(R.id.language_spinner);

        if (BuildConfig.DEBUG) {
            SUBSCRIBE_TOPIC = "test-test";
        }

        setSpinnerItem();

        languageSpinner.setOnItemSelectedListener(this);
        languageSpinner.setOnTouchListener(this);

        // ReceiveText will automatically change the text view
        receiveText = new ReceiveText(outputText, languageSpinner, this.getApplicationContext());

        checkAndSubscribeTopic();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiveText,
                        new IntentFilter("MESSAGE"));
    }

    private void setSpinnerItem() {
        String [] languages = getResources().getStringArray(R.array.list_of_language);
        languageSpinner.setItems(languages);
    }

    private void checkAndSubscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TOPIC)
                .addOnCompleteListener(task -> {
                    String msg = "Subscribe to " + SUBSCRIBE_TOPIC;
                    if (!task.isSuccessful()) {
                        msg = "Failed Subscribed to " + SUBSCRIBE_TOPIC;
                    }
                    Log.d(TAG, msg);
                });
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        if (!isSpinnerTouched) {
            return;
        }

        String targetLanguage = getSpinnerSelectedLanguage();
        String originalText = outputText.getText().toString();
        Log.d(TAG, "onItemSelected Original Text: " + originalText);
        Log.d(TAG, "onItemSelected selectedLanguage: " + targetLanguage);

        new Utils(outputText, languageSpinner, this.getApplicationContext()).translateText(originalText, targetLanguage);
        isSpinnerTouched = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        isSpinnerTouched = true;
        return false;
    }

    String getSpinnerSelectedLanguage() {
        int languagePosition = languageSpinner.getSelectedIndex();
        Log.d(TAG, "getSpinnerSelectedLanguage Position: " + languagePosition);

        String languageCode = "";
        switch (languagePosition) {
            case 0:
                languageCode = "ar";
                break;
            case 1:
                languageCode = "zh-CN";
                break;
            case 2:
                languageCode = "en";
                break;
            case 3:
                languageCode = "fr";
                break;
            case 4:
                languageCode = "de";
                break;
            case 5:
                languageCode = "hi";
                break;
            case 6:
                languageCode = "it";
                break;
            case 7:
                languageCode = "ja";
                break;
            case 8:
                languageCode = "ko";
                break;
            case 9:
                languageCode = "ms";
                break;
            case 10:
                languageCode = "pa";
                break;
            case 11:
                languageCode = "ta";
                break;
            case 12:
                languageCode = "tl";
                break;
            case 13:
                languageCode = "th";
                break;
            case 14:
                languageCode = "vi";
                break;
        }
        return languageCode;
    }
}
