package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AllEnemiesLoseHPAction extends AbstractGameAction {


    public AllEnemiesLoseHPAction(int damageAmount) {
        this.amount = damageAmount;

    }

    @Override
    public void update() {
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.HP_LOSS);
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, info, AttackEffect.SLASH_HEAVY));
        }

        this.isDone = true;
    }
}
