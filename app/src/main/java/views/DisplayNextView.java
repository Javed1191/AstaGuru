package views;

import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public final class DisplayNextView implements Animation.AnimationListener {
private boolean mCurrentView;
LinearLayout image1;
    LinearLayout image2;

public DisplayNextView(boolean currentView, LinearLayout image1, LinearLayout image2) {
mCurrentView = currentView;
this.image1 = image1;
this.image2 = image2;
}

public void onAnimationStart(Animation animation) {
}

public void onAnimationEnd(Animation animation) {
image1.post(new SwapViews(mCurrentView, image1, image2));
}

public void onAnimationRepeat(Animation animation) {
}
}