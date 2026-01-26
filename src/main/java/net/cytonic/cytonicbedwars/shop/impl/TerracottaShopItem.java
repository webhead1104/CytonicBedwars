package net.cytonic.cytonicbedwars.shop.impl;

import java.util.List;
import java.util.Objects;

import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.cytonicbedwars.data.enums.Currency;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.shop.ItemShopPage;
import net.cytonic.cytonicbedwars.shop.ShopItem;
import net.cytonic.cytosis.utils.Msg;

public class TerracottaShopItem extends ShopItem {

    public TerracottaShopItem() {
        super("terracotta", Msg.mm("Terracotta"),
            List.of(Msg.mm("Basic block to defend your bed.")),
            12, Currency.IRON, 16, Material.TERRACOTTA,
            ItemShopPage.BLOCKS, 30);
    }

    private Material mapTeamToTerracotta(String teamColor) {
        return switch (teamColor.toUpperCase()) {
            case "RED" -> Material.RED_TERRACOTTA;
            case "BLUE" -> Material.BLUE_TERRACOTTA;
            case "GREEN" -> Material.GREEN_TERRACOTTA;
            case "YELLOW" -> Material.YELLOW_TERRACOTTA;
            case "AQUA" -> Material.LIGHT_BLUE_TERRACOTTA;
            case "PINK" -> Material.PINK_TERRACOTTA;
            case "WHITE" -> Material.WHITE_TERRACOTTA;
            case "GRAY" -> Material.GRAY_TERRACOTTA;
            default -> throw new IllegalStateException("Unexpected value: " + teamColor);
        };
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        Material terracottaMaterial = mapTeamToTerracotta(
            Objects.requireNonNull(player.getBedwarsTeam()).getColor().toString());
        player.getInventory().addItemStack(ItemStack.builder(terracottaMaterial)
            .amount(16)
            .build());
    }

}