package com.github.edg_thexu.cafelib.data.codec;

import com.github.edg_thexu.cafelib.api.datacomponent.IDataComponentType;
import com.mojang.serialization.Codec;

import java.util.function.Supplier;

public class DataComponentProvider<T extends IDataComponentType<T>> extends LazyCodecProvider<T> {
    String name;

    public DataComponentProvider(String name, Supplier<Codec<T>> mapCodecSupplier) {
        super(mapCodecSupplier);
        this.name = name;
    }

    public String name(){
        return name;
    }



}
