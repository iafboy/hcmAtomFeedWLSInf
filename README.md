# springboot-war-jcs-template project
本工程可以作为部署至jcs的springboot web 程序的template
## 调用 HCM CS的ATOM FEED REST Trigger应用
直接调用HCM的atom feed取得雇员信息更新列表
## core is spring boot
程序核心为spring boot 1.5.9，页面使用thymeleaf,资源文件位置发现部署在wls中无法识别，所以直接放在webapp下，thymeleaf的目录仍为resources/templates
## 注意：OP与云不同
本地使用wls12C调试成功后部署至JCS，但是发现JCS各种报错，幸好JCS可以看到系统后台日志可以逐个排查，如果是用JCS-SX那基本两眼一抹黑无解了。
