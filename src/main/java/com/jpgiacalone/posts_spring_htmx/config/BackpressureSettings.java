package com.jpgiacalone.posts_spring_htmx.config;

import java.time.Duration;

import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BackpressureSettings {
    
    private Flux<Long> createNoOverflowFlux() {
        return Flux.range(1, Integer.MAX_VALUE)
        .log()
        .concatMap(x -> Mono.delay(Duration.ofMillis(100)));
        
        // Simulate a process that takes time   
    }

    private Flux<Long> createOverflowFlux() { // flux that overflows itself
        return Flux.interval(Duration.of(1, null))
        .log().concatMap(x -> Mono.delay(Duration.ofMillis(100)));
    }

    private Flux<Long> createDropOnBackpressureFlux() {
        /// this function starts dropping the overflowed flows
        /// 
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop()
                .concatMap(a -> Mono.delay(Duration.ofMillis(100)).thenReturn(a))
                .doOnNext(a -> System.out.println("element kept  :"+ a ));

    }

    private Flux<Long> createBufferOnBackpressureFlux() {
        /// this function starts piling the overflowed flows
        /// 
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureBuffer(50, BufferOverflowStrategy.DROP_OLDEST) // can be drop_latest
                .concatMap(a -> Mono.delay(Duration.ofMillis(100)).thenReturn(a))
                .doOnNext(a -> System.out.println("element kept  :"+ a ));

    }

    
    public static void main(String[] args) {
        BackpressureSettings run = new BackpressureSettings();
        run.createDropOnBackpressureFlux()
            .blockLast();
    }
}
