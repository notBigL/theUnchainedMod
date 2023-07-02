package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.powers.RelentlessOnslaughtPower;

public class RelentlessOnslaughtAction extends AbstractGameAction {
    private final int attackAmount;
    private final AbstractMonster monster;
    private final DamageInfo info;
    private final int damageAmount;


    public RelentlessOnslaughtAction(int attackAmount, AbstractMonster monster, DamageInfo info, int damageAmount) {
        this.attackAmount = attackAmount;
        this.monster = monster;
        this.info = info;
        this.damageAmount = damageAmount;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new MultiAttackAction(this.attackAmount, this.monster, this.info));
        if (!isDeadOrEscaped(monster)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RelentlessOnslaughtPower(AbstractDungeon.player, 1, new RelentlessOnslaughtAction(this.attackAmount, this.monster, this.info, this.damageAmount), this.damageAmount, this.attackAmount, AbstractCard.CardType.ATTACK)));
        }
        this.isDone = true;
    }
}
