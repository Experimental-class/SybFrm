package custom;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Created by Tyhj on 2016/9/27.
 */

public class MyFunction {
    //轮廓
    public static ViewOutlineProvider getOutline(boolean b, final int x){
        if(b) {
            return  new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    //outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                    outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }else {
            return new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                    //outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }

    }

}
