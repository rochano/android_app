package com.rochano.myapplication.model;

import java.io.Serializable;

/**
 * Created by rochano-pc on 3/2/2017.
 */

public class Shop implements Serializable {
    private String shopNameJp;
    private String shopNameEn;
    private String imageLogo;

    public Shop(String shopNameJp, String shopNameEn, String imageLogo) {
        super();
        this.setShopNameJp(shopNameJp);
        this.setShopNameEn(shopNameEn);
        this.setImageLogo(imageLogo);
    }

    public String getShopNameJp() {
        return shopNameJp;
    }

    public void setShopNameJp(String shopNameJp) {
        this.shopNameJp = shopNameJp;
    }

    public String getShopNameEn() {
        return shopNameEn;
    }

    public void setShopNameEn(String shopNameEn) {
        this.shopNameEn = shopNameEn;
    }

    public String getImageLogo() {
        return imageLogo;
    }

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }
}
