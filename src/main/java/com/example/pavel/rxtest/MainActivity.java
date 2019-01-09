package com.example.pavel.rxtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RX_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // test();
       // testCallable();
//        testMap();
//        testBuffer();
//        testTake();
//        testSkip();
//        testDistinct();
//        testFilter();
//        testMerge();
//        testZip();
//        testTakeUntil();
//        testAll();
        testAction();
    }

    //Simple
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

    //Callable
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

    //Map
    private void testMap(){
        Function<String, Integer> stringToInteger = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.parseInt(s);
            }
        };

        // create observable
        Observable<Integer> observable = Observable
                .fromArray(new String[]{"1", "2", "3", "4", "5", "6"})
                .map(stringToInteger);


        // create observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    //Buffer
    private void testBuffer(){
        // create observable
        Observable<List<Integer>> observable = Observable
                .fromArray(new Integer[]{1,2,3,4,5,6,7,8})
                .buffer(3);

        // create observer
        Observer<List<Integer>> observer = new Observer<List<Integer>>() {
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(List<Integer> s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    //Take
    private void testTake(){
        // create observable
        Observable<Integer> observable = Observable
                .fromArray(new Integer[]{5,6,7,8,9})
                .take(3);

        // create observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    //Skip
    private void testSkip(){
        // create observable
        Observable<Integer> observable = Observable
                .fromArray(new Integer[]{5,6,7,8,9})
                .skip(2);

        // create observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    //Distinct - delete duplicates
    private void testDistinct(){
        // create observable
        Observable<Integer> observable = Observable
                .fromArray(new Integer[]{5,9,7,5,8,6,7,8,9})
                .distinct();


        // create observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    //Filter
    private void testFilter(){

        Predicate<String> filterFiveOnly = new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s.contains("5");
            }
        };

        // create observable
        Observable<String> observable = Observable
                .fromArray(new String[]{"15", "27", "34", "46", "52", "63"})
                .filter(filterFiveOnly);

        // create observer
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
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

        // subscribe
        observable.subscribe(observer);
    }

    //Merge
    private void testMerge(){

        // create observable
        Observable<Integer> observable = Observable
                .fromArray(new Integer[]{1,2,3})
                .mergeWith(Observable.fromArray(new Integer[]{6,7,8,9}));

        // create observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
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

        // subscribe
        observable.subscribe(observer);
    }

    //Zip
    private void testZip(){

        BiFunction<Integer, String, String> zipIntWithString = new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer i, String s) throws Exception {
                return s + ": " + i;
            }
        };

        // create observable
        Observable<String> observable = Observable
                .fromArray(new Integer[]{1,2,3})
                .zipWith(Observable.fromArray(new String[]{"One", "Two", "Three"}), zipIntWithString);

        // create observer
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

        };

        // subscribe
        observable.subscribe(observer);
    }

    //TakeUntil
    private void testTakeUntil(){

        // create observable
        Observable<Integer> observable = Observable
                .fromArray(new Integer[]{1,2,3,4,5,6,7,8})
                .takeUntil(i -> i == 5);

        // create observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
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

        // subscribe
        observable.subscribe(observer);
    }

    //All
    private void testAll(){

        // create observable
        Single<Boolean> observable = Observable
                .fromArray(new Integer[]{1,2,3,4,5,6,7,8})
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer i) throws Exception {
                        return i < 10;
                    }
                });

        // create observer
        SingleObserver<Boolean> observer = new SingleObserver<Boolean>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onSuccess(Boolean b) {
                Log.d(TAG, "onSuccess: " + b);
            }

        };

        // subscribe
        observable.subscribe(observer);
    }

    //Action
    private void testAction(){
        // create observable
        Observable<String> observable = Observable.fromArray(new String[]{"one", "two", "three"});

        // create action
        Consumer<String> action = s -> Log.d(TAG, "onNext: " + s);

        // subscribe
        observable.subscribe(action);
    }
}
