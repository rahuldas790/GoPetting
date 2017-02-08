package rahulkumardas.gopetting;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Rahul Kumar Das on 08-02-2017.
 */

public class GoApplication extends Application {

    public static GoogleApiClient mGoogleApiClient;
    public static int label=0;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();

    }

    public static Context getContext(){
        return  context;
    }

}
