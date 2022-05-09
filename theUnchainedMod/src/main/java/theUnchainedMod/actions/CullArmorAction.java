package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CullArmorAction extends AbstractGameAction {


    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            int blockOfEnemy = mo.currentBlock;
            if (blockOfEnemy > 0) {
                this.addToBot(new RemoveAllBlockAction(mo, player));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(player, blockOfEnemy, DamageInfo.DamageType.HP_LOSS), AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }
}
