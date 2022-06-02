package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.powers.NextTurnRelayedDamagePower;
//TODO fix it so it works with new Relayed Damage
public class RescheduleAction extends AbstractGameAction {
    private static final String relayedDamage = "theUnchainedMod:RelayedDamagePower";
    private final AbstractPlayer player;

    public RescheduleAction(AbstractPlayer p) {
        player = p;
    }

    @Override
    public void update() {
        if(player.hasPower(relayedDamage)) {
            int rescheduleAmount = player.getPower(relayedDamage).amount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, relayedDamage));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new NextTurnRelayedDamagePower(player, player, rescheduleAmount)));
        }
        this.isDone = true;
    }
}
