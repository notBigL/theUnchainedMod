package theUnchainedMod.packs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import thePackmaster.packs.AbstractCardPack;
import thePackmaster.packs.PackPreviewCard;
import theUnchainedMod.TheUnchainedMod;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;
import static theUnchainedMod.TheUnchainedMod.makeImagePath;

public abstract class AbstractUnchainedPack extends AbstractCardPack {
    private String previewArtCardID;

    public AbstractUnchainedPack(String id, String previewArt, AbstractCardPack.PackSummary summary) {
        super(id, CardCrawlGame.languagePack.getUIString(id).TEXT[0], CardCrawlGame.languagePack.getUIString(id).TEXT[1], CardCrawlGame.languagePack.getUIString(id).TEXT[2], summary);
        previewArtCardID = previewArt;
        previewPackCard = this.makePreviewCard();
    }

    public PackPreviewCard makePreviewCard() {
        if (previewArtCardID == null) return super.makePreviewCard();
        return new PackPreviewCard(packID, makeCardPath(previewArtCardID.replace(TheUnchainedMod.getModID() + ":", "")+".png"), this);
    }

    public String getHatPath() {
        return makeImagePath("hats/"+packID.replace(TheUnchainedMod.getModID() + ":", "")+".png");
    }
}