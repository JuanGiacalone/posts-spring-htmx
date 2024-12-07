package com.jpgiacalone.posts_spring_htmx;

import java.time.Duration;
import java.util.List;
import java.util.Locale;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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

        return Flux.concat(flux, flux2).log();

    }

    public static void main(String[] args) throws InterruptedException {
        ReactivePractice runTest = new ReactivePractice(); runTest.testMono().subscribe( System.out::println );
        runTest.testFlux().subscribe( System.out::println );
        runTest.testMap().subscribe( System.out::println );
        runTest.testFlatMap().subscribe( System.out::println );
        runTest.testSkip1().subscribe( System.out::println );
        runTest.testSkip2().subscribe( System.out::println );

        try {
            Thread.sleep(10_000); 
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
