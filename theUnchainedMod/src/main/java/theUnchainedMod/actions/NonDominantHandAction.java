package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NonDominantHandAction extends AbstractGameAction {

    private final AbstractPlayer player;
    private final AbstractMonster monster;
    private final int damageAmount;


    public NonDominantHandAction(AbstractPlayer p, AbstractMonster m, int damage) {
        this.player = p;
        this.monster = m;
        this.damageAmount = damage;
    }

    @Override
    public void update() {
        if (player.hasPower("Weakened")) {
            int weakAmount = player.getPower("Weakened").amount;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, damageAmount * weakAmount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        if (player.hasPower("Frail")) {
            int frailAmount = player.getPower("Frail").amount;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, damageAmount * frailAmount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        isDone = true;
    }
}
