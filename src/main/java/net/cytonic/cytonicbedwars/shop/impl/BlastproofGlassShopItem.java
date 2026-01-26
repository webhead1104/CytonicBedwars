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
public class BlastproofGlassShopItem extends ShopItem {

    public BlastproofGlassShopItem() {
        super("blast_proof_glass", Msg.mm("Blast-Proof Glass"), List.of(), 4, Currency.IRON, 16, Material.GLASS,
            ItemShopPage.BLOCKS, 22);
    }

    private Material mapTeamToGlass(String teamColor) {
        return switch (teamColor.toUpperCase()) {
            case "RED" -> Material.RED_STAINED_GLASS;
            case "BLUE" -> Material.BLUE_STAINED_GLASS;
            case "GREEN" -> Material.LIME_STAINED_GLASS;
            case "YELLOW" -> Material.YELLOW_STAINED_GLASS;
            case "AQUA" -> Material.LIGHT_BLUE_STAINED_GLASS;
            case "PINK" -> Material.PINK_STAINED_GLASS;
            case "WHITE" -> Material.WHITE_STAINED_GLASS;
            case "GRAY" -> Material.GRAY_STAINED_GLASS;
            default -> throw new IllegalStateException("Unexpected value: " + teamColor);
        };
    }

    @Override
    public void onPurchase(BedwarsPlayer player) {
        Material glassMaterial = mapTeamToGlass(
            Objects.requireNonNull(player.getBedwarsTeam()).getColor().toString());
        player.getInventory().addItemStack(ItemStack.builder(glassMaterial)
            .amount(16)
            .build());
    }
}
