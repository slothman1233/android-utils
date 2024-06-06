package cjjc.plugin.cjjcMvp.fragment.mvp

fun mvpFragmentKt(
    mRootPackageName:String?,  //全路径
    mApplicationId:String?,  //工程ID包名
    mFragmentPackageName:String, //创建的文件名
    mPageName:String, //页面名
    mFragmentLayoutName:String //布局文件名
)="""

package ${mRootPackageName}.${mFragmentPackageName};

import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.cjjc.lib_base_view.view.fragment.BaseFragmentMvp;
import ${mApplicationId}.R;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "")
public class ${mPageName}Fragment extends BaseFragmentMvp<${mPageName}Presenter> implements ${mPageName}Interface.View{

    @Override
    protected int getViewId() {
        return R.layout.${mFragmentLayoutName};
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }
    
    @Override
    protected void initListener() {
        
    }
}

"""