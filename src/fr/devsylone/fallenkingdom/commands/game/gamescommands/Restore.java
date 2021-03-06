package fr.devsylone.fallenkingdom.commands.game.gamescommands;

import fr.devsylone.fallenkingdom.commands.ArgumentParser;
import fr.devsylone.fallenkingdom.commands.abstraction.Argument;
import fr.devsylone.fallenkingdom.commands.abstraction.CommandPermission;
import fr.devsylone.fallenkingdom.commands.abstraction.CommandResult;
import fr.devsylone.fallenkingdom.commands.abstraction.FkCommand;
import fr.devsylone.fallenkingdom.utils.Messages;
import org.bukkit.command.CommandSender;

import fr.devsylone.fallenkingdom.Fk;
import fr.devsylone.fallenkingdom.exception.FkLightException;
import fr.devsylone.fallenkingdom.game.Game;
import fr.devsylone.fallenkingdom.utils.FkSound;

import java.util.List;

public class Restore extends FkCommand
{
	public Restore()
	{
		super("restore", Argument.list(Argument.create("pause_id", false, "(Par défaut restaure à la dernière pause)")), Messages.CMD_MAP_GAME_RESTORE, CommandPermission.ADMIN);
	}

	@Override
	public CommandResult execute(Fk plugin, CommandSender sender, List<String> args, String label)
	{
		if(!Fk.getInstance().getGame().getState().equals(Game.GameState.PAUSE))
			throw new FkLightException(Messages.CMD_ERROR_NOT_IN_PAUSE);

		int id = -1;
		if (args.size() > 0) {
			id = ArgumentParser.parsePositiveInt(args.get(0), true, Messages.CMD_ERROR_PAUSE_ID);
		}
		id = Fk.getInstance().getPauseRestorer().restoreAll(id); // Si l'id était -1 ça remet le bon
		Fk.broadcast(Messages.CMD_GAME_RESTORE.getMessage().replace("%id%", String.valueOf(id)), FkSound.NOTE_PLING);
		return CommandResult.SUCCESS;
	}
}
