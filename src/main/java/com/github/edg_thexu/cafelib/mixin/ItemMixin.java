package com.github.edg_thexu.cafelib.mixin;

import com.github.edg_thexu.cafelib.api.item.CafeItemProperties;
import com.github.edg_thexu.cafelib.api.item.IItemExtension;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin implements IItemExtension {

    @Unique
    Item.Properties cafe_lib$properties;

    @Unique
    @Inject(method = "<init>", at = @At("RETURN"))
    public void initMixin(Item.Properties p_41383_, CallbackInfo ci){
        this.cafe_lib$properties = p_41383_;
    }

    @Override
    public void cafe_lib$onStackInit(ItemStack stack) {
        if(cafe_lib$properties instanceof CafeItemProperties properties){
            properties.init(stack);
        }
    }
}
