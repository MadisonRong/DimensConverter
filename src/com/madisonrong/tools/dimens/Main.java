
package com.madisonrong.tools.dimens;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * ClassName:Main
 * Date:     2015年9月21日 下午4:56:36
 * @author 黄守江, MadisonRong
 */
public class Main {

    private String defaultDimensPath = "d:\\dimens.xml", resFolderPath = "D:\\res";
    private String designWidth = "864", designDpiString = "1";

    public static void main(String[] args) {
        new Main().commandLineMode(args);
    }

    public void commandLineMode(String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("-i")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入标准宽度");
                while (!isInteger(designWidth = scanner.next())) {
                    System.out.println("输入有误，宽度为正整数，请重新输入");
                }System.out.println("请输入标准 dpi 值");
                while (!isInteger(designDpiString = scanner.next())) {
                    System.out.println("输入有误，标准 dpi 值为正整数，请重新输入");
                }
                System.out.println("请输入1136x640 dimens.xml文件路径（例如 d:\\dimens.xml ）");
                while (!isFileExists(defaultDimensPath = scanner.next())) {
                    System.out.println("输入有误，dimens.xml文件不存在，请重新输入");
                }
                System.out.println("请输入res文件夹路径（例如 d:\\res ）");
                while (!isFileExists(resFolderPath = scanner.next())) {
                    System.out.println("输入有误，res文件夹不存在，请重新输入");
                }
                scanner.close();
                takeAction(defaultDimensPath, resFolderPath, designWidth, designDpiString);
            } else if (args[0].equalsIgnoreCase("-h")) {
                printHelpMethod();
            } else {
                printHelpMethod();
            }
        } else if (args.length > 1) {
            if (args.length % 2 == 0) {
                HashMap<String, String> argsMap = new HashMap<>();
                for (int i = 0; i < args.length - 1; i+=2) {
                    argsMap.put(args[i], args[i+1]);
                }
                Set<String> argsMapKeySet = argsMap.keySet();
                boolean hasError = false;
                for (String arg : argsMapKeySet) {
                    System.out.println(String.format("key: %s, value: %s", arg, argsMap.get(arg)));
                    switch (arg.toLowerCase()) {
                        case "-w":
                            String tempDesignWidth = argsMap.get(arg);
                            if (isInteger(tempDesignWidth)) {
                                designWidth = tempDesignWidth;
                            } else {
                                hasError = true;
                                System.out.println("给定的标准宽度不正确");
                            }
                            break;

                        case "-dpi":
                            String tempDesignDpiString = argsMap.get(arg);
                            if (isInteger(tempDesignDpiString)) {
                                designDpiString = tempDesignDpiString;
                            } else {
                                hasError = true;
                                System.out.println("给定的 DPI 值不正确");
                            }
                            break;

                        case "-s":
                            String tempDefaultDimensPath = argsMap.get(arg);
                            if (isFileExists(tempDefaultDimensPath)) {
                                defaultDimensPath = tempDefaultDimensPath;
                            } else {
                                hasError = true;
                                System.out.println("给定的dimens.xml文件不存在");
                            }
                            break;

                        case "-d":
                            String tempResFolderPath = argsMap.get(arg);
                            if (isFileExists(tempResFolderPath)) {
                                resFolderPath = tempResFolderPath;
                            } else {
                                hasError = true;
                                System.out.println("给定的res文件夹不存在");
                            }
                            break;
                    }
                    if (hasError) {
                        System.exit(0);
                    }
                }
                takeAction(defaultDimensPath, resFolderPath, designWidth, designDpiString);
            } else {
                System.out.println("命令有误");
                printHelpMethod();
            }
        } else {
            printHelpMethod();
        }
    }

    private void takeAction(String defaultDimensPath, String resFolderPath, String designWidth, String designDpiString) {
        System.out.println("");
        System.out.println("design width: " + designWidth);
        System.out.println("source: " + defaultDimensPath);
        System.out.println("destination: " + resFolderPath);
        System.out.println("");
        DimensionCalcManager.executeBatchTask(defaultDimensPath, resFolderPath,
                Integer.parseInt(designWidth), Integer.parseInt(designDpiString));
    }

    private void printHelpMethod() {
        System.out.println("使用方法： \n" +
                "-h 显示此帮助\n" +
                "-w [标准宽度/设计图宽度]\n" +
                "-dpi [设计图 dpi 值]\n" +
                "-s [dimens.xml文件路径]\n" +
                "-d [res文件夹路径]");
        System.exit(0);
    }

    private boolean isInteger(String width) {
        if (width.matches("\\d+")) {
            return true;
        }
        return false;
    }

    private boolean isFileExists(String path) {
        if (!isStringEmpty(path)) {
            File file = new File(path);
            return file.exists();
        }
        return false;
    }

    private boolean isStringEmpty(String str) {
        if (str != null && str.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        return str == null || str.length() == 0;
    }

}

