package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SpirePatch(clz = AbstractCreature.class, method = "addBlock")
public class DentedArmorPatch {

    @SpirePostfixPatch()
    public static void checkForDentedArmor(AbstractCreature creature, int blockAmount) {
        if(creature.hasPower("theUnchainedMod:DentedArmorPower")) {
            creature.getPower("theUnchainedMod:DentedArmorPower").onSpecificTrigger();
        }

    }
}
