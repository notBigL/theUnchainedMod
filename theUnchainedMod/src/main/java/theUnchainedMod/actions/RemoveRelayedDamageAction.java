package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveRelayedDamageAction extends AbstractGameAction {
    private static final String relayedDamage = "theUnchainedMod:RelayedDamagePower";
    private static final String nextTurnRelayedDamage = "theUnchainedMod:NextTurnRelayedDamagePower";
    private final AbstractPlayer player;

    public RemoveRelayedDamageAction(AbstractPlayer p) {
        player = p;
    }

    @Override
    public void update() {
        if (player.hasPower(nextTurnRelayedDamage)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, nextTurnRelayedDamage));
        }
        if (player.hasPower(relayedDamage)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, relayedDamage));
        }
        this.isDone = true;
    }
}
