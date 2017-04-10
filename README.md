# SwitchButton

开关按钮

## Gradle

在项目的build.gradle中添加:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

在使用库的module中添加:
```groovy
dependencies {
    compile 'com.github.shucc:SwitchButton:v1.1'
}
```

## Attributes

|name|format|description|
|:---:|:---:|:---:|
| switchButton_move | boolean | 是否可以拖动滑动，默认为FALSE
| switchButton_selectedColor | color | 选中部分背景色
| switchButton_unSelectedColor | color | 未选中部分背景色
| switchButton_color | color | 开关按钮颜色
| switchButton_padding | dimension | 开关按钮距离顶部底部间距
| switchButton_duration | integer | 按钮切换动画时间，默认300ms
| switchButton_springback | int | 判断自动回弹距离设置，默认为6，表示滑动距离小于1/6可滑动距离则回弹，只有switchButton_move设置为TRUE该属性才生效

## Demo

![](https://github.com/shucc/SwitchButton/blob/master/demo/demo.gif)