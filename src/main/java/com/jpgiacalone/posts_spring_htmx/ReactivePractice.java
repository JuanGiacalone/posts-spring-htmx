package com.jpgiacalone.posts_spring_htmx;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.function.Tuple2;


@SuppressWarnings("unused")
public class ReactivePractice {

        /// Reactive testing
    /// 
    private Mono<String> testMono() {
        return Mono.just("hello from mono").log();
    }

    private Flux<String> testFlux() {
        // return Flux.just("hello from flux").log();
        List<String> list = List.of("hello from flux","hello from flux","hello from flux");
        return Flux.fromIterable(list).log();
    }

    private Flux<String> testMap() {
        Flux<String> flux = Flux.just("hello from flux","hello from flux","hello from flux");
        return flux.map(s -> s.toUpperCase(Locale.ROOT)).log();
    }

    private Flux<String> testFlatMap() {
        Flux<String> flux = Flux.just("hello from flux","hello from flux","hello from flux");
        return flux.flatMap(s -> Mono.just(s.toUpperCase(Locale.ROOT))).log();
    }

    private Flux<String> testSkip1() {
        Flux<String> flux = Flux.just("hello from flux1","hello from flux2","hello from flux3");
        return flux.skip(2);
    }
    private Flux<String> testSkip2() {
        Flux<String> flux = Flux.just("Java","Cpp","Rust","Python")
        .delayElements(Duration.ofSeconds(1));
        return flux.skip(Duration.ofMillis(2010));
        // this one will just print Rust and Pything since will skip 2,1 seconds and Cpp will come at 2 seconds and be skipped
    }

    private Flux<String> testSkip3() {
        Flux<String> flux = Flux.just("Java","Cpp","Rust","Python")
        .delayElements(Duration.ofSeconds(1));
        return flux.skipLast(2); // skips the last 2
       
    }

    private Flux<Integer> testSkip4() {
        Flux<Integer> flux  = Flux.range(1, 20).delayElements(Duration.ofSeconds(1));
        // return flux.skipWhile(i -> i < 10); // it will skip while the condition is true
        return flux.skipUntil(i -> i == 10); // it will skip until the condition is true
    }

    private Flux<Integer> testConcat() {

        Flux<Integer> flux  = Flux.range(1, 20).delayElements(Duration.ofSeconds(1));
        Flux<Integer> flux2  = Flux.range(110, 20).delayElements(Duration.ofSeconds(1));

        // Concat will print 1-20 and then 110-130
        return Flux.concat(flux, flux2).log();
    }
    private Flux<Integer> testMerge() {

        Flux<Integer> flux  = Flux.range(1, 20).delayElements(Duration.ofSeconds(1));
        Flux<Integer> flux2  = Flux.range(110, 20).delayElements(Duration.ofSeconds(1));

        // Merge will print both as they arrive
        return Flux.merge(flux, flux2).log();
    }
    
    private Flux<Tuple2<Integer, Integer>> testZip() {

        Flux<Integer> flux  = Flux.range(1, 20).delayElements(Duration.ofSeconds(1));
        Flux<Integer> flux2  = Flux.range(110, 20).delayElements(Duration.ofSeconds(1));

        // it will take 1 number from both flux and send them in tuples.
        // it will end as soon as 1 flux ends
        return Flux.zip(flux, flux2).log();
    }

    private Mono<List<Integer>> testCollect(){
        Flux<Integer> flux = Flux.range(1, 20);
        
        // this returns a MONO with the contains of flux in a single list
        return flux.collectList();
    }

    private Flux<List<Integer>> testBuffer(){
        Flux<Integer> flux = Flux.range(1, 20).delayElements(Duration.ofMillis(1000));
        
        // this returns a FLUX with the contains of flux in a single list
        // accepts a max size to publish every X elements or set it from Duration
        // return flux.buffer(Duration.ofMillis(3_100))
        return flux.buffer(3);
    }
    
