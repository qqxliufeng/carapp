package com.android.ql.lf.carapp.data;

import java.util.List;

/**
 * Created by lf on 18.3.23.
 *
 * @author lf on 18.3.23
 */

public class GoodsBean {

    private String product_id;
    private String product_price;
    private String product_time;
    private String product_sv;
    private String product_name;
    private String product_ms;
    private String product_content;
    private String product_collect;
    private List<String> product_pic;

    public String getProduct_collect() {
        return product_collect;
    }

    public void setProduct_collect(String product_collect) {
        this.product_collect = product_collect;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_time() {
        return product_time;
    }

    public void setProduct_time(String product_time) {
        this.product_time = product_time;
    }

    public String getProduct_sv() {
        return product_sv;
    }

    public void setProduct_sv(String product_sv) {
        this.product_sv = product_sv;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_ms() {
        return product_ms;
    }

    public void setProduct_ms(String product_ms) {
        this.product_ms = product_ms;
    }

    public String getProduct_content() {
        return product_content;
    }

    public void setProduct_content(String product_content) {
        this.product_content = product_content;
    }

    public List<String> getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(List<String> product_pic) {
        this.product_pic = product_pic;
    }
}
