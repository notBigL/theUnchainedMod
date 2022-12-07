package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.vfx.SuffocateViceCrushEffect;

public class SuffocateAction extends AbstractGameAction {


    public SuffocateAction(AbstractMonster m) {
        this.target = m;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        int hpLossForEnemy = target.currentBlock * 2;
        if (hpLossForEnemy > 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SuffocateViceCrushEffect(target.hb.cX, target.hb.cY), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, hpLossForEnemy, DamageInfo.DamageType.HP_LOSS), AttackEffect.NONE));
        }

        this.isDone = true;
    }
}
