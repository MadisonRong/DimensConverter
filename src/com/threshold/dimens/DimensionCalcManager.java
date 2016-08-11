package com.threshold.dimens;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:DimensionCalcManager 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     2015年9月21日 下午4:39:48 
 * @author   黄守江
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class DimensionCalcManager {

	/**
	 * executeBatchTask: 批量执行Dimension文件转换，
	 * 只转换dp，不转换sp、px
	 * 
	 *
	 * @author 黄守江
	 * @param standardDimensionFilePath 1136x640的标准dimension.xml文件路径
	 * @param destinationResFolderPath 含有许多values的 res文件夹路径。子文件夹路径格式形如 values-mdpi-864x480
	 * @since JDK 1.6
	 */
	public static void executeBatchTask(String standardDimensionFilePath, String destinationResFolderPath){
		File file=new File(destinationResFolderPath);
		FileFilter filter= pathname -> {
			Pattern pattern = Pattern.compile("values-(l|m|h|xh|xxh)dpi-\\d+?x\\d+");
			System.out.println(pathname.getAbsolutePath());
			Matcher matcher = pattern.matcher(pathname.getAbsolutePath());
			if (matcher.find()) {
				return true;
			}
            return false;
        };
		File[] listFiles = file.listFiles(filter);
		for (File file2 : listFiles) {
			String folderName=file2.getName();
			System.out.println("Folder Name= "+folderName+", Path= "+file2.getAbsolutePath());
		    String[] split = folderName.split("-");
		    String dpiString=split[1];
		    String[] resolutions = split[2].split("x");
		    int height=Integer.valueOf(resolutions[0]);
		    int width=Integer.valueOf(resolutions[1]);
		    System.out.println("height= "+height+", width= "+width);
		    new ChangeDimensionTask(standardDimensionFilePath, file2.getAbsolutePath(), height, width, dpiString).execute();
		}
		System.out.println("\n\n\n ============================All Done================================");
		System.out.println("All Done,Calculate Dimension Complete!!!");
	}
}

