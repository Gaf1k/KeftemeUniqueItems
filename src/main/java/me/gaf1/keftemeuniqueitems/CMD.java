package me.gaf1.keftemeuniqueitems;

import me.gaf1.keftemeuniqueitems.items.gunpowder.GunPowderManager;
import me.gaf1.keftemeuniqueitems.items.membrane.MembraneManager;
import me.gaf1.keftemeuniqueitems.items.rose.RoseManager;
import me.gaf1.keftemeuniqueitems.items.slime.SlimeManager;
import me.gaf1.keftemeuniqueitems.items.snow.SnowManager;
import me.gaf1.keftemeuniqueitems.utils.ChatUtil;
import me.gaf1.keftemeuniqueitems.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CMD implements TabExecutor {

    private Player player1;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            System.out.println("Тебе нельзя!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0){
            ChatUtil.sendMessage(player,ConfigManager.instance.getString("Messages.not_enough_args"));
            return true;
        }


        if (args[0].equalsIgnoreCase("reload")){
            Plugin.getInstance().reloadConfig();
            ChatUtil.sendMessage(player,ConfigManager.instance.getString("Messages.reload_config"));
            return true;
        }
        else if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 1) {
                ChatUtil.sendMessage(player, ConfigManager.instance.getString("messages.not_enough_items"));
                return true;
            }



            if (args.length == 3){
                player1 = Bukkit.getPlayer(args[2]);
                if (player1 == null){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cТакого игрока не существует либо он оффлайн"));
                    return true;
                }
                String material = args[1].substring(0,1).toUpperCase() + args[1].substring(1).toLowerCase();
                ChatUtil.sendConfigMessage(player,"Messages.give_item", Map.of("%item%", Plugin.getInstance().getConfig().getString(material+".Name"),"%player%",player1.getName()));
            }


            if (args[1].equalsIgnoreCase("snowball")) {
                if (args.length == 3){
                    SnowManager.instance.giveSnow(player1);
                    return true;
                }
                SnowManager.instance.giveSnow(player);
                return true;
            } else if (args[1].equalsIgnoreCase("rose")) {
                if (args.length == 3){
                    RoseManager.instance.giveRose(player1);
                    return true;
                }
                RoseManager.instance.giveRose(player);
                return true;
            } else if (args[1].equalsIgnoreCase("slime")) {
                if (args.length == 3){
                    SlimeManager.instance.giveSlime(player1);
                    return true;
                }
                SlimeManager.instance.giveSlime(player);
                return true;
            } else if (args[1].equalsIgnoreCase("powder")) {
                if (args.length == 3){
                    GunPowderManager.instance.givePowder(player1);
                    return true;
                }
                GunPowderManager.instance.givePowder(player);
                return true;
            } else if (args[1].equalsIgnoreCase("membrane")) {
                if (args.length == 3){
                    MembraneManager.instance.giveMembrane(player1);
                    return true;
                }
                MembraneManager.instance.giveMembrane(player);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)){
            return null;
        }

        Player player = (Player) sender;
        if (args.length == 1){
            if (player.isOp()) {
                return List.of("reload", "give");
            }
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give")){
            return List.of("slime","rose","snowball","powder","membrane");
        }
        return null;
    }
}
