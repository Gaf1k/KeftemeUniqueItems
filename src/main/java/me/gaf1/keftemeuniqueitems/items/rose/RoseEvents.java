package me.gaf1.keftemeuniqueitems.items.rose;

import me.gaf1.keftemeuniqueitems.Plugin;
import me.gaf1.keftemeuniqueitems.utils.ChatUtil;
import me.gaf1.keftemeuniqueitems.utils.ConfigManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;


public class RoseEvents implements Listener {


    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        if (!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("rose"),PersistentDataType.INTEGER))){
            return;
        }
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand().clone();
        itemStack.setAmount(1);

        int radius = ConfigManager.instance.getInt("Rose.Radius");
        for (Entity entity : event.getPlayer().getNearbyEntities(radius, radius, radius)) {

            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (player == event.getPlayer()){
                    continue;
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, ConfigManager.instance.getInt("Rose.Potion_effect_duration") * 20, ConfigManager.instance.getInt("Rose.Potion_effect_amplifier") - 1));
            }
        }
        ChatUtil.sendConfigMessage(event.getPlayer(),"Messages.use_item", Map.of("%item%", Plugin.getInstance().getConfig().getString("Rose.Name")));
        event.getPlayer().getInventory().removeItem(itemStack);
    }
}
