package cjjc.plugin.cjjcMvp.libraryDemo.common.adapter

fun testAdapterKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};
import com.cjjc.lib_base_view.view.adapter.recycle.BaseRecycleListAdapter;
import com.cjjc.lib_base_view.view.adapter.recycle.BaseViewHolder;
import ${mRootPackageName}.bean.TestBean;

import java.util.List;


public class $mPageName extends BaseRecycleListAdapter<TestBean> {

    public TestAdapter(List<TestBean> list) {
        super(list);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void bindData(BaseViewHolder holder, TestBean bean, int position) {
    }
}  
                    
"""