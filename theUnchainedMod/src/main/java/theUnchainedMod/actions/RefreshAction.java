package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.patches.RelayHelpers;

public class RefreshAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public RefreshAction(AbstractPlayer player) {
        this.player = player;
    }


    @Override
    public void update() {
        int relay = 0;

        relay += RelayHelpers.thisTurnRelayedDamage.get(player);
        RelayHelpers.loseThisTurnRelayedDamage(false, player);
        relay += RelayHelpers.nextTurnRelayedDamage.get(player);
        RelayHelpers.loseNextTurnRelayedDamage(false, player);

        if (relay > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainRelayAction(player, relay));
        }
        this.isDone = true;
    }
}
