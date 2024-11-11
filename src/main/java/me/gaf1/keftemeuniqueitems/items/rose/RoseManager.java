package me.gaf1.keftemeuniqueitems.items.rose;

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

public class RoseManager {
    public static final RoseManager instance = new RoseManager();

    public void giveRose(Player player){

        ItemStack rose = new ItemBuilder(Material.WITHER_ROSE).setName(ConfigManager.instance.getString("Rose.Name"))
                .setLore(ConfigManager.instance.getListString("Rose.Lore"))
                .addOrRemoveAllFlags(true)
                .addPersistent("rose", PersistentDataType.INTEGER,1)
                .build();

        ItemMeta meta = rose.getItemMeta();
        if (ConfigManager.instance.getBoolean("Rose.Glow")){
            meta.addEnchant(Enchantment.PROTECTION_FALL,1,false);
        }
        rose.setItemMeta(meta);

        player.getInventory().addItem(rose);
        ChatUtil.sendConfigMessage(player,"Messages.receiving_item", Map.of("%item%", Plugin.getInstance().getConfig().getString("Rose.Name")));
    }

}
