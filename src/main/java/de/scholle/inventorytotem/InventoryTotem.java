package de.scholle.inventorytotem;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InventoryTotem extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (event.getFinalDamage() >= player.getHealth()) {
            ItemStack totem = findTotem(player);
            if (totem != null) {
                totem.setAmount(totem.getAmount() - 1);
                player.setHealth(1);
                player.setFireTicks(0);
                player.setFallDistance(0);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 800, 0));
                player.getWorld().spawnParticle(Particle.TOTEM, player.getLocation(), 30, 0.5, 1, 0.5, 0.1);
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1f, 1f);
                event.setCancelled(true);
            }
        }
    }

    private ItemStack findTotem(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) return item;
        }
        return null;
    }
}
