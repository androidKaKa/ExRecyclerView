package com.kale.wfalldemo.aaa.mode;

public class PhotoData implements IAdapterModel {

    public static final int FIRST = 1;
    public static final int Second = 2;
    
    public int favorite_count;

    public String msg;

    public Photo photo;

    @Override
    public int getDataTypeCount() {
        return 2;
    }

    @Override
    public int getDataType() {
        int type = 1+ (int) (Math.random() * 2);
        return type;
    }
}
