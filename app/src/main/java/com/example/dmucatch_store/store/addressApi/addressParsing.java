package com.example.dmucatch_store.store.addressApi;

public class addressParsing {

    public static void parseGu(String add_orign){

        // 이전까지 자르기
        String target = "구 ";
        String[] addArray = add_orign.split(target);
        System.out.println("구로 짤라짐? " + addArray[0]);

        AddVo.par_newAdd = addArray[0];

    }


}
