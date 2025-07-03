package com.github.edg_thexu.cafelib.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class RenderUtil {

    public static void shaderBlit(Matrix4f matrix4f, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        shaderBlit(matrix4f, x , x+width, y, y+height , 0, width, height,uOffset, vOffset, textureWidth, textureHeight);
    }

    static void shaderBlit(Matrix4f matrix4f, int x1, int x2, int y1, int y2, int blitOffset, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        innerBlit(matrix4f, x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float)textureWidth, (uOffset + (float)uWidth) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight);
    }

    static void innerBlit(Matrix4f matrix4f,int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float)x1, (float)y1, (float)blitOffset).uv(minU, minV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)x1, (float)y2, (float)blitOffset).uv(minU, maxV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)x2, (float)y2, (float)blitOffset).uv(maxU, maxV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)x2, (float)y1, (float)blitOffset).uv(maxU, minV).endVertex();

//        bufferbuilder.vertex(0.0, (double)f1, 0.0).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
//        bufferbuilder.vertex((double)f, (double)f1, 0.0).uv(f2, 0.0F).color(255, 255, 255, 255).endVertex();
//        bufferbuilder.vertex((double)f, 0.0, 0.0).uv(f2, f3).color(255, 255, 255, 255).endVertex();
//        bufferbuilder.vertex(0.0, 0.0, 0.0).uv(0.0F, f3).color(255, 255, 255, 255).endVertex();

        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    public static void blitScreen(ShaderInstance shader, Consumer<ShaderInstance> setupShader){

        RenderSystem.assertOnRenderThread();
        GlStateManager._colorMask(true, true, true, false);
        GlStateManager._disableDepthTest();
        GlStateManager._viewport(0, 0, Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height);

//        ShaderInstance shader = ModRenderTypes.Shaders.colorBlitShader;
//        ShaderInstance shaderinstance = Objects.requireNonNull(shader, "Blit shader not loaded");
//        shader.COLOR_MODULATOR.set(1f, 1f, 1f, 0.2f);
//        shaderinstance.setSampler("Sampler0", Minecraft.getInstance().getMainRenderTarget());
//        shaderinstance.setSampler("Sampler1", BrainOfCthulhuRenderer.target);
        float f = Minecraft.getInstance().getMainRenderTarget().width;
        float f1 = Minecraft.getInstance().getMainRenderTarget().height;
        setupShader.accept(shader);
        Matrix4f matrix4f = (new Matrix4f()).setOrtho(0.0F, f, f1, 0.0F, 1000.0F, 3000.0F);
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.ORTHOGRAPHIC_Z);
        if (shader.MODEL_VIEW_MATRIX != null) {
            shader.MODEL_VIEW_MATRIX.set((new Matrix4f()).translation(0.0F, 0.0F, -2000.0F));
        }

        if (shader.PROJECTION_MATRIX != null) {
            shader.PROJECTION_MATRIX.set(matrix4f);
        }
        shader.apply();

        float f2 = 1;
        float f3 = 1;
        Tesselator tesselator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0, f1, 0.0).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(f, f1, 0.0).uv(f2, 0.0F).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(f, 0.0, 0.0).uv(f2, f3).color(255, 255, 255, 255).endVertex();
        bufferbuilder.vertex(0.0, 0.0, 0.0).uv(0.0F, f3).color(255, 255, 255, 255).endVertex();
        BufferUploader.draw(bufferbuilder.end());
        shader.clear();
        GlStateManager._depthMask(true);
        GlStateManager._colorMask(true, true, true, true);
    }

    public static void renderDebugBlock(VertexConsumer buffer, BlockPos pos, float size, int r, int g, int b, int a){
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        buffer.vertex(x, y + size, z).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y + size, z).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y + size, z).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y + size, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y + size, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x, y + size, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x, y + size, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x, y + size, z).color(r,g,b,a).endVertex();

        // BOTTvertex()
        buffer.vertex(x + size, y, z).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x, y, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x, y, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x, y, z).color(r,g,b,a).endVertex();
        buffer.vertex(x, y, z).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y, z).color(r,g,b,a).endVertex();

        // Edgevertex()
        buffer.vertex(x + size, y, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y + size, z + size).color(r,g,b,a).endVertex();

        // Edgevertex()
        buffer.vertex(x + size, y, z).color(r,g,b,a).endVertex();
        buffer.vertex(x + size, y + size, z).color(r,g,b,a).endVertex();

        // Edgevertex()
        buffer.vertex(x, y, z + size).color(r,g,b,a).endVertex();
        buffer.vertex(x, y + size, z + size).color(r,g,b,a).endVertex();

        // Edgevertex()
        buffer.vertex(x, y, z).color(r,g,b,a).endVertex();
        buffer.vertex(x, y + size, z).color(r,g,b,a).endVertex();
    }

    public static void renderAABB(VertexConsumer buffer,
                                  float x1, float y1, float z1, float x2, float y2, float z2,
                                  int r, int g, int b, int a,
                                  float size, float u, float v

    ){
        float w = x2 - x1;
        float h = y2 - y1;
        float d = z2 - z1;

        float scaleX =  w / size;
        float scaleY =  h / size;
        float scaleZ =  d / size;

        buffer.vertex(x1, y1, z1).color(r,g,b,a).uv(0 + u, 0+v).endVertex();
        buffer.vertex(x2, y1, z1).color(r,g,b,a).uv(scaleX+ u, 0+v).endVertex();
        buffer.vertex(x2, y2, z1).color(r,g,b,a).uv(scaleX+ u, scaleY+v).endVertex();
        buffer.vertex(x1, y2, z1).color(r,g,b,a).uv(0 + u, scaleY+v).endVertex();

        buffer.vertex(x1, y1, z2).color(r,g,b,a).uv(0 + u, 0+v).endVertex();
        buffer.vertex(x2, y1, z2).color(r,g,b,a).uv(scaleX+ u, 0+v).endVertex();
        buffer.vertex(x2, y2, z2).color(r,g,b,a).uv(scaleX+ u, scaleY+v).endVertex();
        buffer.vertex(x1, y2, z2).color(r,g,b,a).uv(0 + u, scaleY+v).endVertex();

        buffer.vertex(x1, y1, z1).color(r,g,b,a).uv(0+ u, 0+v).endVertex();
        buffer.vertex(x1, y1, z2).color(r,g,b,a).uv(scaleZ+ u, 0+v).endVertex();
        buffer.vertex(x1, y2, z2).color(r,g,b,a).uv(scaleZ+ u, scaleY+v).endVertex();
        buffer.vertex(x1, y2, z1).color(r,g,b,a).uv(0 + u, scaleY+v).endVertex();

        buffer.vertex(x2, y1, z1).color(r,g,b,a).uv(0+ u, 0+v).endVertex();
        buffer.vertex(x2, y1, z2).color(r,g,b,a).uv(scaleZ+ u, 0+v).endVertex();
        buffer.vertex(x2, y2, z2).color(r,g,b,a).uv(scaleZ+ u, scaleY+v).endVertex();
        buffer.vertex(x2, y2, z1).color(r,g,b,a).uv(0 + u, scaleY+v).endVertex();

        buffer.vertex(x1, y1, z1).color(r,g,b,a).uv(0+ u, 0+v).endVertex();
        buffer.vertex(x1, y1, z2).color(r,g,b,a).uv(scaleZ+ u, 0+v).endVertex();
        buffer.vertex(x2, y1, z2).color(r,g,b,a).uv(scaleZ+ u, scaleX+v).endVertex();
        buffer.vertex(x2, y1, z1).color(r,g,b,a).uv(0 + u, scaleX+v).endVertex();

        buffer.vertex(x1, y2, z1).color(r,g,b,a).uv(0+ u, 0+v).endVertex();
        buffer.vertex(x1, y2, z2).color(r,g,b,a).uv(scaleZ+ u, 0+v).endVertex();
        buffer.vertex(x2, y2, z2).color(r,g,b,a).uv(scaleZ+ u, scaleX+v).endVertex();
        buffer.vertex(x2, y2, z1).color(r,g,b,a).uv(0+ u, scaleX+v).endVertex();

    }

    public static void renderAABBOutLine(VertexConsumer buffer,
                                  float x1, float y1, float z1, float x2, float y2, float z2,
                                  int r, int g, int b, int a,
                                  float size

    ){

        buffer.vertex(x1, y1, z1).color(r,g,b,a).endVertex();
//        buffer.vertex(x2, y1, z1).color(r,g,b,a).endVertex();
//        buffer.vertex(x2, y2, z1).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y2, z1).color(r,g,b,a).endVertex();

//        buffer.vertex(x1, y1, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y1, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y2, z2).color(r,g,b,a).endVertex();
//        buffer.vertex(x1, y2, z2).color(r,g,b,a).endVertex();

//        buffer.vertex(x1, y1, z1).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y1, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y2, z2).color(r,g,b,a).endVertex();
//        buffer.vertex(x1, y2, z1).color(r,g,b,a).endVertex();

        buffer.vertex(x2, y1, z1).color(r,g,b,a).endVertex();
//        buffer.vertex(x2, y1, z2).color(r,g,b,a).endVertex();
//        buffer.vertex(x2, y2, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y2, z1).color(r,g,b,a).endVertex();

        buffer.vertex(x1, y1, z1).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y1, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y1, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y1, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y1, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y1, z1).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y1, z1).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y1, z1).color(r,g,b,a).endVertex();

        buffer.vertex(x1, y2, z1).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y2, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y2, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y2, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y2, z2).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y2, z1).color(r,g,b,a).endVertex();
        buffer.vertex(x2, y2, z1).color(r,g,b,a).endVertex();
        buffer.vertex(x1, y2, z1).color(r,g,b,a).endVertex();

    }

    public static void customTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, List<ClientTooltipComponent> components){
        Minecraft minecraft = Minecraft.getInstance();
//        List<ClientTooltipComponent> components = List.of(
//                ClientTooltipComponent.create(FormattedCharSequence.forward("hello world", Style.EMPTY.withColor(0xFFFFFF)))
////                    ClientTooltipComponent.create(Component.translatable(SwordItems.BEE_KEEPER.get().getDescriptionId()).withStyle(Style.EMPTY.withColor(0xFFFFFF)).getVisualOrderText())
//
//        );
        int i = 0;
        int j = components.size() == 1 ? -2 : 0;

        ClientTooltipComponent clienttooltipcomponent;
        for(Iterator<ClientTooltipComponent> var9 = components.iterator(); var9.hasNext(); j += clienttooltipcomponent.getHeight()) {
            clienttooltipcomponent = var9.next();
            int k = clienttooltipcomponent.getWidth(minecraft.font);
            if (k > i) {
                i = k;
            }
        }

        int l = mouseX + 12;
        int i1 = mouseY - 12;
        guiGraphics.pose().pushPose();

        TooltipRenderUtil.renderTooltipBackground(guiGraphics, l, i1, i, j, 400,
                -267386864, -267386864,
                1347420415, 1347420415);

        guiGraphics.pose().translate(0.0F, 0.0F, 400.0F);
        int k1 = i1;

        int k2;
        ClientTooltipComponent clienttooltipcomponent2;
        for(k2 = 0; k2 < components.size(); ++k2) {
            clienttooltipcomponent2 = components.get(k2);
            clienttooltipcomponent2.renderText(minecraft.font, l, k1, guiGraphics.pose().last().pose(), guiGraphics.bufferSource());
            k1 += clienttooltipcomponent2.getHeight() + (k2 == 0 ? 2 : 0);
        }

        k1 = i1;

        for(k2 = 0; k2 < components.size(); ++k2) {
            clienttooltipcomponent2 = components.get(k2);
            clienttooltipcomponent2.renderImage(minecraft.font, l, k1, guiGraphics);
            k1 += clienttooltipcomponent2.getHeight() + (k2 == 0 ? 2 : 0);
        }

        guiGraphics.pose().popPose();
    }
}
