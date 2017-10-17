package aademo.superawesome.tv.awesomeadsdemo.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.main.MainActivity;
import aademo.superawesome.tv.awesomeadsdemo.library.models.LogedUser;
import aademo.superawesome.tv.awesomeadsdemo.workers.UserWorker;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SALoadScreen;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.UsernameTextView) TextView username;
    @BindView(R.id.PasswordTextView) TextView password;
    @BindView(R.id.ButtonLogin) Button login;

    private LoginModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Observable<String> rxUsername = RxTextView.textChanges(username).map(charSequence -> charSequence.toString().trim());
        Observable<String> rxPassword = RxTextView.textChanges(password).map(charSequence -> charSequence.toString().trim());

        Observable.combineLatest(rxUsername, rxPassword, LoginModel::new)
                .doOnNext(loginModel -> model = loginModel)
                .map(LoginModel::isValid)
                .subscribe(login::setEnabled);

        RxView.clicks(login)
                .doOnNext(aVoid -> SALoadScreen.getInstance().show(LoginActivity.this))
                .flatMap(aVoid -> UserWorker.loginUser(LoginActivity.this, model.getUsername(), model.getPassword()).toObservable())
                .doOnNext(logedUser -> SALoadScreen.getInstance().hide())
                .doOnError(throwable -> SALoadScreen.getInstance().hide())
                .subscribe(logedUser -> gotoMain(), throwable -> authError());
    }

    private void gotoMain () {
        this.startActivity(new Intent(this, MainActivity.class));
    }

    private void authError() {
        SAAlert.getInstance().show(
                this,
                getString(R.string.page_login_popup_more_title),
                getString(R.string.page_login_popup_more_message),
                getString(R.string.page_login_popup_more_ok_button),
                null,
                false,
                0,
                null);
    }
}
