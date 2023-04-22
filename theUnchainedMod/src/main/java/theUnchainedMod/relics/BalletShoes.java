package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeRelicOutlinePath;
import static theUnchainedMod.TheUnchainedMod.makeRelicPath;

public class BalletShoes extends CustomRelic {

    public static final String ID = TheUnchainedMod.makeID("BalletShoes");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BalletShoes_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BalletShoes_relic.png"));

    public BalletShoes() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
