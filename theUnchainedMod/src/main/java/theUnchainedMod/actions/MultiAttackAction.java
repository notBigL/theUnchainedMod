package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MultiAttackAction extends AbstractGameAction {

    private final AbstractMonster monster;
    private final DamageInfo info;

    public MultiAttackAction(int attackAmount, AbstractMonster monster, DamageInfo info) {
        this.amount = attackAmount;
        this.monster = monster;
        this.info = info;
    }

    @Override
    public void update() {
        for (int i = 0; i < this.amount; ++i) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.monster, this.info));
        }
        this.isDone = true;
    }
}
