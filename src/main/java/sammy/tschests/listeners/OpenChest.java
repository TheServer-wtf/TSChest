package sammy.tschests.listeners;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import sammy.tschests.TSChests;

import java.util.HashMap;
import java.util.Map;

public class OpenChest implements Listener {
    public TSChests plugin;
    public Map<Material, Boolean> ischest = new HashMap<Material,Boolean>();
    public Map<String, Long> cooldown = new HashMap<String, Long>();
    public OpenChest(TSChests plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        ischest.put(Material.CHEST, true);
        ischest.put(Material.BARREL, true);
        ischest.put(Material.BLAST_FURNACE, true);
        ischest.put(Material.BREWING_STAND, true);
        ischest.put(Material.DISPENSER, true);
        ischest.put(Material.DROPPER, true);
        ischest.put(Material.FURNACE, true);
        ischest.put(Material.HOPPER, true);
        ischest.put(Material.SHULKER_BOX, true);
        ischest.put(Material.SMOKER, true);
        ischest.put(Material.TRAPPED_CHEST, true);
        ischest.put(Material.BLACK_SHULKER_BOX, true);
        ischest.put(Material.BLUE_SHULKER_BOX, true);
        ischest.put(Material.CYAN_SHULKER_BOX, true);
        ischest.put(Material.BROWN_SHULKER_BOX, true);
        ischest.put(Material.GRAY_SHULKER_BOX, true);
        ischest.put(Material.GREEN_SHULKER_BOX, true);
        ischest.put(Material.LIGHT_BLUE_SHULKER_BOX, true);
        ischest.put(Material.LIGHT_GRAY_SHULKER_BOX, true);
        ischest.put(Material.LIME_SHULKER_BOX, true);
        ischest.put(Material.MAGENTA_SHULKER_BOX, true);
        ischest.put(Material.ORANGE_SHULKER_BOX, true);
        ischest.put(Material.PINK_SHULKER_BOX, true);
        ischest.put(Material.PURPLE_SHULKER_BOX, true);
        ischest.put(Material.WHITE_SHULKER_BOX, true);
        ischest.put(Material.YELLOW_SHULKER_BOX, true);
        ischest.put(Material.RED_SHULKER_BOX, true);
    }

    @EventHandler
    public void OnOpen(PlayerInteractEvent event){
        Block b = event.getClickedBlock();
        Player p = event.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if(b != null) {
            if (ischest.get(b.getType()) != null) {
                if(p.hasPermission("tschest.use")){
                    if(i.hasItemMeta()){
                        double incooldown = ((cooldown.getOrDefault(p.getName(),0L)/1000L)+1L) - (System.currentTimeMillis()/1000);
                        if (i.getItemMeta().getDisplayName().equalsIgnoreCase("Chest Key")) {
                            event.setCancelled(true);
                            if(incooldown < 0) {
                                Boolean exists = plugin.getConfig().getBoolean(b.getX() + "-" + b.getY() + "-" + b.getZ());
                                if (exists) {
                                    plugin.getConfig().set(b.getX() + "-" + b.getY() + "-" + b.getZ(), false);
                                    p.sendMessage(ChatColor.RED + "LOCKED!");
                                } else {
                                    plugin.getConfig().set(b.getX() + "-" + b.getY() + "-" + b.getZ(), true);
                                    p.sendMessage(ChatColor.GREEN + "UNLOCKED!");
                                }
                                plugin.saveConfig();
                                cooldown.put(p.getName(), System.currentTimeMillis());
                            }
                        }
                    }
                }
                else {
                    Boolean isopen = plugin.getConfig().getBoolean(b.getX()+"-"+b.getY()+"-"+b.getZ());
                    if(!isopen) event.setCancelled(true);
                }
            }
        }
    }


}
