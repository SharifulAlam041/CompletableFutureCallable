package org.example;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

///////////****  methods of Future Interface ****

//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,1,1, TimeUnit.HOURS,
//                new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
//
//        Future<?> futureObj = poolExecutor.submit(()->{
//            try {
//                Thread.sleep(7000);
//                System.out.println("This is the task, which thread will execute");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        System.out.println("Task is done? " + futureObj.isDone());
//        try {
//            futureObj.get(2,TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            System.out.println("Get Interrupted");
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (TimeoutException e) {
//            System.out.println("Time out exception happens");
//        }
//
//        try {
//            futureObj.get();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("Task is Done? " + futureObj.isDone());
//        System.out.println("Task is cancelled? " + futureObj.isCancelled());
//
//
//
//        ThreadPoolExecutor poolExecutor1 = new ThreadPoolExecutor(3,3,3, TimeUnit.HOURS,
//                new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
//
//        Future<?> futureObject1 = poolExecutor1.submit(()->{
//            System.out.println("Task1 with Runnable");
//        });
//        try {
//            Object object = futureObject1.get();
//            System.out.println(object==null);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        List<Integer> output = new ArrayList<>();
//        Future<List<Integer>> futureObject2 = poolExecutor1.submit(new MyRunnable(output),output);
//
//        try {
//            futureObject2.get();
//            System.out.println(output.get(0));
//            List<Integer> result = futureObject2.get();
//            System.out.println(result.get(0));
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        Future<List<Integer>> futureObject3 = poolExecutor1.submit(()->{
//            List<Integer> outputCallable = new ArrayList<>();
//            outputCallable.add(300);
//            return outputCallable;
//        });
//
//        try {
//            futureObject3.get();
//            System.out.println(output.get(0));
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


        /////////////////***CompletableFuture
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,1,3,TimeUnit.HOURS,
                new ArrayBlockingQueue<>(5),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        try {
            CompletableFuture<String> asyncTask1 = CompletableFuture.supplyAsync(() -> {
                return "task completed";
            },poolExecutor);

            System.out.println(asyncTask1.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<String> asyncTask1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("ThreadName of SupplyAsync:" + Thread.currentThread().getName());
            return "Concept and";
        },poolExecutor).thenApply((String val)-> {
            System.out.println("ThreadName of ThenApply:" + Thread.currentThread().getName());
            return val + " coding";
        });

        CompletableFuture<String> asyncTask2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("ThreadName of SupplyAsync:" + Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Concept and";
        },poolExecutor).thenApplyAsync((String val)-> {
            System.out.println("ThreadName of ThenApply:" + Thread.currentThread().getName());
            return val + " coding";
        });

        CompletableFuture<String> asyncTask3 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("ThreadName of SupplyAsync:" + Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Concept and";
        },poolExecutor).thenCompose((String val)-> {
            System.out.println("ThreadName of ThenApply:" + Thread.currentThread().getName());
            return CompletableFuture.supplyAsync(()-> val+ " coding");
        }).thenCompose((String val)-> {
            System.out.println("ThreadName of ThenApply:" + Thread.currentThread().getName());
            return CompletableFuture.supplyAsync(()-> val+ " practices");
        });

        CompletableFuture<Void> completableFuture = asyncTask3.thenAccept((String val)->{
            System.out.println("Printing value" + val);
        });


        try {
            System.out.println(asyncTask3.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<String> futureTask1 = CompletableFuture.supplyAsync(() -> {
            return "task completed ";
        },poolExecutor);
        CompletableFuture<String> futureTask2 = CompletableFuture.supplyAsync(() -> {
            return "really!";
        },poolExecutor);

        CompletableFuture<String> combineFutureObj = futureTask1.thenCombine(futureTask2,(String val1,String val2)-> val1+val2);
        try {
            System.out.println(combineFutureObj.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}