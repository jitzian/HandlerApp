package examples.android.mac.com.org.handlerapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

/***
 * How to update UI
 * 1. With threads thru the use of View.post
 * new Thread(new Runnable(){...})
 *
 * 2. With threads thru the use of runOnUIThread
 *
 * 3. Thru the use of handlers
 * **/

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getName();

    Button mButton;
    EditText mEditText1, mEditText2, mEditText3;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        mButton = (Button) findViewById(R.id.mButton);
        mEditText1 = (EditText) findViewById(R.id.mEditText1);
        mEditText2 = (EditText) findViewById(R.id.mEditText2);
        mEditText3 = (EditText) findViewById(R.id.mEditText3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Option #1
                        mEditText1.post(new Runnable() {
                            @Override
                            public void run() {
                                mEditText1.setText("New Text");
                            }
                        });
                        //Option #2
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mEditText2.setText("New Text");
                            }
                        });
                        //Option #3
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("newText", "New Text");
                        message.setData(bundle);
                        mHandler.sendMessageDelayed(message, 3000);

                    }
                }).start();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "handleMessage");
            Bundle bundle = msg.getData();
            mEditText3.setText(bundle.get("newText").toString());
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
