package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShatteringSwingAction extends AbstractGameAction {

    public ShatteringSwingAction(int damage, AbstractMonster monster, AbstractPlayer player) {
        this.amount = damage;
        this.target = monster;
        this.source = player;

    }

    @Override
    public void update() {
        boolean hasBlock = this.target.currentBlock != 0;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.NORMAL)));
        if(hasBlock) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.NORMAL)));
        }
        this.isDone = true;
    }
}
