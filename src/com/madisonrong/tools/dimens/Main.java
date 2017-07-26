
package com.madisonrong.tools.dimens;

import java.io.File;
import java.util.Scanner;

/**
 * ClassName:Main
 * Function: TODO ADD FUNCTION.
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月21日 下午4:56:36
 *
 * @author 黄守江, MadisonRong
 * @see
 * @since JDK 1.8
 */
public class Main {

    public static void main(String[] args) {
        String defaultDimensPath = "d:\\dimens.xml", resFolderPath = "D:\\res";
        String designWidth = "864";
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("-i")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入标准宽度");
                while (!isInteger(designWidth = scanner.next())) {
                    System.out.println("输入有误，宽度为正整数，请重新输入");
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
            } else if (args[0].equalsIgnoreCase("-h")) {
                System.out.println("使用方法： \n" +
                        "无参数 使用默认值进行计算转换\n" +
                        "-h 显示此帮助\n" +
                        "-w [标准宽度/设计图宽度] -s [dimens.xml文件路径] -d [res文件夹路径]");
                System.exit(0);
            } else {
                System.out.println("未知的命令：" + args[0]);
                System.exit(0);
            }
        } else if (args.length == 6) {
            if (args[0].equalsIgnoreCase("-w") && isInteger(args[1])) {
                designWidth = args[1];
            } else {
                System.out.println(args[0] + "命令未知或 " + args[1] + " 给定的标准宽度不正确。将使用默认值 864");
            }
            if (args[2].equalsIgnoreCase("-s") && isFileExists(args[3])) {
                defaultDimensPath = args[3];
            } else {
                System.out.println(args[2] + "命令未知或 " + args[3] + " 给定的dimens.xml文件不存在。将使用默认值 d:\\dimens.xml");
            }
            if (args[4].equalsIgnoreCase("-d")&&isFileExists(args[5])) {
                resFolderPath = args[5];
            } else {
                System.out.println(args[4] + "命令未知或 " +args[5] + " 给定的res文件夹不存在。将使用默认值 d:\\res");
            }
        } else {
            System.out.println("\n******************\n" +
                    "使用默认值来转换计算: -w 864 -s d:\\dimens.xml -d d:\\res \n" +
                    "******************\n");
        }
        System.out.println("");
        System.out.println("design width: " + designWidth);
        System.out.println("source: " + defaultDimensPath);
        System.out.println("destination: " + resFolderPath);
        System.out.println("");
        DimensionCalcManager.executeBatchTask(defaultDimensPath, resFolderPath, Integer.parseInt(designWidth));
    }

    private static boolean isInteger(String width) {
        if (width.matches("\\d+")) {
            return true;
        }
        return false;
    }

    private static boolean isFileExists(String path) {
        if (!isStringEmpty(path)) {
            File file = new File(path);
            return file.exists();
        }
        return false;
    }

    private static boolean isStringEmpty(String str) {
        if (str != null && str.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        return str == null || str.length() == 0;
    }

}

