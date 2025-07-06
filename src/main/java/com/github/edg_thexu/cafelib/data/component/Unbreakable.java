package com.github.edg_thexu.cafelib.data.component;

import com.github.edg_thexu.cafelib.api.datacomponent.IDataComponentType;
import com.github.edg_thexu.cafelib.data.codec.DataComponentProvider;
import com.github.edg_thexu.cafelib.init.CafeDataComponentTypes;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Supplier;

/**
 * 不可破坏组件
 */
public record Unbreakable(boolean showText) implements IDataComponentType<Unbreakable> {

    public static final Supplier<Codec<Unbreakable>> CODEC = ()->
            Codec.BOOL.xmap(Unbreakable::new, Unbreakable::showText);


    public static Unbreakable of(boolean showText) {
        return new Unbreakable(showText);
    }

    public void writeToNBT(CompoundTag tag){
        tag.putBoolean("Unbreakable", true);
    }

    @Override
    public DataComponentProvider<Unbreakable> provider() {
        return CafeDataComponentTypes.UNBREAKABLE_COMPONENT.get();
    }


//    public static EffectStrategyComponent of(EffectStrategy effect) {
//        return new EffectStrategyComponent(List.of(effect.getProvider()));
//    }

}
