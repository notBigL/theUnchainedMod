package theUnchainedMod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderRedHealthBar"
)
public class renderHealthBarPink {

    @SpireInsertPatch(rloc = 5)
    public static void renderHealthBarPinkIfRelayIsThere(AbstractCreature creature, @ByRef SpriteBatch[] sb, float x, float y) {
        if(RelayHelpers.currentRelay.get(creature) > 0 && creature.currentBlock == 0) {
            sb[0].setColor(0.913F, 0.3F, 0.46F, 1.0F);
        }
    }
}
