package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeRelicOutlinePath;
import static theUnchainedMod.TheUnchainedMod.makeRelicPath;

public class Carabiner extends CustomRelic {

    public static final String ID = TheUnchainedMod.makeID("Carabiner");


    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Carabiner_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Carabiner_relic.png"));

    public Carabiner() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
