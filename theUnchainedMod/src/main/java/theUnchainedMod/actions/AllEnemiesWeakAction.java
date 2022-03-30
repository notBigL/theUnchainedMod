package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class AllEnemiesWeakAction extends AbstractGameAction {


    public AllEnemiesWeakAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.amount, false), this.amount, true, AttackEffect.NONE));
            }
        }
        this.isDone = true;
    }
}
