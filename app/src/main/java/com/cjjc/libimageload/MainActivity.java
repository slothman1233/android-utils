package com.cjjc.libimageload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.cjjc.lib_imgload.imgLoad.IImgLoad;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    IImgLoad iImgLoad;

    private String url = "https://img2.baidu.com/it/u=1814268193,3619863984&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1663002000&t=6d09846a6862096d22b8f4cba4f8ce4a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ivImg = findViewById(R.id.iv_img);

        iImgLoad.showImg(this, url, ivImg);
//        ImgLoad.init(new GlideImg());
//        ImgLoad.getInstance().showImg(this, url, ivImg);
    }

}