package com.github.edg_thexu.cafelib.init;


import com.github.edg_thexu.cafelib.CafeLib;
import com.github.edg_thexu.cafelib.api.datacomponent.IDataComponentType;
import com.github.edg_thexu.cafelib.data.codec.DataComponentProvider;
import com.github.edg_thexu.cafelib.data.component.SingleBooleanComponent;
import com.github.edg_thexu.cafelib.data.component.Unbreakable;
import com.github.edg_thexu.cafelib.registries.CafeLibRegistries;
import com.mojang.serialization.Codec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public final class CafeDataComponentTypes {

    public static DeferredRegister<DataComponentProvider<? extends IDataComponentType<?>>> TYPES =  DeferredRegister.create(CafeLibRegistries.DataComponentProviders.KEY, CafeLib.MODID);
    public static final Supplier<IForgeRegistry<DataComponentProvider<?>>> REGISTRY = TYPES.makeRegistry(RegistryBuilder::new);

    public static final Supplier<DataComponentProvider<Unbreakable>> UNBREAKABLE_COMPONENT =
            register("unbreakable", Unbreakable.CODEC);

    public static final Supplier<DataComponentProvider<SingleBooleanComponent>> BOOLEAN_COMPONENT =
            register("boolean", SingleBooleanComponent.CODEC);



    private static <T extends IDataComponentType<T>> Supplier<DataComponentProvider<T>> register(String location, Supplier<Codec<T>> codec) {
        return TYPES.register(location, () -> new DataComponentProvider<>(CafeLib.MODID + ":"+location, codec));
    }

    public static void register(IEventBus bus) {
        TYPES.register(bus);
    }

}
