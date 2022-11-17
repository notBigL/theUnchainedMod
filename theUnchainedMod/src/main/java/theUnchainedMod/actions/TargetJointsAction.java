package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TargetJointsAction extends AbstractGameAction {
    private final AbstractMonster targetMonster;

    public TargetJointsAction(AbstractMonster m, int amount) {
        targetMonster = m;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.targetMonster != null && this.targetMonster.getIntentBaseDmg() >= 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyCrushedArmorAction(targetMonster, amount));
        }
        isDone = true;
    }
}
