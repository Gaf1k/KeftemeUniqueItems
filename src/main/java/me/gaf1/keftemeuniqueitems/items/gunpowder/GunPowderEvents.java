package me.gaf1.keftemeuniqueitems.items.gunpowder;

import me.gaf1.keftemeuniqueitems.Plugin;
import me.gaf1.keftemeuniqueitems.utils.ChatUtil;
import me.gaf1.keftemeuniqueitems.utils.ConfigManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;


public class GunPowderEvents implements Listener {


    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.GUNPOWDER){
            return;
        }
        if (!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("powder"),PersistentDataType.INTEGER))){
            return;
        }
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand().clone();
        itemStack.setAmount(1);

        int radius = ConfigManager.instance.getInt("Powder.Radius");
        for (Entity entity : event.getPlayer().getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof Player){
                Player player = (Player) entity;
                if (player == event.getPlayer()){
                    continue;
                }
                Vector direction = event.getPlayer().getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                direction.setY(0.5);
                direction.setX(-direction.getX());
                direction.setZ(-direction.getZ());
                player.setVelocity(direction.multiply(ConfigManager.instance.getDouble("Powder.Power_of_knockback")));
            }
        }
        ChatUtil.sendConfigMessage(event.getPlayer(),"Messages.use_item", Map.of("%item%", Plugin.getInstance().getConfig().getString("Powder.Name")));
        event.getPlayer().getInventory().removeItem(itemStack);
    }
}
