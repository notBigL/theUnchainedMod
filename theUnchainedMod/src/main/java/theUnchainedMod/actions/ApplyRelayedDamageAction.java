package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theUnchainedMod.patches.RelayHelpers;
import theUnchainedMod.patches.RelayedDmgSum;
import theUnchainedMod.relics.TotemOfPain;

public class ApplyRelayedDamageAction extends AbstractGameAction {

    public ApplyRelayedDamageAction(AbstractCreature creature, int amount) {
        this.target = creature;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (target != null && amount > 0) {
            RelayedDmgSum.relayedDamageSum.set(AbstractDungeon.actionManager, RelayedDmgSum.relayedDamageSum.get(AbstractDungeon.actionManager) + amount);
            RelayHelpers.addNextTurnRelayedDamage(amount, target);
        }
        if (target instanceof AbstractPlayer) {
            if (((AbstractPlayer) target).hasRelic(TotemOfPain.ID)) {
                for(AbstractRelic r : AbstractDungeon.player.relics)
                    if(r instanceof TotemOfPain) r.counter += amount;
            }
        }

        isDone = true;
    }
}
