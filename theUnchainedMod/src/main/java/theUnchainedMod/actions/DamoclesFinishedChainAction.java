package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theUnchainedMod.vfx.SwordOfDamoclesEffect;

public class DamoclesFinishedChainAction extends AbstractGameAction {
    private static int strengthLoss;
    public DamoclesFinishedChainAction(int damageAmount, int loseStrengthAmount) {
        this.amount = damageAmount;
        strengthLoss = loseStrengthAmount;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player,  amount, DamageInfo.DamageType.HP_LOSS)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -strengthLoss), -this.strengthLoss));

        SwordOfDamoclesEffect.SwordFallsDown();
        this.isDone = true;
    }
}
