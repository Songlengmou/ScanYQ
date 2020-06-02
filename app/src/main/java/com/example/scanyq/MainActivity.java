package com.example.scanyq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

/**
 * @author Song
 * desc: 无需焦点 扫描器返回结果
 */
public class MainActivity extends AppCompatActivity {
    private TextView deviceText;

    private ScanKeyManager scanKeyManager;
    /**
     * 是否是输入状态（输入时扫码监听不拦截）
     */
    private boolean isInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceText = findViewById(R.id.tv_scan);
        onKeyBoardListener();
        //拦截扫码器回调,获取扫码内容
        scanKeyManager = new ScanKeyManager(new ScanKeyManager.OnScanValueListener() {
            @Override
            public void onScanValue(String value) {
                Log.e("ScanValue", value);
                deviceText.setText(value);
            }
        });
    }

    private void onKeyBoardListener() {
        SoftKeyBoardListener.setListener(MainActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                isInput = true;
                Log.e("软键盘", "键盘显示 高度" + height);
            }

            @Override
            public void keyBoardHide(int height) {
                isInput = false;
                Log.e("软键盘", "键盘隐藏 高度" + height);
            }
        });
    }

    /**
     * 监听键盘事件,除了返回事件都将它拦截,使用我们自定义的拦截器处理该事件
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK && !isInput) {
            scanKeyManager.analysisKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
