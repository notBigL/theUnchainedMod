package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.lwjgl.Sys;
import theUnchainedMod.powers.ChainsFinishedThisTurnPower;
import theUnchainedMod.powers.MomentumPower;
import theUnchainedMod.powers.SwirlsHitAllEnemiesPower;

import java.util.Iterator;

public class DamagePerChainFinishedAction extends AbstractGameAction {
    private DamageInfo info;

    public DamagePerChainFinishedAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public DamagePerChainFinishedAction(AbstractCreature target, DamageInfo info) {
        this(target, info, AttackEffect.NONE);
    }

    public void update() {
        this.isDone = true;
        if (this.target != null && this.target.currentHealth > 0) {
            if (AbstractDungeon.player.hasPower(ChainsFinishedThisTurnPower.POWER_ID)) {
                for (int i = 0; i < AbstractDungeon.player.getPower(ChainsFinishedThisTurnPower.POWER_ID).amount; ++i) {
                    this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
                }
            }
        }

    }
}
