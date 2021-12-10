package net.kettlemc.villagernames.listener;

import net.kettlemc.villagernames.VillagerNamePlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.Random;

public class CreatureSpawnListener implements Listener {

    private VillagerNamePlugin plugin;

    public CreatureSpawnListener(VillagerNamePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (plugin.getConfiguration().getDisabledSpawnReasons().contains(event.getSpawnReason().name()))
            return;
        if (event.getEntityType() != EntityType.VILLAGER)
            return;
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING && !plugin.getConfiguration().generateRandomBabyLastName())
            return; // This gets handled in EntityBreedListener

        event.getEntity().setCustomName(plugin.generateRandomName(false));
        event.getEntity().setCustomNameVisible(true);



    }
}
