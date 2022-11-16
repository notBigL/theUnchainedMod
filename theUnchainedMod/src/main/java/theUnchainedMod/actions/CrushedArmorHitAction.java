package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CrushedArmorHitAction extends AbstractGameAction {

    public CrushedArmorHitAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
        this.tickDuration();
        if (this.isDone) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_VERTICAL, false, true));
            CardCrawlGame.sound.play("crushedArmorHit");
        }
    }
}
