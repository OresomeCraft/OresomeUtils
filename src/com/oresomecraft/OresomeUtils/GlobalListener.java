package com.oresomecraft.OresomeUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class GlobalListener implements Listener {

    public GlobalListener() {
        Bukkit.getPluginManager().registerEvents(this, OresomeUtils.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSignChange(SignChangeEvent event) {
        final String colourChar = "&";
        final String possibleColours = "0123456789abcdefklmnor";
        Player player = event.getPlayer();
        for (int forInt = 0; forInt < 4; forInt++) {
            if (event.getLine(forInt).isEmpty()) continue;
            String[] splitLine = event.getLine(forInt).split(colourChar);
            String newLine = splitLine[0];
            for (int i = 1; i < splitLine.length; i++) {
                int col;
                if (splitLine[i].length() == 0
                        || (col = possibleColours.indexOf(splitLine[i].toLowerCase().charAt(0))) == -1
                        || splitLine[i].length() <= 1) {
                    newLine += colourChar;
                } else {
                    newLine += "\u00A7";
                }
                newLine += splitLine[i];
            }
            event.setLine(forInt, newLine);
        }
    }

}
