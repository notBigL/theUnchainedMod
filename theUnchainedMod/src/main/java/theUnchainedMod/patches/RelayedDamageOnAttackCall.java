package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theUnchainedMod.powers.RelayPower;
import theUnchainedMod.powers.TiedToAnEnemyPower;

@SpirePatch(clz = AbstractCreature.class, method = "decrementBlock")
public class RelayedDamageOnAttackCall {

    @SpirePostfixPatch()
    public static int relayedDmgOnAttCall(int damage, AbstractCreature player, DamageInfo info, int damageAmount) {
        if (player.hasPower("theUnchainedMod:TiedToAnEnemyPower") && damageAmount > 0) {
            TiedToAnEnemyPower tTAEP = (TiedToAnEnemyPower) player.getPower("theUnchainedMod:TiedToAnEnemyPower");
            tTAEP.damageEnemyWhenHit(info, damageAmount);
        }

        if (player.hasPower("theUnchainedMod:RelayPower")) {
            RelayPower relayPower = (RelayPower) player.getPower("theUnchainedMod:RelayPower");
            damage = relayPower.relayDamageWhenAttacked(info, damage);
        }
        return damage;
    }
}
