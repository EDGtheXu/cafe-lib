package com.github.edg_thexu.cafelib.client.gui.widget;

import com.github.edg_thexu.cafelib.CafeLib;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class FloatButton extends TooltipButton {

    private boolean selected;
    long lastClickTime;
    private int duration = 300;

    protected FloatButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }

    public FloatButton(Builder builder) {
        super(builder);
    }

    public static Builder builder(Component message, OnPress onPress) {
        return new Builder( message, onPress);
    }

    public void renderString(GuiGraphics pGuiGraphics, Font pFont, int pColor) {
//        this.renderScrollingString(pGuiGraphics, pFont, 2, pColor);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blitNineSliced(WIDGETS_LOCATION, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, 66);


        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
//        guiGraphics.blitSprite(SPRITES.get(this.active, false), this.getX(), this.getY(), this.getWidth(), this.getHeight());


        long elapsedTime = System.currentTimeMillis() - this.lastClickTime;
        float progress = Mth.clamp(elapsedTime / (float) this.duration, 0.0F, 1.0F);

        if(!this.selected){
            progress = 1 - progress;
        }
//        progress = progress < 0.5 ? 4 * progress * progress * progress : (float) (1 - Math.pow(-2 * progress + 2, 2) / 2);
//        progress = (float) Math.pow(progress, 0.5f);
        progress = 1 - (1 - progress) * (1 - progress);

        int width = (int) (this.getWidth() * 0.5f * progress);
        int i = this.getFGColor();
        // 绿色背景颜色
        guiGraphics.setColor(0F, 1F, 0F, 1.0F);
        int w = (int) (width + this.getWidth() * 0.25f);
//        guiGraphics.blitSprite(SPRITES.get(this.active, false), this.getX(), this.getY(), w, this.getHeight());
        guiGraphics.pose().translate(0,0,20);
        guiGraphics.blitNineSliced(WIDGETS_LOCATION, this.getX(), this.getY(), w, this.getHeight(), 20, 4, 200, 20, 0, 66);

        AbstractWidget.renderScrollingString(guiGraphics, minecraft.font, Component.literal("ON"),
//                (int) (this.getX() + this.width * 0.25f + 2),
                this.getX() + 2, this.getY(),
                (int) (this.getX() + this.width * 0.5F), this.getY() + this.height,
                i | Mth.ceil(this.alpha * 255.0F) << 24);

        // 红色背景颜色
        guiGraphics.setColor(1F, 0F, 0F, 1.0F);
//        guiGraphics.blitSprite(SPRITES.get(this.active, false), this.getX() + w, this.getY(), this.width - w, this.getHeight());


        // 滑块
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        guiGraphics.setColor(1 - progress * 0.2f, 1.0F, 1 - progress * 0.2F, 1.0F);


//        if(TEHelper.isLoaded()) {
//            // 流动速度
//            float speed = 0.01f;
//            ((IShaderInstance) ModRenderTypes.Shaders.floatBarShader).getTerra_entity$Time().set(System.currentTimeMillis() % 100000 * speed);
//            // 噪声强度
//            ((IShaderInstance) ModRenderTypes.Shaders.floatBarShader).getTerra_entity$Radius().set(progress * 0.5F);
//
////            RenderSystem.setShaderTexture(0, ResourceLocation.withDefaultNamespace("textures/gui/sprites/widget/button.png"));
//            RenderSystem.setShaderTexture(0, ResourceLocation.withDefaultNamespace("textures/gui/sprites/container/beacon/button.png"));
//
//            RenderSystem.setShaderTexture(1, TerraEntity.space("textures/gui/noise.png"));
//            RenderSystem.setShader(() -> ModRenderTypes.Shaders.floatBarShader);
//            float ww = this.getWidth() * 0.45f;
//            ShaderUtil.shaderBlit(guiGraphics.pose().last().pose(),
//                    this.getX() + 1 + width, this.getY() + 1,
//                    0, 0F,
//                    (int) ww, this.getHeight() - 2,
//                    (int)ww, 22
//            );


//        }else{
//        public static void blitSprite(GuiGraphics graphics, ResourceLocation sprite, int x, int y, int width, int height){

        float scale = 0.5f;
        guiGraphics.blit(CafeLib.defaultPath("textures/gui/recipe_book.png"), this.getX() + width, this.getY()
                , 82F * scale, 208F * scale, (int) (32 * scale), (int) (32 * scale), (int) (256 * scale) , (int) (256 * scale));
//        }
//            guiGraphics.blitSprite(SPRITES.get(this.active, false), this.getX() + 1 + width, this.getY() + 1,
//            (int) (this.getWidth() * 0.45f), this.getHeight() - 2);

//        }
        // 滑块上的文本
        guiGraphics.setColor(1 - progress, 1.0F, 1 - progress, 1.0F);
        AbstractWidget.renderScrollingString(guiGraphics, minecraft.font, this.getMessage(),
//                (int) (width + this.getX() + this.width * 0.25f ),
                width + 2 + this.getX(), this.getY(),
                (int) (width + this.getX() + this.width * 0.5F) - 2, this.getY() + this.height,
                i | Mth.ceil(this.alpha * 255.0F) << 24);

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
//        this.renderScrollingString(guiGraphics, minecraft.font, width, i | Mth.ceil(this.alpha * 255.0F) << 24);
//        this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);


    }


    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return super.clicked(mouseX, mouseY) && System.currentTimeMillis() - this.lastClickTime > 300;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.lastClickTime = System.currentTimeMillis();
        this.selected = !this.selected;
        super.onClick(mouseX, mouseY);
    }

    @Override
    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
        super.renderTexture(pGuiGraphics, pTexture, pX, pY, pUOffset, pVOffset, pTextureDifference, pWidth, pHeight, pTextureWidth, pTextureHeight);
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static class Builder extends TooltipButton.Builder {

        private int duration = 300;

        public Builder(Component message, OnPress onPress) {
            super(message, onPress);
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        @Override
        public @NotNull FloatButton build() {
            FloatButton button = new FloatButton(this);
            button.setTooltips(tooltips);
            button.duration = duration;
            return button;
        }
    }

}
