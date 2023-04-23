package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.BufferPower;
import theUnchainedMod.powers.TiedToAnEnemyPower;

@SpirePatch(clz = AbstractCreature.class, method = "decrementBlock")
public class RelayedDamageOnAttackCall {

    @SpirePostfixPatch()
    public static int relayedDmgOnAttCall(int damage, AbstractCreature creature, DamageInfo info, int damageAmount) {
        if (creature.hasPower(TiedToAnEnemyPower.POWER_ID) && !creature.hasPower(BufferPower.POWER_ID) && damageAmount > 0) {
            TiedToAnEnemyPower tTAEP = (TiedToAnEnemyPower) creature.getPower(TiedToAnEnemyPower.POWER_ID);
            tTAEP.damageEnemyWhenHit(info, damageAmount);
        }

        if (RelayHelpers.currentRelay.get(creature) > 0 && info.type != DamageInfo.DamageType.HP_LOSS) {
            damage = RelayHelpers.relayDamageWhenAttacked(info, damage, creature);
        }
        return damage;
    }
}
