package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractCreature.class, method = "applyEndOfTurnTriggers")
public class RelayedDamageCalculations {

    @SpirePostfixPatch
    public static void calculateAndApplyRelayedDamage(AbstractCreature creature) {
        if (RelayHelpers.thisTurnRelayedDamage.get(creature) > 0) {
            int damageAmount = RelayHelpers.thisTurnRelayedDamage.get(creature) / 2;
            DamageInfo damageInfo = new DamageInfo(creature, damageAmount, DamageInfo.DamageType.THORNS);
            RelayedDamageField.relayed.set(damageInfo, true);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(creature, damageInfo, AbstractGameAction.AttackEffect.POISON));
            RelayHelpers.loseThisTurnRelayedDamage(false, creature);
        }
        if (RelayHelpers.nextTurnRelayedDamage.get(creature) > 0) {
            RelayHelpers.addThisTurnRelayedDamage(RelayHelpers.nextTurnRelayedDamage.get(creature), creature);
            RelayHelpers.loseNextTurnRelayedDamage(false, creature);
        }
    }
}
