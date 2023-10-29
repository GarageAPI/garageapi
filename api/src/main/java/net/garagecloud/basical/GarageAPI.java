package net.garagecloud.basical;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.bukkit.plugin.java.JavaPlugin;

@Log4j2
public final class GarageAPI extends JavaPlugin {

    @Getter
    private static GarageAPI instance;

    @Override
    public void onLoad() {
        //todo: Logic load
        instance = this;
    }

    @Override
    public void onEnable() {
        //todo: Logic enable
    }

    @Override
    public void onDisable() {
        //todo: Logic disable
    }
}
