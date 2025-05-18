package net.dulidanci.staffmod;

import net.dulidanci.staffmod.block.ModBlockEntities;
import net.dulidanci.staffmod.block.ModBlocks;
import net.dulidanci.staffmod.block.entity.render.StaffUpgradeStationBlockEntityRenderer;
import net.dulidanci.staffmod.entity.ModEntities;
import net.dulidanci.staffmod.entity.client.LoyalBeeEntityRenderer;
import net.dulidanci.staffmod.screen.ModScreenHandlers;
import net.dulidanci.staffmod.screen.StaffUpgradeStationEmptyScreen;
import net.dulidanci.staffmod.screen.StaffUpgradeStationStaffScreen;
import net.dulidanci.staffmod.util.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class StaffModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUEPRINT_PLANKS, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.LOYAL_BEE, LoyalBeeEntityRenderer::new);

        HudRenderer.register();

        HandledScreens.register(ModScreenHandlers.STAFF_UPGRADE_STATION_EMPTY_SCREEN_HANDLER, StaffUpgradeStationEmptyScreen::new);
        HandledScreens.register(ModScreenHandlers.STAFF_UPGRADE_STATION_STAFF_SCREEN_HANDLER, StaffUpgradeStationStaffScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.STAFF_UPGRADE_STATION_BLOCK_ENTITY, StaffUpgradeStationBlockEntityRenderer::new);
    }
}
