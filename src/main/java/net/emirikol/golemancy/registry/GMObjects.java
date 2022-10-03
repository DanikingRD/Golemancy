package net.emirikol.golemancy.registry;

import net.emirikol.golemancy.GMIdentifier;
import net.emirikol.golemancy.GolemancyItemGroup;
import net.emirikol.golemancy.item.SoulstoneEmpty;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class GMObjects {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Item> BLOCKS = new LinkedHashMap<>();

    public static final Item SOULSTONE_EMPTY = create("soulstone_empty", new SoulstoneEmpty(build()));


    private static <T extends Item> T create(String path, T item) {
        ITEMS.put(new GMIdentifier(path), item);
        return item;
    }

    private static Item.Settings build() {
        return new Item.Settings().group(GolemancyItemGroup.GOLEMANCY_ITEM_GROUP);
    }
    public static void register() {
        ITEMS.keySet().forEach(entry -> Registry.register(Registry.ITEM, entry, ITEMS.get(entry)));
        BLOCKS.keySet().forEach(entry -> Registry.register(Registry.ITEM, entry, BLOCKS.get(entry)));
    }
}
