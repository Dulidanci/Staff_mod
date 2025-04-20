package net.dulidanci.staffmod;

import net.dulidanci.staffmod.block.ModBlocks;
import net.dulidanci.staffmod.entity.ModEntities;
import net.dulidanci.staffmod.entity.client.LoyalBeeEntityRenderer;
import net.dulidanci.staffmod.util.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class StaffModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUEPRINT_PLANKS, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.LOYAL_BEE, LoyalBeeEntityRenderer::new);

        HudRenderer.register();
    }
}
