package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theUnchainedMod.patches.RelayHelpers;

public class RemoveRelayedDamageAction extends AbstractGameAction {
    private final AbstractPlayer player;

    public RemoveRelayedDamageAction(AbstractPlayer p) {
        player = p;
    }

    @Override
    public void update() {
        RelayHelpers.loseNextTurnRelayedDamage(false, player);
        RelayHelpers.loseThisTurnRelayedDamage(false, player);
        this.isDone = true;
    }
}
