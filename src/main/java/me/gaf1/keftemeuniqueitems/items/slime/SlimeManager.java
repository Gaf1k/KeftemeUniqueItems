package me.gaf1.keftemeuniqueitems.items.slime;

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

public class SlimeManager {

    public static final SlimeManager instance = new SlimeManager();;

    public void giveSlime(Player player){
        ItemStack slime = new ItemBuilder(Material.SLIME_BALL).setName(ConfigManager.instance.getString("Slime.Name"))
                .setLore(ConfigManager.instance.getListString("Slime.Lore"))
                .addOrRemoveAllFlags(true)
                .addPersistent("slime", PersistentDataType.INTEGER,1)
                .build();
        ItemMeta meta = slime.getItemMeta();
        if (ConfigManager.instance.getBoolean("Slime.Glow")){
            meta.addEnchant(Enchantment.PROTECTION_FALL,1,false);
        }
        slime.setItemMeta(meta);

        player.getInventory().addItem(slime);
        ChatUtil.sendConfigMessage(player,"Messages.receiving_item", Map.of("%item%", Plugin.getInstance().getConfig().getString("Slime.Name")));
    }



}
