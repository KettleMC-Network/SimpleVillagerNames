package net.kettlemc.villagernames.listener;

import net.kettlemc.villagernames.VillagerNamePlugin;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEntityListener implements Listener {

    private VillagerNamePlugin plugin;

    public PlayerInteractEntityListener(VillagerNamePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.isCancelled() || !plugin.getConfiguration().isRenamingAllowed())
            return;

        if (event.getRightClicked().getType() != EntityType.VILLAGER)
            return;

        Entity villager = event.getRightClicked();
        ItemStack item = event.getPlayer().getInventory().getItemInHand();

        if (item.getType() != Material.NAME_TAG)
            return;

        String name = item.getItemMeta().getDisplayName().toString();

        if (name == null || name.isEmpty())
            return;

        event.setCancelled(true);

        villager.setCustomName(name);
        villager.setCustomNameVisible(true);

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;

        item.setAmount(item.getAmount() - 1);
        event.getPlayer().getInventory().setItemInHand(item);
    }
}
