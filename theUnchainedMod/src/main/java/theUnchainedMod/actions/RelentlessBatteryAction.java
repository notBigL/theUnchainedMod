package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.powers.RelentlessBatteryPower;

public class RelentlessBatteryAction extends AbstractGameAction {
    private final int attackAmount;
    private final AbstractMonster monster;
    private final DamageInfo info;
    private final int damageAmount;


    public RelentlessBatteryAction(int attackAmount, AbstractMonster monster, DamageInfo info, int damageAmount) {
        this.attackAmount = attackAmount;
        this.monster = monster;
        this.info = info;
        this.damageAmount = damageAmount;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new MultiAttackAction(this.attackAmount, this.monster, this.info));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RelentlessBatteryPower(AbstractDungeon.player, 1, new RelentlessBatteryAction(this.attackAmount, this.monster, this.info, this.damageAmount), this.damageAmount, this.attackAmount, AbstractCard.CardType.ATTACK)));
        this.isDone = true;
    }
}
