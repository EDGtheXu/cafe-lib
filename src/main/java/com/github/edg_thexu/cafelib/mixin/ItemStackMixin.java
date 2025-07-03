package com.github.edg_thexu.cafelib.mixin;

import com.github.edg_thexu.cafelib.CafeLib;
import com.github.edg_thexu.cafelib.api.item.IItemExtension;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackMixin {

    @Unique
    private ItemStack cafelib$getSelf() {
        return (ItemStack)(Object)this;
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    void init(CompoundTag pCompoundTag, CallbackInfo ci) {
        Item rawItem =  BuiltInRegistries.ITEM.get(CafeLib.parse(pCompoundTag.getString("id")));
        if(rawItem instanceof IItemExtension extension){
            extension.cafe_lib$onStackInit(cafelib$getSelf());
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    void init(ItemLike item, int p_41605_, CompoundTag tag, CallbackInfo ci) {
        if(item instanceof  Item rawItem){
            if(rawItem instanceof IItemExtension extension){
                extension.cafe_lib$onStackInit(cafelib$getSelf());
            }
        }
    }

//    @Inject(method = "<init>(Ljava/lang/Void;)V", at = @At("RETURN"))
//    void init(Void p_282703_, CallbackInfo ci) {
//        if(this.item instanceof IItemExtension extension){
//            extension.cafe_lib$onStackInit(cafelib$getSelf());
//        }
//    }
}
