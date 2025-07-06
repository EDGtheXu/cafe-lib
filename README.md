This is a personal Minecraft mod library I use, which encapsulates some commonly used functionalities.

lang: [中文](./README_zh.md) | [English](./README.md)

***
# Main Features

## Minimalist-Style Configuration Menu

Features a navigation bar on the left and configuration text with corresponding widgets on the right.

* **Usage**: Inherit from [ConfigScreen](./src/main/java/com/github/edg_thexu/cafelib/client/gui/config_container/ConfigScreen.java), then pass the builderFunction into the constructor and register the screen.

* **Example**: For ConfigScreen, refer to [SimpleConfigScreen](https://github.com/EDGtheXu/BetterExperience/blob/1.20.1-forge/src/main/java/com/github/edg_thexu/better_experience/client/gui/config_container/BEConfigScreen.java). 
* For the builder, refer to [ConfigScreenBuilder](https://github.com/EDGtheXu/BetterExperience/blob/1.20.1-forge/src/main/java/com/github/edg_thexu/better_experience/client/gui/config_container/ConfigContainerRegister.java).

## Data Components Similar to Higher Versions

Earlier versions of Minecraft handled item NBT in a very unintuitive way, making operations cumbersome. Inspired by version 1.20.4, data components have been backported to lower versions, making it much easier to adapt between higher and lower versions.

Using a universal Codec, even stream transmission requires minimal code changes.

By using the modified [ItemProperties](./src/main/java/com/github/edg_thexu/cafelib/api/item/CafeItemProperties.java), registered builders can seamlessly integrate with higher versions.

* **Usage**: Register a [DataComponentProvider](./src/main/java/com/github/edg_thexu/cafelib/data/codec/DataComponentProvider.java), which is a Codec wrapper for [DataComponentType](./src/main/java/com/github/edg_thexu/cafelib/api/datacomponent/IDataComponentType.java), and then register it to the event bus. The name field serves as the key for the tag.

* **Example**: Refer to [ItemContainerComponent](https://github.com/EDGtheXu/BetterExperience/blob/1.20.1-forge/src/main/java/com/github/edg_thexu/better_experience/data/component/ItemContainerComponent.java)  and the [registration](https://github.com/EDGtheXu/BetterExperience/blob/1.20.1-forge/src/main/java/com/github/edg_thexu/better_experience/init/ModDataComponentTypes.java).

Note: Since data components in higher versions are stored directly in the ItemStack, while this mod converts NBT into data components, you must call the [writeToNBT](./src/main/java/com/github/edg_thexu/cafelib/api/datacomponent/IDataComponentType.java) method to save any modifications made to the data components in this mod. To encapsulate vanilla NBT, you can override the writeToNBT method. Refer to [Unbreakable](./src/main/java/com/github/edg_thexu/cafelib/data/component/Unbreakable.java) for an example. Automatically saves may be added in the future.