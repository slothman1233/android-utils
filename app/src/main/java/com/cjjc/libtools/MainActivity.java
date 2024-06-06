package com.cjjc.libtools;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cjjc.lib_tools.util.PermissionsUtil;
import com.cjjc.lib_tools.util.toast.ToastEnum;
import com.cjjc.lib_tools.util.toast.ToastUtil;
import com.cjjc.lib_tools.zxing.activity.CaptureActivity;
import com.cjjc.lib_tools.zxing.activity.CodeUtils;


public class MainActivity extends AppCompatActivity {
    private TextView tvCode;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        tvCode = findViewById(R.id.tv_code);
        ToastUtil.getInstance().init(getApplication());
        button.setOnClickListener(v -> PermissionsUtil.requestPermissions(MainActivity.this, (permissions, all) -> {
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    launcher2.launch(intent);
                },
                Manifest.permission.CAMERA
        ));

    }

    /**
     * StartActivityForResult 扫描二维码返回launcher
     *
     * @param
     * @return
     */
    private final ActivityResultLauncher<Intent> launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        switch (result.getResultCode()) {
            case RESULT_OK:
                if (result.getData() != null) {
                    String str = result.getData().getStringExtra(CodeUtils.RESULT_STRING);
                    ToastUtil.getInstance().showToast(ToastEnum.SUCCESS, "扫码成功:" + str);
                    tvCode.setText("扫码成功:" + str);
                }
                break;
            case Activity.RESULT_CANCELED:
                if (result.getData() != null) {
                    ToastUtil.getInstance().showToast(ToastEnum.ERROR, "解析二维码失败");
                }
                break;
            default:
                break;
        }
    });

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}