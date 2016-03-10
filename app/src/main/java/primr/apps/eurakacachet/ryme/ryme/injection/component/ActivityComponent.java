package primr.apps.eurakacachet.ryme.ryme.injection.component;


import dagger.Component;
import primr.apps.eurakacachet.ryme.ryme.injection.scope.PerActivity;
import primr.apps.eurakacachet.ryme.ryme.injection.module.ActivityModule;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile.ArtistProfileActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay.ArtistTrackDisplayViewHolder;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay.ArtistTrackListDisplayFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.upload.UploadTrackFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay.ArtistListDisplayFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artistRequest.ArtistRequestViewFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.CategoriesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.CategoryViewHolder;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.inApp.StoredCategoriesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.comment.AddCommentDialogFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.comment.CommentListFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.crop.CropImageActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd.EventAdsFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd.EventParentViewHolder;
import primr.apps.eurakacachet.ryme.ryme.ui.view.followCategory.FollowCategoryActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.main.MainActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.newTrackFeed.NewReleasedTracksFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads.DownloadsFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay.DownloadTrackDisplayFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay.TrackDisplayActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.search.SearchableActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.settings.SettingsActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.settings.SettingsFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.signin.LoginActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.signup.SignUpActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.splash.SplashActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.TrackDisplayFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay.PublicTrackDisplayViewHolder;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay.PublicTrackListDisplayFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trendingTrackFeed.TrendingTracksFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.verify_code.VerifyCodeActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(VerifyCodeActivity verifyCodeActivity);

    void inject(FollowCategoryActivity followCategoryActivity);

    void inject(MainActivity mainActivity);

    void inject(PublicTrackListDisplayFragment publicTrackListDisplayFragment);

    void inject(CategoriesFragment categoriesFragment);

    void inject(ArtistListDisplayFragment artistListDisplayFragment);

    void inject(CategoryViewHolder categoryViewHolder);

    void inject(StoredCategoriesFragment storedCategoriesFragment);

    void inject(PublicTrackDisplayActivity publicTrackDisplayActivity);

    void inject(TrackDisplayFragment trackDisplayFragment);

    void inject(PublicTrackDisplayViewHolder publicTrackDisplayViewHolder);

    void inject(NewReleasedTracksFragment newReleasedTracksFragment);

    void inject(TrendingTracksFragment trendingTracksFragment);

    void inject(ArtistProfileActivity artistProfileActivity);

    void inject(ArtistTrackListDisplayFragment artistTrackListDisplayFragment);

    void inject(EventAdsFragment eventAdsFragment);

    void inject(DownloadsFragment downloadsFragment);

    void inject(TrackDisplayActivity trackDisplayActivity);

    void inject(DownloadTrackDisplayFragment downloadTrackDisplayFragment);

    void inject(CommentListFragment commentListFragment);

    void inject(SettingsFragment settingsFragment);

    void inject(SettingsActivity settingsActivity);

    void inject(UploadTrackFragment uploadTrackFragment);

    void inject(LoginActivity loginActivity);

    void inject(CropImageActivity cropImageActivity);

    void inject(SearchableActivity searchableActivity);

    void inject(ArtistRequestViewFragment artistRequestViewFragment);

    void inject(AddCommentDialogFragment addCommentDialogFragment);

    void inject(ArtistTrackDisplayViewHolder artistTrackDisplayViewHolder);

    void inject(EventParentViewHolder eventParentViewHolder);
}
