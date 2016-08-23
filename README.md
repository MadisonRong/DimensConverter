# DimensConverter
The dimens converter for android.

>This is intent to calculate dp size to suit different devices.

###Sample usage:
1.decompress the res.zip file which from project test folder to D:  and then copy your app dimens.xml from your project to d:

![SAMPLE.PNG](http://i1.piimg.com/4851/0ae51d42534e82d9.png)

2.use "-i" to go to  edit mode,you can input dimens.xml path and res folder path manually.

3.use "-w -s -d". eg: "-w 864 -s d:\\dimens.xml -d d:\\res " -w: design width -s: source dimens.xml path,-d the res folder path.you need to build some folder like values-mdpi-320x240 in res folder.

4.use "-h": show help

### Note  
This project is forked from [wind0ws/DimensConverter](https://github.com/wind0ws/DimensConverter)  
The jar need JDK 1.8  
fix problem include:  
1.build jar for users  
2.make it work on converting dp, sp and dip  
3.offer one more parameter to specific design width
