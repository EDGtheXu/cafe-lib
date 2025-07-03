package com.github.edg_thexu.cafelib.registries;

import com.github.edg_thexu.cafelib.CafeLib;
import com.github.edg_thexu.cafelib.api.datacomponent.IDataComponentType;
import com.github.edg_thexu.cafelib.data.codec.DataComponentProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import static net.minecraft.resources.ResourceKey.createRegistryKey;

public class CafeLibRegistries {
    public static class DataComponentProviders{
        public static final ResourceKey<Registry<DataComponentProvider<? extends IDataComponentType<?>>>> KEY = createRegistryKey(CafeLib.space("data_component"));

    }
}
