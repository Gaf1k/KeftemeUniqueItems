package me.gaf1.keftemeuniqueitems.items.membrane;

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

public class MembraneManager {

    public static final MembraneManager instance = new MembraneManager();

    public void giveMembrane(Player player){

        ItemStack membrane = new ItemBuilder(Material.PHANTOM_MEMBRANE).setName(ConfigManager.instance.getString("Membrane.Name"))
                .setLore(ConfigManager.instance.getListString("Membrane.Lore"))
                .addOrRemoveAllFlags(true)
                .addPersistent("membrane", PersistentDataType.INTEGER,1)
                .build();

        ItemMeta meta = membrane.getItemMeta();
        if (ConfigManager.instance.getBoolean("Membrane.Glow")){
            meta.addEnchant(Enchantment.PROTECTION_FALL,1,false);
        }
        membrane.setItemMeta(meta);

        player.getInventory().addItem(membrane);
        ChatUtil.sendConfigMessage(player,"Messages.receiving_item", Map.of("%item%", Plugin.getInstance().getConfig().getString("Membrane.Name")));
    }

}
