package net.garagecloud.basical.command;

import lombok.Getter;
import lombok.Setter;
import net.garagecloud.basical.command.manager.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

@Getter
public abstract class BaseCommand<S extends CommandSender>
        extends Command
        implements CommandExecutor {

    @Setter
    private String permission;


    /**
     * На тот случай, если при регистрации команды
     * нужно указывать только 1 алиас
     *
     * @param command - алиас
     */
    public BaseCommand(String command) {
        this(command, new String[0]);
    }

    /**
     * На тот случай, если у команды несколько
     * вариаций алиасов
     *
     * @param command - главный алиас
     * @param aliases - алиасы
     */
    public BaseCommand(String command, String... aliases) {
        this(false, command, aliases);
    }

    /**
     * На тот случай, если у команды несколько
     * вариаций алиасов
     *
     * @param command - главный алиас
     * @param aliases - алиасы
     */
    public BaseCommand(boolean constructorRegister, String command, String... aliases) {
        super(command, "Register by GarageAPI", ("/").concat(command), Arrays.asList(aliases));

        if (constructorRegister) {
            CommandManager.INSTANCE.registerCommand(this, command, aliases);
        }
    }

    @Override
    @SuppressWarnings("all")
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        Class<S> senderClass = (Class<S>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

        if (!senderClass.isAssignableFrom(CommandSender.class)) {
            boolean senderIsPlayer = senderClass.isAssignableFrom(Player.class);

            if (!(commandSender instanceof Player) && senderIsPlayer) {
                return true;
            }

            if (commandSender instanceof Player && !senderIsPlayer) {
                return true;
            }
        }

        if (commandSender instanceof Player) {
          if (!commandSender.hasPermission(permission)) {
              //TODO: Logic sendMessage
              return true;
          }

        }

        onExecute((S) commandSender, args);
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        return true;
    }

    /**
     * execute команды
     *
     * @param commandSender - отправитель
     * @param args - аргументы команды
     */
    protected abstract void onExecute(S commandSender, String[] args);
}


