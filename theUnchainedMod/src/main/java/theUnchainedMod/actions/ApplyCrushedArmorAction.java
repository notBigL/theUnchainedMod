package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theUnchainedMod.powers.CrushedArmorPower;
import theUnchainedMod.relics.CrushingGauntlets;
import theUnchainedMod.relics.Wrench;

public class ApplyCrushedArmorAction extends AbstractGameAction {
//TODO: Make the base a variable

    public ApplyCrushedArmorAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.source = AbstractDungeon.player;
    }

    @Override
    public void update() {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof CrushingGauntlets) {
                int dmg = amount * 3;
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(source, dmg, DamageInfo.DamageType.HP_LOSS), AttackEffect.SLASH_HORIZONTAL));
                r.flash();
                this.isDone = true;
                return;
            }
        }
        boolean hasArtifact = target.hasPower(ArtifactPower.POWER_ID);
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new CrushedArmorPower(target, source, amount)));
        if (!hasArtifact) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof Wrench) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(source, 4, DamageInfo.DamageType.HP_LOSS), AttackEffect.SLASH_HORIZONTAL));
                    r.flash();
                    break;
                }
            }
        }
        this.isDone = true;
    }
}
