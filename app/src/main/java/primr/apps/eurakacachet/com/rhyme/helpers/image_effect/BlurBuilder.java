package primr.apps.eurakacachet.com.rhyme.helpers.image_effect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

public class BlurBuilder {

    private static final float BITMAP_SCALE = 0.2f;
    private static final float BLUR_RADIUS = 25.f;

    public static Bitmap blur(Context context, Bitmap image) {

        Bitmap outputBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(outputBitmap);
        Paint paint = new Paint();
        ColorFilter filter = new LightingColorFilter(0xff727272, 0x00000000);
        paint.setColorFilter(filter);

        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

//        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
//        outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, image);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        c.drawBitmap(outputBitmap, 0, 0, paint);
        return outputBitmap;
    }
}
