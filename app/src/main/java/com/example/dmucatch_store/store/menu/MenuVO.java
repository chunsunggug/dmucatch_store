package com.example.dmucatch_store.store.menu;

public class MenuVO {
   private int menu_seq[];
   private String menu_name[];
   private String menu_price[];
   private String menu_info[];
   private String menu_freeview[];

    public MenuVO() {  super();}
    public MenuVO(int size) {
        this.menu_seq = new int[size];
        this.menu_name = new String[size];
        this.menu_price = new String[size];
        this.menu_info = new String[size];
        this.menu_freeview = new String[size];
    }

    public int getMenu_seq(int idx) {
        return menu_seq[idx];
    }

    public void setMenu_seq(int menu_seq,int idx) {
            this.menu_seq[idx] =menu_seq;
    }

    public String getMenu_name(int idx) {
        return menu_name[idx];
    }

    public void setMenu_name(String menu_name,int idx) {
        this.menu_name[idx] = menu_name;
    }

    public String getMenu_price(int idx) {
        return menu_price[idx];
    }

    public void setMenu_price(String menu_price,int idx) {
        this.menu_price[idx] = menu_price;
    }

    public String getMenu_info(int idx) {
        return menu_info[idx];
    }

    public void setMenu_info(String menu_info,int idx) {
        this.menu_info[idx] = menu_info;
    }

    public String getMenu_freeview(int idx) {
        return menu_freeview[idx];
    }

    public void setMenu_freeview(String menu_freeview,int idx) {
        this.menu_freeview[idx] = menu_freeview;
    }
}
