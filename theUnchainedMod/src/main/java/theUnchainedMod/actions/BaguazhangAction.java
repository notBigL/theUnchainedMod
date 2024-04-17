package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BaguazhangAction extends AbstractGameAction {

    private final AbstractMonster m;
    private final int enemyDamageAmount;

    public BaguazhangAction(AbstractPlayer p, AbstractMonster m, int blockAmount, int enemyDamageAmount) {
        this.source = p;
        this.m = m;
        this.amount = blockAmount;
        this.enemyDamageAmount = enemyDamageAmount;
    }

    @Override
    public void update() {
        if (m != null && (m.getIntentDmg() > enemyDamageAmount && (m.intent == AbstractMonster.Intent.ATTACK ||
                m.intent == AbstractMonster.Intent.ATTACK_BUFF || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEFEND))) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, amount));
        }
        this.isDone = true;
    }
}
