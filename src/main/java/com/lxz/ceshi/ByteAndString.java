package com.lxz.ceshi;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ByteAndString {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String inputStr = "你好, Hello";
        String charset = "UTF-8";
        String outputStr = null;
        byte[] firstLayerByteArray = null;
        // default
        firstLayerByteArray = inputStr.getBytes();
        outputStr = new String(firstLayerByteArray);
        System.out.println("default:");
        System.out.println(Arrays.toString(firstLayerByteArray));
        System.out.println(outputStr);
        System.out.println("-------------");
        // UTF-8
        charset = "UTF-8";
        firstLayerByteArray = inputStr.getBytes(charset);
        outputStr = new String(firstLayerByteArray, charset);
        System.out.println(charset + ":");
        System.out.println(Arrays.toString(firstLayerByteArray));
        System.out.println(outputStr);
        System.out.println("-------------");
        // GBK
        charset = "GBK";
        firstLayerByteArray = inputStr.getBytes(charset);
        outputStr = new String(firstLayerByteArray, charset);
        System.out.println(charset + ":");
        System.out.println(Arrays.toString(firstLayerByteArray));
        System.out.println(outputStr);
        System.out.println("-------------");
        // GB2312
        charset = "GB2312";
        firstLayerByteArray = inputStr.getBytes(charset);
        outputStr = new String(firstLayerByteArray, charset);
        System.out.println(charset + ":");
        System.out.println(Arrays.toString(firstLayerByteArray));
        System.out.println(outputStr);
        System.out.println("-------------");
        // ISO-8859-1
        charset = "ISO-8859-1";
        firstLayerByteArray = inputStr.getBytes(charset);
        outputStr = new String(firstLayerByteArray, charset);
        System.out.println(charset + ":");
        System.out.println(Arrays.toString(firstLayerByteArray));
        System.out.println(outputStr);
        System.out.println("-------------");
    }
}
