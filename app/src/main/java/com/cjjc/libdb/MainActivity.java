package com.cjjc.libdb;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cjjc.lib_db.sql.BaseDao;
import com.cjjc.lib_db.sql.BaseDaoFactory;
import com.cjjc.lib_db.sql.IBaseDao;
import com.cjjc.lib_db.sql.bean.TbUser;
import com.cjjc.lib_db.sql.update.UpdateManager;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private IBaseDao<TbUser> userDao;
    private UpdateManager instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission(this);

        instance = UpdateManager.getInstance();
    }

    public static boolean checkPermission(
            Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
        return false;
    }

    public void login(View view) {
        //初始化总数据库
        userDao = BaseDaoFactory.getInstance().getUserDao(BaseDao.class, TbUser.class);

        //向总数据库插入用户信息
        String ids = ((EditText) (findViewById(R.id.ids))).getText().toString();
        String name = ((EditText) (findViewById(R.id.name))).getText().toString();
        String password = ((EditText) (findViewById(R.id.password))).getText().toString();
        userDao.insert(new TbUser(ids, name, password, 1));
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    public void add(View view) {
        //插入个人信息
        BaseDao<Photo> photoDao = BaseDaoFactory.getInstance().getAppDao(Photo.class);
        for (int i = 0; i < 100; i++) {
            photoDao.insert(new Photo("2021-7-12", "测试少时诵诗书" + i + ".jpg"));
        }
    }

    public void sel(View view) {
        BaseDao<Photo> photoDao = BaseDaoFactory.getInstance().getAppDao(Photo.class);
        List<Photo> query = photoDao.query(new Photo());
        Log.e("=_=","query-->"+query.size());
//        Log.e("=_=","query-->"+query.get(0).getPath());
    }

    public void del(View view) {
        BaseDao<Photo> photoDao = BaseDaoFactory.getInstance().getAppDao(Photo.class);
        int delete = photoDao.delete(new Photo("2021-7-12", "测试少时诵诗书111.jpg"));
        Log.e("=_=","delete-->"+delete);
    }

    public void upd(View view) {
        BaseDao<Photo> photoDao = BaseDaoFactory.getInstance().getAppDao(Photo.class);
        int update = photoDao.update(new Photo("2021-7-12", "测试少时诵诗书111.jpg"), new Photo("2021-7-12", "测试少时诵诗书0.jpg"));
        Log.e("=_=","update-->"+update);
    }


    public void dowloadApk(View view) {
        FileUtil.saveVersionInfo(this, "1.5");
    }

    //升级数据库
    public void update(View view) {
        instance.startUpdateDb(this,"assets/updateXml.xml");
    }

    public void back(View view) {
//        updateManager.rollBack(false);
    }
}