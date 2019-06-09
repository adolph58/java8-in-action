package lambdasinaction.chap6;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    @Override
    public Supplier<List<T>> supplier() {
        return () -> new ArrayList<T>();  //创建集合操作的起始点
        //return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return (list, item) -> list.add(item);  //累积遍历过的项目，原位修改累加器
        //return List::add;
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return i -> i;  //恒等函数
        //return Function.identity();
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);  //修改第一个累加器，将其与第二个累加器的内容合并
            return list1;  //返回修改后的第一个累加器
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));  //为收集器添加 IDENTITY_FINISH 和 CONCURRENT 标志
    }
}
