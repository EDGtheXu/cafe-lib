package com.github.edg_thexu.cafelib.data.component;

import com.github.edg_thexu.cafelib.api.datacomponent.IDataComponentType;
import com.github.edg_thexu.cafelib.data.codec.DataComponentProvider;
import com.github.edg_thexu.cafelib.init.CafeDataComponentTypes;
import com.mojang.serialization.Codec;

import java.util.function.Supplier;


public record SingleBooleanComponent(boolean value) implements IDataComponentType<SingleBooleanComponent> {

    public static final SingleBooleanComponent TRUE = new SingleBooleanComponent(true);
    public static final SingleBooleanComponent FALSE = new SingleBooleanComponent(false);

    public static final Supplier<Codec<SingleBooleanComponent>> CODEC = ()->Codec.BOOL.xmap(SingleBooleanComponent::new, SingleBooleanComponent::value);



    @Override
    public DataComponentProvider<SingleBooleanComponent> provider() {return CafeDataComponentTypes.BOOLEAN_COMPONENT.get();}

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if(object instanceof SingleBooleanComponent other){
            return value == other.value;
        }
        return false;
    }

}
