package net.kettlemc.villagernames.listener;

import net.kettlemc.villagernames.VillagerNamePlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreedEvent;

public class EntityBreedListener implements Listener {

    private VillagerNamePlugin plugin;

    public EntityBreedListener(VillagerNamePlugin plugin) {
        this.plugin = plugin;
    }

    public void onBreed(EntityBreedEvent event) {
        if (plugin.getConfiguration().generateRandomBabyLastName() || plugin.getConfiguration().getDisabledSpawnReasons().contains(CreatureSpawnEvent.SpawnReason.BREEDING.name()))
            return;

        if (event.getEntityType() != EntityType.VILLAGER)
            return;

        String name = event.getEntity().getCustomName();

        if (name == null || name.isEmpty())
            return;

        String names[] = name.split(" ");
        String lastName = names[names.length - 1];
        String firstName = plugin.generateRandomName(true);

        event.getEntity().setCustomName(firstName + lastName);
    }
}
