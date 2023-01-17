# QQ自动续火图片生成器

#### 介绍
原用于为QQ的自动续火脚本生成图片的java程序。根据命令将图片复制到剪切板中从而通过脚本将图片发送到指定QQ用户中。仍然在开发中……

Release分支包含jar包和使用脚本，使用前请配置好java和ahk。


#### 安装教程

1.  下载release分支的内容
2.  配置java8和ahk
3.  双击运行后缀名为ahk的文件即可

#### 使用说明

jar包可以被cmd命令调用，主要功能是将生成图片并将其送入剪贴板中。同时在当前目录下会生成后缀名为json的用户信息。
调用示例（cmd/bash）：
> java -Dfile.encoding=utf-8 -jar QQImageGenerator.jar tr 预热测试

可以使用的命令
1.  rc user //生成随机颜色和幸运数字
2.  tr user //生成15连抽
3.  lootMine user1 user2 user3... //不生成图片，每一个人会随机掠夺其他人10%经验值。

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
