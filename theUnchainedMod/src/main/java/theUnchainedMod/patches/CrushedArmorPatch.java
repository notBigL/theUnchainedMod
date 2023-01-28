package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.powers.ArcaneTransmutationPower;
import theUnchainedMod.powers.CrushedArmorPower;

@SpirePatch(clz = AbstractCreature.class, method = "addBlock")
public class CrushedArmorPatch {

    @SpirePostfixPatch()
    public static void checkForDentedArmor(AbstractCreature creature, int blockAmount) {
        if (creature.hasPower(CrushedArmorPower.POWER_ID)) {
            creature.getPower(CrushedArmorPower.POWER_ID).onSpecificTrigger();
        }
        if (AbstractDungeon.player.hasPower(ArcaneTransmutationPower.POWER_ID) && creature instanceof AbstractMonster) {
            AbstractDungeon.player.getPower(ArcaneTransmutationPower.POWER_ID).onSpecificTrigger();
        }

    }
}
