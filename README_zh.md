这是我个人用的Minecraft模组lib，封装了一些常用的功能 ~~欢迎一键CV~~

lang: [中文](./README_zh.md) | [English](./README.md)

***
# 主要功能

## 简约风格的配置菜单

左侧有导航栏，右侧是每个配置项文本和对应的配置wedget

* **用法**：需要继承 [ConfigScreen](./src/main/java/com/github/edg_thexu/cafelib/client/gui/config_container/ConfigScreen.java)，然后将builderFunction传入构造函数，注册屏幕即可。

* **示例**：ConfigScreen请参考 [SimpleConfigScreen](https://github.com/EDGtheXu/BetterExperience/blob/1.20.1-forge/src/main/java/com/github/edg_thexu/better_experience/client/gui/config_container/BEConfigScreen.java), 
builder可参考[ConfigScreenBuilder](https://github.com/EDGtheXu/BetterExperience/blob/1.20.1-forge/src/main/java/com/github/edg_thexu/better_experience/client/gui/config_container/ConfigContainerRegister.java)。

## 类似高版本物品的数据组件

先前版本物品的nbt非常不人性化，操作起来很麻烦。受到1.20.4的启发，将数据组件移植到了低版本，这样，高版本和低版本直接的移植就方便了很多。

采用通用的Codec，即使是流传输，也依然不需要修改过多的代码。

使用修改过的[ItemProperties](./src/main/java/com/github/edg_thexu/cafelib/api/item/CafeItemProperties.java), 可以将注册的builder与高版本无缝衔接。

* **用法**：注册[DataComponentProvider](./src/main/java/com/github/edg_thexu/cafelib/data/codec/DataComponentProvider.java)，即[DataComponentType](./src/main/java/com/github/edg_thexu/cafelib/api/datacomponent/IDataComponentType.java)的Codec包装类，然后注册到事件总线。其中的name字段为tag的key

* **示例**：请参考[ItemContainerComponent](https://github.com/EDGtheXu/BetterExperience/blob/1.20.1-forge/src/main/java/com/github/edg_thexu/better_experience/data/component/ItemContainerComponent.java) 以及[注册方式](https://github.com/EDGtheXu/BetterExperience/blob/1.20.1-forge/src/main/java/com/github/edg_thexu/better_experience/init/ModDataComponentTypes.java)

注意：由于高版本的数据组件是直接存储在ItemStack里面的，而本模组是将nbt转化成数据组件，所以在修改本模组数据组件以后须调用函数[writeToNBT](./src/main/java/com/github/edg_thexu/cafelib/api/datacomponent/IDataComponentType.java)方法来保存修改。若要封装原版的nbt，可以重写writeToNBT方法，请参考[Unbreakable](./src/main/java/com/github/edg_thexu/cafelib/data/component/Unbreakable.java)，不过未来可能做成自动保存


