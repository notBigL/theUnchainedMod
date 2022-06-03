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
            int hpLossForEnemy = mo.currentBlock * 2;
            if (hpLossForEnemy > 0) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(player, hpLossForEnemy, DamageInfo.DamageType.HP_LOSS), AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }
}
