package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz= AbstractDungeon.class, method = "initializeRelicList")
public class AddBoosterPackRelicsPatch {
/*
    @SpirePostfixPatch
    public static void AddBoosterPackRelics(AbstractDungeon dungeon) {
        if(!DefaultMod.UNCHAINED_BOOSTER_PACK_ACTIVATED) return;

        AbstractDungeon.shopRelicPool.add(CrushingGauntlets.ID);

        //Collections.shuffle(AbstractDungeon.commonRelicPool, new java.util.Random(AbstractDungeon.relicRng.randomLong()));
        //Collections.shuffle(AbstractDungeon.uncommonRelicPool, new java.util.Random(AbstractDungeon.relicRng.randomLong()));
        //Collections.shuffle(AbstractDungeon.rareRelicPool, new java.util.Random(AbstractDungeon.relicRng.randomLong()));
        Collections.shuffle(AbstractDungeon.shopRelicPool, new java.util.Random(AbstractDungeon.relicRng.randomLong()));
        //Collections.shuffle(AbstractDungeon.bossRelicPool, new java.util.Random(AbstractDungeon.relicRng.randomLong()));

    }*/
}
