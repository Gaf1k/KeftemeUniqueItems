package me.gaf1.keftemeuniqueitems.items.slime;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.print.DocFlavor;
import java.util.Map;

public class SlimeEvents implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() ==  Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR){
            return;
        }
        if (!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("slime"), PersistentDataType.INTEGER))){
            return;
        }
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand().clone();
        itemStack.setAmount(1);

        int radius = ConfigManager.instance.getInt("Slime.Radius");

        for (Entity entity: event.getPlayer().getNearbyEntities(radius,radius,radius)){
            if (!(entity instanceof Player)){
                continue;
            }
            Player player = (Player) entity;
            if (player == event.getPlayer()){
                continue;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(ConfigManager.instance.getInt("Slime.Slowness_effect_duration")*20),(ConfigManager.instance.getInt("Slime.Slowness_effect_amplifier")-1)));
        }


        ChatUtil.sendConfigMessage(event.getPlayer(),"Messages.use_item", Map.of("%item%",Plugin.getInstance().getConfig().getString("Slime.Name")));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,(ConfigManager.instance.getInt("Slime.Regeneration_effect_duration")*20),(ConfigManager.instance.getInt("Slime.Regeneration_effect_amplifier")*-1)));
        event.getPlayer().getInventory().removeItem(itemStack);
    }
}
