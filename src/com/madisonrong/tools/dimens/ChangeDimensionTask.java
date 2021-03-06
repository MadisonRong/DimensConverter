/**
 * Project Name:TestFunction
 * File Name:ChangeDimension.java
 * Package Name:com.threshold.testfunction
 * Date:2015年9月21日下午2:12:17
 * Copyright (c) 2015,QuanXiang All Rights Reserved.
 */

package com.madisonrong.tools.dimens;

import java.io.*;
import java.math.BigDecimal;

/**
 * ClassName:ChangeDimension
 * Date:     2015年9月21日 下午2:12:17 
 * @author 黄守江, MadisonRong
 */
public class ChangeDimensionTask {

    private StringBuilder stringBuilder;
    private String sourceFileName;
    private String destFileFolder;
    private String dpiString;
    private float ratio;
    private int height, width, designWidth, designDpi;
    private float coefficient;
    private String firstLineStatement = "";

    public ChangeDimensionTask(String sourceFileName, String destFolder,
                               int designWidth, int designDpi, int height, int width, String dpi) {
        this.sourceFileName = sourceFileName;
        this.destFileFolder = destFolder;
        this.dpiString = dpi;
        stringBuilder = new StringBuilder();
        this.designWidth = designWidth;
        this.designDpi = designDpi;
        this.height = height;
        this.width = width;
        calculatorRatio();
    }


    private void calculatorRatio() {
        switch (dpiString) {
            case DpiName.LDPI:
                coefficient = 0.75f;
                firstLineStatement = "ldpi 120dpi";
                break;
            case DpiName.MDPI:
                coefficient = 1.0f;
                firstLineStatement = "mdpi 160dpi";
                break;
            case DpiName.HDPI:
                coefficient = 1.5f;
                firstLineStatement = "hdpi 240dpi";
                break;
            case DpiName.XHDPI:
                coefficient = 2.0f;
                firstLineStatement = "xhdpi 320dpi";
                break;
            case DpiName.XXHDPI:
                coefficient = 3.0f;
                firstLineStatement = "xxhdpi 480dpi";
                break;
            case DpiName.XXXHDPI:
                coefficient = 4.0f;
                firstLineStatement = "xxxhdpi 640dpi";
                break;
            default:
                String dpiNumber = dpiString.substring(0, dpiString.indexOf("dpi"));
                coefficient = Float.valueOf(dpiNumber) / 160;
                firstLineStatement = dpiString;
                break;
        }
        System.out.println("dpi: " + coefficient);
        this.ratio = coefficient / ((designDpi * width) / designWidth);
        System.out.println("计算出的系数：" + ratio);
    }


    public void execute() {
        readFileByLines();
        saveFile();
    }

    private void saveFile() {
        File file = new File(destFileFolder + File.separator + "dimens.xml");
        try (FileOutputStream fop = new FileOutputStream(file)) {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println(file.getAbsolutePath()+"   created successfully.");
                } else {
                    System.out.println("*** "+file.getAbsolutePath()+"  created FAILED !!!!");
                }
            } else {
                System.out.println(file.getAbsolutePath()+" already has a dimens.xml file.I will rewrite it.");
            }
            byte[] contentInBytes = stringBuilder.toString().getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("Done: " + destFileFolder + File.separator + "dimens.xml");
            System.out.println("------------------------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readFileByLines() {
        File file = new File(sourceFileName);
        BufferedReader reader = null;
        try {
            // 以行为单位读取文件内容，一次读一整行
            reader = new BufferedReader(new FileReader(file));
            String oneLine;
            // 一次读入一行，直到读入null为文件结束
            while ((oneLine = reader.readLine()) != null) {
                stringBuilder.append(updateDimension(oneLine));
                stringBuilder.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    System.out.println("error on read: "+e1);
                }
            }
        }
    }

    private String updateDimension(String oneLine) {
        if (oneLine.contains("Default screen margins")) {
            //说明是第一行备注 mdpi 160dpi 1136x640
            return "    <!-- " + firstLineStatement + " " + height + "x" + width + " -->";
        }
        String newTempString = updateDimension(oneLine, "dp");
        newTempString = updateDimension(newTempString, "dip");
        newTempString = updateDimension(newTempString, "sp");
        if (newTempString != null) return newTempString;
        return oneLine;
    }

    private String updateDimension(String oneLine, String keyword) {
        int dpDimension = oneLine.indexOf(keyword + "</dimen>");
        if (dpDimension > -1) {
            int begin = oneLine.indexOf("\">");
            int end = oneLine.indexOf("</");
            if (begin > 0 && end > 0 && end > begin) {
                String dimensionString = oneLine.substring(begin + 2, end);
                double dp = Double.valueOf(dimensionString.substring(0, dimensionString.indexOf(keyword)));
                BigDecimal newDimen = new BigDecimal(dp * ratio).setScale(2, BigDecimal.ROUND_HALF_UP);
                return oneLine.replace(dimensionString, newDimen + keyword);
            }
        }
        return oneLine;
    }


    private static final class DpiName {
        static final String LDPI = "ldpi";
        static final String MDPI = "mdpi";
        static final String HDPI = "hdpi";
        static final String XHDPI = "xhdpi";
        static final String XXHDPI = "xxhdpi";
        static final String XXXHDPI = "xxxhdpi";
    }


}

