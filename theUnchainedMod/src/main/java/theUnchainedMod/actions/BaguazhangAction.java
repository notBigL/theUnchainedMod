package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BaguazhangAction extends AbstractGameAction {

    private final AbstractMonster targetMonster;
    private final int damageAmount;

    public BaguazhangAction(AbstractPlayer p, AbstractMonster m, int blockAmount, int damageAmount) {
        this.source = p;
        this.targetMonster = m;
        this.amount = blockAmount;
        this.damageAmount = damageAmount;
    }

    @Override
    public void update() {
        if (targetMonster != null && (targetMonster.getIntentDmg() < damageAmount || targetMonster.intent == AbstractMonster.Intent.BUFF ||
                targetMonster.intent == AbstractMonster.Intent.DEBUFF || targetMonster.intent == AbstractMonster.Intent.STRONG_DEBUFF ||
                targetMonster.intent == AbstractMonster.Intent.DEBUG || targetMonster.intent == AbstractMonster.Intent.DEFEND ||
                targetMonster.intent == AbstractMonster.Intent.DEFEND_DEBUFF || targetMonster.intent == AbstractMonster.Intent.DEFEND_BUFF ||
                targetMonster.intent == AbstractMonster.Intent.ESCAPE || targetMonster.intent == AbstractMonster.Intent.MAGIC ||
                targetMonster.intent == AbstractMonster.Intent.NONE || targetMonster.intent == AbstractMonster.Intent.SLEEP ||
                targetMonster.intent == AbstractMonster.Intent.STUN || targetMonster.intent == AbstractMonster.Intent.UNKNOWN)) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, amount));
        }
        this.isDone = true;
    }
}
