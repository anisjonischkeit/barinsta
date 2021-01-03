package awais.instagrabber.adapters.viewholder.directmessages;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.facebook.drawee.backends.pipeline.Fresco;

import awais.instagrabber.databinding.LayoutDmAnimatedMediaBinding;
import awais.instagrabber.databinding.LayoutDmBaseBinding;
import awais.instagrabber.interfaces.MentionClickListener;
import awais.instagrabber.models.ProfileModel;
import awais.instagrabber.repositories.responses.directmessages.AnimatedMediaFixedHeight;
import awais.instagrabber.repositories.responses.directmessages.AnimatedMediaImages;
import awais.instagrabber.repositories.responses.directmessages.DirectItem;
import awais.instagrabber.repositories.responses.directmessages.DirectItemAnimatedMedia;
import awais.instagrabber.repositories.responses.directmessages.DirectThread;
import awais.instagrabber.utils.NumberUtils;

public class DirectItemAnimatedMediaViewHolder extends DirectItemViewHolder {

    private final LayoutDmAnimatedMediaBinding binding;

    public DirectItemAnimatedMediaViewHolder(@NonNull final LayoutDmBaseBinding baseBinding,
                                             @NonNull final LayoutDmAnimatedMediaBinding binding,
                                             final ProfileModel currentUser,
                                             final DirectThread thread,
                                             final MentionClickListener mentionClickListener,
                                             final View.OnClickListener onClickListener) {
        super(baseBinding, currentUser, thread, onClickListener);
        this.binding = binding;
        setItemView(binding.getRoot());
    }

    @Override
    public void bindItem(final DirectItem item, final MessageDirection messageDirection) {
        removeBg();
        final DirectItemAnimatedMedia animatedMediaModel = item.getAnimatedMedia();
        final AnimatedMediaImages images = animatedMediaModel.getImages();
        if (images == null) return;
        final AnimatedMediaFixedHeight fixedHeight = images.getFixedHeight();
        if (fixedHeight == null) return;
        final String url = fixedHeight.getWebp();
        final Pair<Integer, Integer> widthHeight = NumberUtils.calculateWidthHeight(
                fixedHeight.getHeight(),
                fixedHeight.getWidth(),
                mediaImageMaxHeight,
                mediaImageMaxWidth
        );
        binding.ivAnimatedMessage.setVisibility(View.VISIBLE);
        final ViewGroup.LayoutParams layoutParams = binding.ivAnimatedMessage.getLayoutParams();
        final int width = widthHeight.first != null ? widthHeight.first : 0;
        final int height = widthHeight.second != null ? widthHeight.second : 0;
        layoutParams.width = width;
        layoutParams.height = height;
        binding.ivAnimatedMessage.requestLayout();
        binding.ivAnimatedMessage.setController(Fresco.newDraweeControllerBuilder()
                                                      .setUri(url)
                                                      .setAutoPlayAnimations(true)
                                                      .build());
    }
}
