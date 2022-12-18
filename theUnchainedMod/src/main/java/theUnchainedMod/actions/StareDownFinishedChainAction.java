package theUnchainedMod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

public class StareDownFinishedChainAction extends AbstractGameAction {


    public StareDownFinishedChainAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GiantEyeEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.87F, 0.83F, 0.18F, 1.0F))));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.amount, false), this.amount, true, AttackEffect.NONE));
        }
        this.isDone = true;
    }
}
