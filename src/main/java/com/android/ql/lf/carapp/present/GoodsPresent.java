package com.android.ql.lf.carapp.present;

import com.android.ql.lf.carapp.data.RefreshData;
import com.android.ql.lf.carapp.ui.fragments.bottom.MainMallFragment;
import com.android.ql.lf.carapp.utils.RxBus;

/**
 * Created by lf on 18.3.29.
 *
 * @author lf on 18.3.29
 */

public class GoodsPresent {

    /**
     * 通知刷新商品状态
     */
    public static void notifyRefreshGoodsStatus() {
        RefreshData refreshData = RefreshData.INSTANCE;
        refreshData.setRefresh(true);
        refreshData.setAny(MainMallFragment.Companion.getREFRESH_COLLECTION_STATUS_FLAG());
        RxBus.getDefault().post(RefreshData.INSTANCE);
    }

}
