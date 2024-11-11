package me.gaf1.keftemeuniqueitems.items.membrane;

import me.gaf1.keftemeuniqueitems.Plugin;
import me.gaf1.keftemeuniqueitems.utils.ChatUtil;
import me.gaf1.keftemeuniqueitems.utils.ConfigManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class MembraneEvents implements Listener {


    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.PHANTOM_MEMBRANE){
            return;
        }
        if (!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("membrane"),PersistentDataType.INTEGER))){
            return;
        }
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand().clone();
        itemStack.setAmount(1);

        int radius = ConfigManager.instance.getInt("Membrane.Radius");
        List<Entity> nearbyEntities = event.getPlayer().getNearbyEntities(radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (player == event.getPlayer()){
                    continue;
                }
                player.getWorld().strikeLightningEffect(player.getLocation());
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, ConfigManager.instance.getInt("Membrane.Blindness_effect_duration") * 20, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, ConfigManager.instance.getInt("Membrane.Confusion_effect_duration") * 20, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ConfigManager.instance.getInt("Membrane.Slowness_effect_duration") * 20, ConfigManager.instance.getInt("Membrane.Slowness_effect_amplifier") - 1));
            }
        }
        ChatUtil.sendConfigMessage(event.getPlayer(),"Messages.use_item", Map.of("%item%", Plugin.getInstance().getConfig().getString("Membrane.Name")));
        event.getPlayer().getInventory().removeItem(itemStack);
    }
}
