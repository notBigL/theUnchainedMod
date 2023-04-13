package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DamagePerChainFinishedAction extends AbstractGameAction {
    private DamageInfo info;

    public DamagePerChainFinishedAction(AbstractCreature target, DamageInfo info, int count, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.amount = count;
    }

    public void update() {
        this.isDone = true;
        if (this.target != null && !target.isDeadOrEscaped()) {
                for (int i = 0; i < amount; ++i) {
                    this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
            }
        }

    }
}
