package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;

@SpirePatch(
        clz = DamageInfo.class,
        method = SpirePatch.CLASS
)
public class RelayedDamageField {
    public static SpireField<Boolean> relayed = new SpireField<>(() -> false);
}