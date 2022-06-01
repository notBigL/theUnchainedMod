package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LoseRelayedDamageAction extends AbstractGameAction {

    public LoseRelayedDamageAction(int relayedDamageToLose) {
        this.amount = relayedDamageToLose;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasPower("theUnchainedMod:RelayedDamagePower")) {
            AbstractPower relayedDamage = player.getPower("theUnchainedMod:RelayedDamagePower");
            if (this.amount < relayedDamage.amount) {
                relayedDamage.reducePower(this.amount);
            } else {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, relayedDamage));
            }
        }
        this.isDone = true;
    }
}
