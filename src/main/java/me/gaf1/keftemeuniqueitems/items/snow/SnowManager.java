package me.gaf1.keftemeuniqueitems.items.snow;

import me.gaf1.keftemeuniqueitems.Plugin;
import me.gaf1.keftemeuniqueitems.utils.ChatUtil;
import me.gaf1.keftemeuniqueitems.utils.ConfigManager;
import me.gaf1.keftemeuniqueitems.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class SnowManager {

    public static final SnowManager instance = new SnowManager();


    public void giveSnow(Player player){

        ItemStack snowItem = new ItemBuilder(Material.SNOWBALL).setName(ConfigManager.instance.getString("Snowball.Name"))
                .setLore(ConfigManager.instance.getListString("Snowball.Lore"))
                .addOrRemoveAllFlags(true)
                .addPersistent("snow",PersistentDataType.INTEGER,1)
                .build();

        if (ConfigManager.instance.getBoolean("Snowball.Glow")){
            ItemMeta meta = snowItem.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_FALL,1,false);
            snowItem.setItemMeta(meta);
        }

        player.getInventory().addItem(snowItem);
        ChatUtil.sendConfigMessage(player,"Messages.receiving_item", Map.of("%item%", Plugin.getInstance().getConfig().getString("Snowball.Name")));
    }

}
