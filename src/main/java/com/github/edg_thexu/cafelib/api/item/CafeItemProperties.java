package com.github.edg_thexu.cafelib.api.item;

import com.github.edg_thexu.cafelib.api.datacomponent.IDataComponentType;
import com.github.edg_thexu.cafelib.data.codec.DataComponentProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 适配1.21.1的组件系统
 */
public class CafeItemProperties extends Item.Properties {
    Map<Supplier<? extends DataComponentProvider>, IDataComponentType> dataComponentTypeMap = new HashMap<>();


    public<B extends IDataComponentType<B>> CafeItemProperties component(Supplier<DataComponentProvider<B>> provider, B dataComponent){
        dataComponentTypeMap.put(provider, dataComponent);
        return this;
    }

    public void init(ItemStack stack){
        this.dataComponentTypeMap.forEach((k,v)->{
            v.writeToNBT(k, stack.getOrCreateTag());
        });
    }
}