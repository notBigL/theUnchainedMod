package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.powers.NextTurnRelayedDamagePower;
import theUnchainedMod.powers.RelayedDamagePower;

public class RemoveRelayedDamageAction extends AbstractGameAction {
    private final AbstractPlayer player;

    public RemoveRelayedDamageAction(AbstractPlayer p) {
        player = p;
    }

    @Override
    public void update() {
        if (player.hasPower(NextTurnRelayedDamagePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, NextTurnRelayedDamagePower.POWER_ID));
        }
        if (player.hasPower(RelayedDamagePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, RelayedDamagePower.POWER_ID));
        }
        this.isDone = true;
    }
}
