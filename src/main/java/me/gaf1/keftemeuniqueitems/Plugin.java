package me.gaf1.keftemeuniqueitems;

import me.gaf1.keftemeuniqueitems.items.gunpowder.GunPowderEvents;
import me.gaf1.keftemeuniqueitems.items.membrane.MembraneEvents;
import me.gaf1.keftemeuniqueitems.items.rose.RoseEvents;
import me.gaf1.keftemeuniqueitems.items.slime.SlimeEvents;
import me.gaf1.keftemeuniqueitems.items.snow.SnowEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    public static Plugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new SnowEvents(),this);
        getServer().getPluginManager().registerEvents(new RoseEvents(),this);
        getServer().getPluginManager().registerEvents(new SlimeEvents(),this);
        getServer().getPluginManager().registerEvents(new GunPowderEvents(),this);
        getServer().getPluginManager().registerEvents(new MembraneEvents(),this);

        getCommand("keftemeitems").setExecutor(new CMD());
        getCommand("keftemeitems").setTabCompleter(new CMD());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
