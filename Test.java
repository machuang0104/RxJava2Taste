package com.nyx.rx;

import com.nyx.rx.bdzc.HostUrl;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Flowable.just(HostUrl.SERVER_LIST).subscribe(System.out::println);
        Disposable flowString = Flowable.just(HostUrl.SERVER_LIST).subscribe(s -> {
            System.out.println(s);
        });
        flowString.dispose();
        Flowable<Integer> flow = Flowable.range(1, 5).map(v -> v * v).filter(v -> v % 3 == 0);
        flow.subscribe(System.out::println);
        runtime();
    }

    static void runtime() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        final int[] index = {0};
        ObservableOnSubscribe<Integer> source = new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                System.out.println("ssss = " + index[0]);
                while (!e.isDisposed()) {
                    System.out.println("Get Index = " + index[0]);
                    e.onNext(list.get(index[0]));
                    index[0]++;
                    if (index[0] > 9) {
                        e.onComplete();
//                    e.onError(new Exception("ddd"));
                    }
                }
            }
        };
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("accept : " + integer);
            }
        };
        Observable.create(source).subscribe(consumer);
//        Observable.create(emitter -> {
//            while (!emitter.isDisposed()) {
//                long time = System.currentTimeMillis();
//                emitter.onNext(time);
//                if (time % 2 != 0) {
//                    System.out.println("time % 2 != 0");
//                    emitter.onError(new IllegalStateException("Odd millisecond!"));
//                    break;
//                }
//            }
//        }).subscribe(System.out::println, Throwable::printStackTrace);
    }
}