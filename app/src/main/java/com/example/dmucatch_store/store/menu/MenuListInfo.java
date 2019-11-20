package com.example.dmucatch_store.store.menu;

public class MenuListInfo {

    String menuSeq[];
    String menuNm[];
    String menuInfo[];
    String menuPrice[];

    public MenuListInfo(int size){
        this.menuSeq = new String[size];
        this.menuNm = new String[size];
        this.menuInfo = new String[size];
        this.menuPrice = new String[size];
    }

    public String getMenuSeq(int idx) {
        return menuSeq[idx];
    }

    public void setMenuSeq(String value, int idx) {
        this.menuSeq[idx] = value;
    }

    public String getMenuNm(int idx) {
        return menuNm[idx];
    }

    public void setMenuNm(String value, int idx) {
        this.menuNm[idx] = value;
    }

    public String getMenuInfo(int idx) {
        return menuInfo[idx];
    }

    public void setMenuInfo(String value, int idx) {
        this.menuInfo[idx] = value;
    }

    public String getMenuPrice(int idx) {
        return menuPrice[idx];
    }

    public void setMenuPrice(String value, int idx) {
        this.menuPrice[idx] = value;
    }

}
