package net.emirikol.golemancy;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.emirikol.golemancy.block.ClayEffigyBlock;
import net.emirikol.golemancy.block.ObsidianEffigyBlock;
import net.emirikol.golemancy.block.SoulGrafterBlock;
import net.emirikol.golemancy.block.TerracottaEffigyBlock;
import net.emirikol.golemancy.block.entity.SoulGrafterBlockEntity;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.entity.projectile.ClayballEntity;
import net.emirikol.golemancy.event.CommandRegistrationHandler;
import net.emirikol.golemancy.event.ConfigurationHandler;
import net.emirikol.golemancy.event.SoulstoneFillHandler;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.emirikol.golemancy.item.GolemWand;
import net.emirikol.golemancy.item.SoulMirror;
import net.emirikol.golemancy.item.SoulstoneEmpty;
import net.emirikol.golemancy.item.SoulstoneFilled;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.emirikol.golemancy.registry.GMObjects;
import net.emirikol.golemancy.screen.SoulGrafterScreenHandler;
import net.emirikol.golemancy.screen.SoulMirrorScreenHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.emirikol.golemancy.GolemancyItemGroup.buildGolemancyItemGroup;

public class Golemancy implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Golemancy");
    public static final Identifier ConfigPacketID = new Identifier("golemancy", "config_packet");

    public static ScreenHandlerType<SoulMirrorScreenHandler> SOUL_MIRROR_SCREEN_HANDLER;
    public static BlockEntityType<SoulGrafterBlockEntity> SOUL_GRAFTER_ENTITY;
    public static ScreenHandlerType<SoulGrafterScreenHandler> SOUL_GRAFTER_SCREEN_HANDLER;


    public static void doInstantiation() {
        SOUL_MIRROR_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier("golemancy", "soul_mirror"), SoulMirrorScreenHandler::new);
        //Instantiate soul grafter.
//        FabricBlockSettings soul_grafter_settings = FabricBlockSettings.of(Material.STONE);
//        soul_grafter_settings.hardness(4.0F).strength(5.0F, 1200.0F);
//        soul_grafter_settings.requiresTool();
//        SOUL_GRAFTER = new SoulGrafterBlock(soul_grafter_settings);
//        FabricItemSettings soul_grafter_item_settings = new FabricItemSettings();
//        SOUL_GRAFTER_ITEM = new BlockItem(SOUL_GRAFTER, soul_grafter_item_settings);
        SOUL_GRAFTER_ENTITY = FabricBlockEntityTypeBuilder.create(SoulGrafterBlockEntity::new, GMObjects.SOUL_GRAFTER).build(null);
        SOUL_GRAFTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("golemancy", "soul_grafter"), SoulGrafterScreenHandler::new);
        //Instantiate clay effigy.
//        FabricBlockSettings clay_effigy_settings = FabricBlockSettings.of(Material.DECORATION);
//        clay_effigy_settings.strength(0.6F).nonOpaque();
//        CLAY_EFFIGY_BLOCK = new ClayEffigyBlock(clay_effigy_settings);
//        FabricItemSettings effigy_settings = new FabricItemSettings();
//        effigy_settings.group(null);
//        CLAY_EFFIGY = new BlockItem(CLAY_EFFIGY_BLOCK, effigy_settings);
//        //Instantiate terracotta effigy.
//        TERRACOTTA_EFFIGY_BLOCK = new TerracottaEffigyBlock(clay_effigy_settings);
//        TERRACOTTA_EFFIGY = new BlockItem(TERRACOTTA_EFFIGY_BLOCK, effigy_settings);
        //Instantiate obsidian effigy.
//        OBSIDIAN_EFFIGY_BLOCK = new ObsidianEffigyBlock(clay_effigy_settings);
//        OBSIDIAN_EFFIGY = new BlockItem(OBSIDIAN_EFFIGY_BLOCK, effigy_settings);
//        //Instantiate golems.

    }

    public static void doRegistration() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, "golemancy:soul_grafter", SOUL_GRAFTER_ENTITY);
        //Register golems.

        for (EntityType<? extends AbstractGolemEntity> type : SoulTypes.getEntityTypes()) {
            FabricDefaultAttributeRegistry.register(type, AbstractGolemEntity.createGolemAttributes());
        }
    }

    @Override
    public void onInitialize(ModContainer container) {
        GMObjects.register();
        GMEntityTypes.register();
        doInstantiation();
        doRegistration();
        CommandRegistrationHandler.commandRegistrationHook(); //add event hook for registering this mod's commands
        SoulstoneFillHandler.soulstoneFillHook(); //add event hook for replacing soulstones with mob soulstones when you kill mobs
        ConfigurationHandler.syncConfigHook(); //add event hook for syncing server and client configs when a player connects
        buildGolemancyItemGroup(); ////add custom ItemGroup that contains all mod items including custom soulstones
        AutoConfig.register(ConfigurationHandler.class, GsonConfigSerializer::new); //register the AutoConfig handler - see GolemancyConfig for details
        LOGGER.info("Arise, my minions!");
    }
}
