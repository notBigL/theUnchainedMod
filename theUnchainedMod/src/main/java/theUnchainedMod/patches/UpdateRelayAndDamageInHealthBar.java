package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "updateHealthBar"
)
public class UpdateRelayAndDamageInHealthBar {


    @SpirePostfixPatch
    public static void updateRelayWithHealthBarUpdate(AbstractCreature creature) {
        RelayHelpers.updateRelayAnimations(creature);
        RelayHelpers.updateNextTurnRelayedDamageAnimations(creature);
        RelayHelpers.updateThisTurnRelayedDamageAnimations(creature);
    }
}
