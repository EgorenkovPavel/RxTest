package com.example.pavel.rxtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RX_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // test();
        testCallable();
    }

    private void test() {
        // create observable
        Observable<String> stringObservable = Observable.fromArray(new String[]{"one", "two", "three"});
        Observable<Integer> integerObservable = Observable.range(10, 4);
        Observable<Long> longObservable = Observable.interval(500, TimeUnit.MILLISECONDS);

        // create observer
        Observer<String> stringObserver = new Observer<String>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        Observer<Integer> integerObserver = new Observer<Integer>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        Observer<Long> longObserver = new Observer<Long>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Long s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        stringObservable.subscribe(stringObserver);
        integerObservable.subscribe(integerObserver);
        longObservable.subscribe(longObserver);
    }

    private void testCallable() {
        Observable.fromCallable(new CallableLongAction("5"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i ->
                    Log.d(TAG,"onNext " + i)
                );
    }

    private int longAction(String text) {
        Log.d(TAG,"longAction");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(text);
    }

    class CallableLongAction implements Callable<Integer> {

        private final String data;

        public CallableLongAction(String data) {
            this.data = data;
        }

        @Override
        public Integer call() throws Exception {
            return longAction(data);
        }
    }

}
