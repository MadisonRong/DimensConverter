package com.madisonrong.tools.dimens;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:DimensionCalcManager
 * Date:     2015年9月21日 下午4:39:48 
 * @author   黄守江, MadisonRong
 */
public class DimensionCalcManager {

	/**
	 * executeBatchTask: 批量执行Dimension文件转换，
	 * 只转换 dp/dip/sp，不转换 px
	 * 
	 *
	 * @author 黄守江, MadisonRong
	 * @param standardDimensionFilePath 1136x640的标准dimension.xml文件路径
	 * @param destinationResFolderPath 含有许多values的 res文件夹路径。子文件夹路径格式形如 values-mdpi-864x480
	 * @param designWidth 设计图标准宽度,作为基准宽度
     * @param designDpi 设计图标准 DPI 值, 默认为 1
	 */
	public static void executeBatchTask(String standardDimensionFilePath, String destinationResFolderPath,
										int designWidth, int designDpi){
		File file = new File(destinationResFolderPath);
		FileFilter filter= pathname -> {
			Pattern pattern = Pattern.compile("values-(l|m|h|xh|xxh|xxxh|\\d+)dpi-\\d+x\\d+");
			Matcher matcher = pattern.matcher(pathname.getAbsolutePath());
			if (matcher.find()) {
				return true;
			}
            return false;
        };
		File[] listFiles = file.listFiles(filter);
        if (listFiles != null) {
            for (File file2 : listFiles) {
                String folderName = file2.getName();
                System.out.println("Folder Name= " + folderName + ", Path= " + file2.getAbsolutePath());
                String[] split = folderName.split("-");
                String dpiString = split[1];
                String[] resolutions = split[2].split("x");
                int width = Integer.valueOf(resolutions[0]);
                int height = Integer.valueOf(resolutions[1]);
                System.out.println(String.format("width: %s, height: %s", width, height));
                new ChangeDimensionTask(standardDimensionFilePath, file2.getAbsolutePath(),
                        designWidth, designDpi, height, width, dpiString).execute();
            }
            System.out.println("\n============================All Done================================");
            System.out.println("All Done,Calculate Dimension Complete!!!");
        } else {
            System.out.println("no folder found");
        }
	}
}

