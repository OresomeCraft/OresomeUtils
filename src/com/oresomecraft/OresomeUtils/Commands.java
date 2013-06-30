package com.oresomecraft.OresomeUtils;

import com.sk89q.minecraft.util.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Commands {

    OresomeUtils plugin;

    public Commands(OresomeUtils pl) {
        plugin = pl;
    }

    @Command(aliases = {"nearby"},
            usage = "/nearby",
            desc = "Get nearby players")
    @CommandPermissions({"oresomeutils.nearby"})
    public void stats(CommandContext args, CommandSender sender) throws CommandException {
        Player p = (Player) sender;
        String nearby = "No one";
        boolean first = true;
        for (Entity e : p.getNearbyEntities(20, 20, 20))
            if (e instanceof Player) {
                if (first) {
                    nearby = "";
                    first = false;
                }
                nearby += ((Player) e).getName() + ", ";
            }

        sender.sendMessage(ChatColor.DARK_AQUA + "Nearby players: " + ChatColor.RESET + nearby);
    }

    @Command(aliases = {"kittycannon"},
            usage = "/kittycannon",
            desc = "Catz!")
    @CommandPermissions({"oresomeutils.kittycannon"})
    public void kittycannon(CommandContext args, CommandSender sender) throws CommandException {
        Player p = (Player) sender;
        final Ocelot e = p.getWorld().spawn(p.getLocation(), Ocelot.class);
        Bukkit.getScheduler().scheduleSyncDelayedTask(OresomeUtils.getInstance(), new Runnable() {
            @Override
            public void run() {
                final Location loc = e.getLocation();
                e.remove();
                loc.getWorld().createExplosion(loc, 0F);
            }
        }, 40);
    }

    @Command(aliases = {"donatorbroadcast"},
            usage = "/donatorbroadcast",
            desc = "donatorbroadcast!")
    @CommandPermissions({"oresomeutils.donatorbroadcast"})
    public void donatorbroadcast(CommandContext args, CommandSender sender) throws CommandException {
        ChatColor AQUA = ChatColor.AQUA;
        ChatColor GREEN = ChatColor.GREEN;
        ChatColor GOLD = ChatColor.GOLD;

        if (args.argsLength() < 1) {
            Bukkit.broadcastMessage(GOLD + "[Donator] " + AQUA + args.getString(0) + GREEN + " is now a Donator! Thanks for supporting us!");
        }
    }

    @Command(aliases = {"sendall"},
            usage = "/sendall",
            desc = "sendall!")
    @CommandPermissions({"oresomeutils.sendall"})
    public void sendall(CommandContext args, CommandSender sender) throws CommandException {
        sender.sendMessage(ChatColor.GOLD + "Sent all users to server: " + args.getString(0));
        for (Player p : Bukkit.getOnlinePlayers()) {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("Connect");
                out.writeUTF(args.getString(0));
            } catch (IOException ex) { /* Impossible */ }
            p.sendPluginMessage(OresomeUtils.getInstance(), "BungeeCord", b.toByteArray());

        }
    }

    @Command(aliases = {"gc", "mem"},
            usage = "/sendall",
            desc = "sendall!")
    @CommandPermissions({"oresomeutils.gc"})
    public void gc(CommandContext args, CommandSender sender) throws CommandException {
        sender.sendMessage(ChatColor.DARK_AQUA + "Uptime: " + ChatColor.RED + EssUtil.formatDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime()));
        Bukkit.dispatchCommand(sender, "tps"); // I'm lazy, just use Spigot's /tps command
        sender.sendMessage(ChatColor.DARK_AQUA + "Max memory: " + ChatColor.RED + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "MB");
        sender.sendMessage(ChatColor.DARK_AQUA + "Allocated memory: " + ChatColor.RED + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "MB");
        sender.sendMessage(ChatColor.DARK_AQUA + "Free memory: " + ChatColor.RED + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + "MB");
        for (World w : Bukkit.getWorlds()) {
            sender.sendMessage(ChatColor.GOLD + "World " + ChatColor.DARK_AQUA + w.getName() + ": Chunks: " + ChatColor.RED + w.getLoadedChunks().length +
                    ChatColor.DARK_AQUA + " Entities: " + ChatColor.RED + w.getEntities().size());
        }
    }


}