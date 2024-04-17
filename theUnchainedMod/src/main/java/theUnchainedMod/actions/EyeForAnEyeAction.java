package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theUnchainedMod.patches.RelayHelpers;

public class EyeForAnEyeAction extends AbstractGameAction {


    public EyeForAnEyeAction(int amount, AbstractCreature p) {
        this.amount = amount;
        this.target = p;
    }

    @Override
    public void update() {
        if (RelayHelpers.thisTurnRelayedDamage.get(target) > 0 || RelayHelpers.nextTurnRelayedDamage.get(target) > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new StrengthPower(target, amount), amount));
        }
        this.isDone = true;
    }
}
