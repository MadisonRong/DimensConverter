# DimensConverter
The dimens converter for android.

>This is intent to calculate dp size to suit different devices.

### Usage:
>first of all, you need to create some folder in your destination folder.And name them like 'values-xxhdpi-1080x1920'

1.use "-i" to go to  edit mode,you can input dimens.xml path and res folder path manually.

2.options:
1) -w width of design
2) -s source dimens.xml file location(Absolute path is recommended)
3) -d destination folder(generally /your/path/to/src/main/res/)
4) -dpi DPI value of design(1 is default)

3.use "-h": show help

### Notice
This project is forked from [wind0ws/DimensConverter](https://github.com/wind0ws/DimensConverter)  
This jar needs JDK 1.8
fix problem include:  
1.build jar for users  
2.make it work on converting dp, sp and dip  
3.offer -w parameter to specific design width
4.offer -dpi parameter to specific design DPI value