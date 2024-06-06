package cjjc.plugin.cjjcMvp.activity.mvp

fun mvpActivityKt(
    mRootPackageName:String?,  //全路径
    mApplicationId:String?,  //工程ID包名
    mActivityPackageName:String, //创建的文件名
    mPageName:String, //页面名
    mActivityLayoutName:String //布局文件名
)="""

package ${mRootPackageName}.${mActivityPackageName};

import com.alibaba.android.arouter.facade.annotation.Route;
import ${mApplicationId}.R;
import com.cjjc.lib_base_view.view.activity.BaseActivityMvp;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
@Route(path = "")
public class ${mPageName}Activity extends BaseActivityMvp<${mPageName}Presenter> implements ${mPageName}Interface.View {

    @Override
    protected int getLayout() {
        return R.layout.${mActivityLayoutName};
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initListener() {
    }

}

"""