    @SuppressWarnings("unused")
    private Mono<Map<Integer,Integer>> testCollectMap(){
        Flux<Integer> flux = Flux.range(1, 20).delayElements(Duration.ofMillis(1000));
        
        // a , a*a
        // 2 , 25
        // 6 , 36

        return flux.collectMap( inte -> inte , inte -> inte * inte);
    }
    
    private Flux <Integer> testDoFunctions() {
        Flux<Integer> flux = Flux.range(1, 20).delayElements(Duration.ofMillis(1000));

        return flux.doOnEach( signal -> {
            if (signal.getType() == SignalType.ON_COMPLETE) {
                
                System.out.println("i am done");
            } else {
                System.out.println(signal.get());
            }
        });
    }

    private Flux <Integer> testDoFunctions2() {
        Flux<Integer> flux = Flux.range(1, 20).delayElements(Duration.ofMillis(1000));

        return flux.doOnComplete(() -> System.out.println("I am Complete"));
    }

    private Flux <Integer> testDoNext() {
        Flux<Integer> flux = Flux.range(1, 20).delayElements(Duration.ofMillis(1000));

        return flux.doOnNext( integer -> System.out.println("I am " + integer));
    }


    private Flux <Integer> testDoOnSubscribe() {
        Flux<Integer> flux = Flux.range(1, 20).delayElements(Duration.ofMillis(1000));

        return flux.doOnSubscribe( a -> System.out.println("Hey you subscribed!"));
    }

    private Flux <Integer> testDoOnCancel() {

        Flux<Integer> flux = Flux.range(1, 20).delayElements(Duration.ofMillis(1000));

        return flux.doOnCancel(() -> System.out.println("Cancelled"));
    }

    private Flux <Integer> testErrorHand() {

        Flux<Integer> flux = Flux
            .range(1, 20)    
            .map(n -> {
            if(n==5) {
                throw new RuntimeErrorException(null, "number 5 not right");
            }
            return n;
        });

        return flux.onErrorContinue((t,o) -> System.out.println("threw "+t+" about this number"+o));
    }

    private Flux <Integer> testErrorHand2() {

        Flux<Integer> flux = Flux
            .range(1, 20)    
            .map(n -> {
            if(n==5) {
                throw new RuntimeErrorException(null, "number 5 not right");
            }
            return n;
        });

        return flux.onErrorReturn(ArithmeticException.class,-1);
    }

    private Flux <Integer> testErrorHand3() {

        Flux<Integer> flux = Flux
            .range(1, 20)    
            .map(n -> {
            if(n==5) {
                throw new RuntimeErrorException(null, "number 5 not right");
            }
            return n;
        });


        return flux.onErrorResume( th -> Flux.range(100, 5));
    }

    private Flux <Integer> testErrorHand4() {

        Flux<Integer> flux = Flux
            .range(1, 20)    
            .map(n -> {
            if(n==5) {
                throw new RuntimeErrorException(null, "number 5 not right");
            }
            return n;
        });


        return flux.onErrorMap( th -> new UnsupportedOperationException(th.getMessage()));
    }

    public static void main(String[] args) throws InterruptedException {
        ReactivePractice runTest = new ReactivePractice(); 
       
        // Disposable disposable = runTest.testDoOnCancel().subscribe();
        /*
       
       runTest.testMono().subscribe( System.out::println );
        runTest.testFlux().subscribe( System.out::println );
        runTest.testMap().subscribe( System.out::println );
        runTest.testFlatMap().subscribe( System.out::println );
        runTest.testSkip1().subscribe( System.out::println );
        runTest.testSkip2().subscribe( System.out::println );
       
       
       */ 
        // runTest.testDoOnSubscribe().subscribe(System.out::println);

        // disposable.dispose();
        
        //...

        runTest.testErrorHand3().subscribe(System.out::println);
    


        try {
            Thread.sleep(20_000); 
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
