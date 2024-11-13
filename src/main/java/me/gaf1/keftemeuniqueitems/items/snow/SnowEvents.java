package me.gaf1.keftemeuniqueitems.items.snow;

import me.gaf1.keftemeuniqueitems.Plugin;
import me.gaf1.keftemeuniqueitems.utils.ChatUtil;
import me.gaf1.keftemeuniqueitems.utils.ConfigManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Map;

public class SnowEvents implements Listener {

    @EventHandler
    public void onHit(ProjectileHitEvent event){
        if (!(event.getEntity().getShooter() instanceof Player)){
            return;
        }
        if (!(event.getEntity().hasMetadata("snow"))){
            return;
        }

        Snowball snowball = (Snowball) event.getEntity();

        int radius = ConfigManager.instance.getInt("Snowball.Radius");
        if (event.getHitBlock() != null){
            for (Entity entity : event.getHitBlock().getWorld().getNearbyEntities(event.getHitBlock().getLocation(),radius, radius, radius)) {

                if (entity instanceof Player) {

                    Player player = (Player) entity;
                    if (!ConfigManager.instance.getBoolean("Snowball.Effect_for_player_launch_snow")) {
                        if (player == event.getEntity().getShooter()) {
                            continue;
                        }
                    }

                    player.setVelocity(new Vector(0.0, ConfigManager.instance.getDouble("Snowball.Jump_height"), 0.0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, ConfigManager.instance.getInt("Snowball.Potion_effect_duration") * 20, ConfigManager.instance.getInt("Snowball.Potion_effect_amplifier") - 1));
                }

            }
        }
        else if (event.getHitEntity() != null){
            for (Entity entity : event.getHitEntity().getWorld().getNearbyEntities(event.getHitEntity().getLocation(),radius, radius, radius)) {

                if (entity instanceof Player) {

                    Player player = (Player) entity;
                    if (player == event.getEntity().getShooter()){
                        continue;
                    }

                    player.setVelocity(new Vector(0.0, ConfigManager.instance.getDouble("Snowball.Jump_height"), 0.0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, ConfigManager.instance.getInt("Snowball.Potion_effect_duration") * 20, ConfigManager.instance.getInt("Snowball.Potion_effect_amplifier") - 1));
                }

            }
        }
        Player player = (Player) event.getEntity().getShooter();
        ChatUtil.sendConfigMessage(player,"Messages.use_item", Map.of("%item%",Plugin.getInstance().getConfig().getString("Snowball.Name")));
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event){

        Player player = (Player) event.getEntity().getShooter();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (!(itemStack.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("snow"),PersistentDataType.INTEGER))){
            return;
        }

        event.getEntity().setMetadata("snow",new FixedMetadataValue(Plugin.getInstance(),1));
    }


}
