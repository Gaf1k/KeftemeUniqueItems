package me.gaf1.keftemeuniqueitems.items.gunpowder;

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

public class GunPowderManager {

    public static final GunPowderManager instance = new GunPowderManager();

    public void givePowder(Player player){

        ItemStack gunPowder = new ItemBuilder(Material.GUNPOWDER).setName(ConfigManager.instance.getString("Powder.Name"))
                .setLore(ConfigManager.instance.getListString("Powder.Lore"))
                .addOrRemoveAllFlags(true)
                .addPersistent("powder", PersistentDataType.INTEGER,1)
                .build();

        if (ConfigManager.instance.getBoolean("Powder.Glow")){
            ItemMeta meta = gunPowder.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_FALL,1,false);
            gunPowder.setItemMeta(meta);
        }

        player.getInventory().addItem(gunPowder);

        ChatUtil.sendConfigMessage(player,"Messages.receiving_item", Map.of("%item%", Plugin.getInstance().getConfig().getString("Powder.Name")));
    }

}
