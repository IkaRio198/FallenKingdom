package fr.devsylone.fallenkingdom.commands.teams;

import fr.devsylone.fallenkingdom.MockUtils;
import fr.devsylone.fallenkingdom.commands.CommandTest;
import fr.devsylone.fallenkingdom.commands.abstraction.CommandResult;
import fr.devsylone.fkpi.FkPI;
import fr.devsylone.fkpi.teams.Team;
import fr.devsylone.fkpi.util.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class TeamCommandTest extends CommandTest {

    @Test
    public void create() {
        assertRun("team create purple");
        Team purpleTeam = FkPI.getInstance().getTeamManager().getTeam("purple");
        assertNotNull(purpleTeam);
        assertEquals("purple", purpleTeam.getName());
        assertEquals(Color.VIOLET, purpleTeam.getColor());

        assertRun("team create purple", CommandResult.STATE_ERROR);
    }

    @Test
    public void remove() {
        FkPI.getInstance().getTeamManager().createTeam("DemoR");
        FkPI.getInstance().getTeamManager().getTeam("DemoR").addPlayer(MockUtils.getConstantPlayer().getName());
        assertRun("team remove demor");
        assertRun("team remove unknown", CommandResult.STATE_ERROR);
    }

    @Test
    public void addPlayer() {
        FkPI.getInstance().getTeamManager().createTeam("Demo");
        assertRun(MockUtils.getConstantPlayer(), "team addPlayer " + MockUtils.getConstantPlayer().getName() + " DEmo");
        if (!FkPI.getInstance().getTeamManager().getTeam("Demo").getPlayers().contains(MockUtils.getConstantPlayer().getName()))
            fail();
        FkPI.getInstance().getTeamManager().removeTeam("Demo");
    }

    @Test
    public void removePlayer() {
        FkPI.getInstance().getTeamManager().createTeam("Demo");
        FkPI.getInstance().getTeamManager().getTeam("Demo").addPlayer(MockUtils.getConstantPlayer().getName());
        FkPI.getInstance().getTeamManager().getTeam("Demo").getPlayers().forEach(System.out::println);
        assertRun(MockUtils.getConstantPlayer(), "team removePlayer " + MockUtils.getConstantPlayer().getName());
        FkPI.getInstance().getTeamManager().removeTeam("Demo");
    }

    @Test
    public void setColor() {
        FkPI.getInstance().getTeamManager().createTeam("FallenKingdom");
        assertRun("team setColor FallenKingdom green");
        assertRun("team setColor unknown green", CommandResult.STATE_ERROR);
    }
}
