package com.android.ql.lf.carapp.present

import com.android.ql.lf.carapp.data.ShoppingCarItemBean

/**
 * Created by lf on 18.3.15.
 * @author lf on 18.3.15
 */
class ShoppingCarPresent(var shopItems:ArrayList<ShoppingCarItemBean>) {

    fun calculateAllPrice():Float{
        var allMoney = 0.0f
        shopItems.forEach {
            if (it.isSelector) {
                allMoney += (it.shopcart_price.toFloat() * it.shopcart_num.toInt())
            }
        }
        return allMoney
    }

    fun allItemSelects():Pair<Boolean,Float>{
        shopItems.forEach {
            it.isSelector = true
        }
        return Pair(true,calculateAllPrice())
    }
}