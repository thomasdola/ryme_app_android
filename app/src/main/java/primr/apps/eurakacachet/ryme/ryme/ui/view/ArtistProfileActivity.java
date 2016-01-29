package primr.apps.eurakacachet.ryme.ryme.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import primr.apps.eurakacachet.ryme.ryme.R;

public class ArtistProfileActivity extends AppCompatActivity {

    public static TabLayout sTabLayout;
    public static ViewPager sViewPager;
    private Bitmap mOldBitmap;
    private ImageView mHeader;
    public static String POSITION = "POSITION";
    public static int sItemCount = 2;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, sTabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        initBlurCollapsingImageHeader();
//        mHeader = (ImageView) findViewById(R.uuid.artist_profile_header);
//        mOldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shattawale);
//        mHeader.setImageBitmap(mOldBitmap);

        sViewPager = (ViewPager) findViewById(R.id.artist_profile_view_pager);
        sViewPager.setAdapter(new ArtistProfilePageAdapter(getSupportFragmentManager()));
        sViewPager.setCurrentItem(0);

        sTabLayout = (TabLayout) findViewById(R.id.artist_profile_tab_layout);
        sTabLayout.setupWithViewPager(sViewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.artist_profile_toolbar_layout);
        collapsingToolbarLayout.setTitle("Shata Wale");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

//    private void initBlurCollapsingImageHeader() {
//        mHeader = (ImageView) findViewById(R.uuid.artist_profile_header);
//        mOldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shattawale);
//        Bitmap blurredBitmap = BlurBuilder.blur(this, mOldBitmap);
//        mHeader.setImageBitmap(blurredBitmap);
//    }


    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, ArtistProfileActivity.class);
        return intent;
    }


    class ArtistProfilePageAdapter extends FragmentPagerAdapter {

        public ArtistProfilePageAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            switch (position){
                case 0:
                    return new ArtistNewTracksFragment();
                case 1:
                    return new ArtistAllTracksFragment();
            }

            return null;
        }

        @Override
        public int getCount(){
            return sItemCount;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "Latest Releases";
                case 1:
                    return "All Tracks";
            }
            return null;
        }
    }


    private Bitmap blurImage(Bitmap input){
        Bitmap outputBitmap = Bitmap.createBitmap(input.getWidth(), input.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(outputBitmap);
        Paint paint = new Paint();
        ColorFilter filter = new LightingColorFilter(0xff727272, 0x00000000);
        paint.setColorFilter(filter);

        RenderScript rs = RenderScript.create(ArtistProfileActivity.this);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));;
        Allocation tmpIn = Allocation.createFromBitmap(rs, input);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(25.f);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        c.drawBitmap(outputBitmap, 0, 0, paint);
        return outputBitmap;
    }


}
