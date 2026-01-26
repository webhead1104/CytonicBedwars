package net.cytonic.cytonicbedwars.shop.impl;

import java.util.List;
import java.util.Objects;

import com.google.j2objc.annotations.UsedByReflection;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.cytonicbedwars.data.enums.Currency;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.shop.ItemShopPage;
import net.cytonic.cytonicbedwars.shop.ShopItem;
import net.cytonic.cytosis.utils.Msg;

@UsedByReflection
public class WoolShopItem extends ShopItem {

    public WoolShopItem() {
        super("wool", Msg.mm("Wool"),
            List.of(Msg.mm("Greate for briding across islands."), Msg.mm("Turns into your team's color.")),
            4, Currency.IRON, 16, Material.WHITE_WOOL,
            ItemShopPage.BLOCKS, 21);
    }

    private Material mapTeamToWool(String teamColor) {
        return switch (teamColor.toUpperCase()) {
            case "RED" -> Material.RED_WOOL;
            case "BLUE" -> Material.BLUE_WOOL;
            case "GREEN" -> Material.LIME_WOOL;
            case "YELLOW" -> Material.YELLOW_WOOL;
            case "AQUA" -> Material.LIGHT_BLUE_WOOL;
            case "PINK" -> Material.PINK_WOOL;
            case "WHITE" -> Material.WHITE_WOOL;
            case "GRAY" -> Material.GRAY_WOOL;
            default -> throw new IllegalStateException("Unexpected value: " + teamColor);
        };
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        Material woolMaterial = mapTeamToWool(
            Objects.requireNonNull(player.getBedwarsTeam()).getColor().toString());
        player.getInventory().addItemStack(ItemStack.builder(woolMaterial)
            .amount(16)
            .build());
    }
}